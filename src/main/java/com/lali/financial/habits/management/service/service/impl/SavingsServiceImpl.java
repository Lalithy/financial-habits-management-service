package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: SavingsServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.CommonConstants;
import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.*;
import com.lali.financial.habits.management.service.dto.dtoi.SavingsDTOI;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.entity.Savings;
import com.lali.financial.habits.management.service.repository.SavingsRepository;
import com.lali.financial.habits.management.service.service.SavingsService;
import com.lali.financial.habits.management.service.service.UserService;
import com.lali.financial.habits.management.service.util.CommonUtilities;
import com.lali.financial.habits.management.service.util.DateValidator;
import com.lali.financial.habits.management.service.util.DateValidatorDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.lali.financial.habits.management.service.util.CommonUtilities.getFirstOfCurrentMonthToCurrentDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingsServiceImpl implements SavingsService {

    private final SavingsRepository savingsRepository;

    private final UserService userService;

    /**
     * The method add a savings
     *
     * @param savingsDTO -> {savingsDetails, savingsAmount, savingsDate, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> addSavings(RequestSavingsDTO savingsDTO) {

        log.info("SavingsServiceImpl.addSavings Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        try {

            DateTimeFormatter formatter = CommonUtilities.getDateTimeFormatter(CommonConstants.YYYY_MM_dd_HH_MM_SS);
            ValidatorDTO validateSavings = isValidateSavings(savingsDTO, formatter);
            if (validateSavings.isStatus()) {
                log.warn("SavingsServiceImpl.addSavings Method : {}", MessageConstants.VALIDATION_FAILED);
                response.setMessage(validateSavings.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            LocalDateTime savingsDateTime = getSavingsDateTime(savingsDTO.getSavingsDate(), formatter);

            GuestUser user = userService.findUserById(savingsDTO.getUserId());

            Savings savings = Savings.builder()
                    .savingsDetails(savingsDTO.getSavingsDetails())
                    .savingsAmount(savingsDTO.getSavingsAmount())
                    .savingsDate(savingsDateTime)
                    .user(user)
                    .build();
            savingsRepository.save(savings);

            response.setMessage(MessageConstants.SUCCESSFULLY_CREATED);
            response.setStatus(HttpStatus.OK);
            response.setTimestamp(LocalDateTime.now());
            response.setDetails(savings);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException exception) {
            log.error("SavingsServiceImpl.addSavings Method : {}", exception.getMessage());
            response.setMessage(MessageConstants.FAILED_INSERTING);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * The method provide all savings by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getSavingsByUserId(Integer userId) {

        log.info("SavingsImpl.getSavingsByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        FromToDateDTO fromToDate = getFirstOfCurrentMonthToCurrentDateTime();
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<SavingsDTOI> allSavingsList = savingsRepository
                .findByUserUserIdOrderBySavingsIdDesc(userId);

        List<SavingsDTOI> allSavings = getSavings(fromDate, toDate, allSavingsList);

        double sumOfSavingsAmount = getSumOfSavingsAmount(allSavings);

        SumSavingsDTO sumSavings = SumSavingsDTO.builder()
                .sumOfSavingsAmount(sumOfSavingsAmount)
                .build();

        response.setMessage(MessageConstants.FOUND_INCOMES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(sumSavings);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    /**
     * The method provide sum of savings amount
     *
     * @param allSavings
     * @return double
     * @author Lali..
     */
    private double getSumOfSavingsAmount(List<SavingsDTOI> allSavings) {
        return allSavings.stream()
                .mapToDouble(SavingsDTOI::getSavingsAmount)
                .sum();
    }

    /**
     * The method provide savings
     *
     * @param fromDate
     * @param toDate
     * @param allSavingsList
     * @return List<SavingsDTOI>
     * @author Lali..
     */
    private List<SavingsDTOI> getSavings(LocalDateTime fromDate, LocalDateTime toDate, List<SavingsDTOI> allSavingsList) {
        log.info("SavingsImpl.getSavings Method : {}", MessageConstants.ACCESSED);
        return allSavingsList.stream()
                .filter(expense -> expense.getSavingsDate().isAfter(fromDate)
                        && expense.getSavingsDate().isBefore(toDate)
                        || expense.getSavingsDate().isEqual(fromDate)
                        || expense.getSavingsDate().isEqual(toDate))
                .toList();
    }

    /**
     * The method delete an savings by savings id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> removeSavingsByUserId(Integer userId) {

        log.info("SavingsImpl.removeSavingsByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO responseDTO = new ResponseDTO();

        FromToDateDTO fromToDate = getFirstOfCurrentMonthToCurrentDateTime();
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<Long> savingsList = savingsRepository
                .findByUserIdAndSavingsDateBetween(userId, fromDate, toDate);
        if (savingsList.isEmpty()) {
            log.warn("SavingsImpl.removeSavingsByUserId Method : {}", MessageConstants.DOES_NOT_FOUND_SAVING_FOR_REMOVING);
            responseDTO.setMessage(MessageConstants.DOES_NOT_FOUND_SAVING_FOR_REMOVING);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        try {
            savingsList.forEach(savingsRepository::deleteBySavingsId);
            responseDTO.setMessage(MessageConstants.SUCCESSFULLY_DELETED);
            responseDTO.setDetails(savingsList);
            responseDTO.setStatus(HttpStatus.OK);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException exception) {
            log.error("SavingsServiceImpl.removeSavingsByUserId Method : {}", exception.getMessage());
            responseDTO.setMessage(MessageConstants.FAILED_DELETING);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }

    /**
     * The method validate savings details
     *
     * @param savingsDTO
     * @param formatter
     * @return ValidatorDTO
     * @author Lali..
     */
    private ValidatorDTO isValidateSavings(RequestSavingsDTO savingsDTO, DateTimeFormatter formatter) {
        log.info("SavingsServiceImpl.isValidateSavings Method : {}", MessageConstants.ACCESSED);
        boolean isInvalidBudgetCategory = CommonUtilities.isNullEmptyBlank(savingsDTO.getSavingsDetails());
        DateValidator validator = new DateValidatorDateTimeFormatter(formatter);
        boolean isValidSavingsDate = !validator.isValid(savingsDTO.getSavingsDate());
        ValidatorDTO validatorDTO = new ValidatorDTO();

        if (isInvalidBudgetCategory) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_SAVINGS_DETAILS);
            return validatorDTO;
        }
        if (savingsDTO.getSavingsAmount() <= 0) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_SAVINGS_AMOUNT);
            return validatorDTO;
        }

        validatorDTO.setStatus(isValidSavingsDate);
        validatorDTO.setMessage(MessageConstants.INVALID_DATE);
        return validatorDTO;
    }

    /**
     * The method provide a  LocalDateTime object of savings date
     *
     * @param savingsDate
     * @param formatter
     * @return LocalDateTime
     * @author Lali..
     */
    private LocalDateTime getSavingsDateTime(String savingsDate, DateTimeFormatter formatter) {
        log.info("SavingsServiceImpl.getSavingsDateTime Method : {}", MessageConstants.ACCESSED);
        return CommonUtilities.convertStringToLocalDateTime(savingsDate, formatter);
    }
}
