package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: SavingsServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestSavingsDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.SumSavingsDTO;
import com.lali.financial.habits.management.service.dto.ValidatorDTO;
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

            DateTimeFormatter formatter = CommonUtilities.getDateTimeFormatter();
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
        List<SavingsDTOI> allSavings = savingsRepository.findByUserUserId(userId);

        double sumOfSavingsAmount = allSavings.stream()
                .mapToDouble(SavingsDTOI::getSavingsAmount)
                .sum();

        SumSavingsDTO sumSavingsDTO = SumSavingsDTO.builder()
                .sumOfSavingsAmount(sumOfSavingsAmount)
                .build();

        if (allSavings.isEmpty()) {
            log.warn("SavingsImpl.getSavingsByUserId Method : {}", MessageConstants.INCOMES_IS_EMPTY);
            response.setMessage(MessageConstants.CAN_NOT_FIND_INCOMES);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setMessage(MessageConstants.FOUND_INCOMES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(sumSavingsDTO);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
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
