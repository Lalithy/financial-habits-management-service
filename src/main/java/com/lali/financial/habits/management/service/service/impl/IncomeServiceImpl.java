package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/17/2023
 * Project: financial-habits-management-service
 * Description: IncomeServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestIncomeDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.ValidatorDTO;
import com.lali.financial.habits.management.service.dto.dtoi.IncomeDTOI;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.entity.Income;
import com.lali.financial.habits.management.service.repository.IncomeRepository;
import com.lali.financial.habits.management.service.service.IncomeService;
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
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    private final UserService userService;

    /**
     * The method add an income
     *
     * @param incomeDTO -> {incomeDetails, incomeAmount, incomeDate, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> addIncome(RequestIncomeDTO incomeDTO) {

        log.info("IncomeServiceImpl.addIncome Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        try {

            DateTimeFormatter formatter = CommonUtilities.getDateTimeFormatter();
            ValidatorDTO validateIncome = isValidateIncome(incomeDTO, formatter);
            if (validateIncome.isStatus()) {
                log.warn("IncomeServiceImpl.addIncome Method : {}", MessageConstants.VALIDATION_FAILED);
                response.setMessage(validateIncome.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            LocalDateTime incomeDateTime = getIncomeDateTime(incomeDTO.getIncomeDate(), formatter);

            GuestUser user = userService.findUserById(incomeDTO.getUserId());

            Income income = Income.builder()
                    .incomeDetails(incomeDTO.getIncomeDetails())
                    .incomeAmount(incomeDTO.getIncomeAmount())
                    .incomeDate(incomeDateTime)
                    .user(user)
                    .build();
            incomeRepository.save(income);

            response.setMessage(MessageConstants.SUCCESSFULLY_CREATED);
            response.setStatus(HttpStatus.OK);
            response.setTimestamp(LocalDateTime.now());
            response.setDetails(income);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException exception) {
            log.error("IncomeServiceImpl.addIncome Method : {}", exception.getMessage());
            response.setMessage(MessageConstants.FAILED_INSERTING);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * The method provide all incomes by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getIncomesByUserId(Integer userId) {
        log.info("IncomesImpl.getIncomesByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        List<IncomeDTOI> allIncomes = incomeRepository.findByUserUserId(userId);

        if (allIncomes.isEmpty()) {
            log.warn("IncomesImpl.getIncomesByUserId Method : {}", MessageConstants.INCOMES_IS_EMPTY);
            response.setMessage(MessageConstants.CAN_NOT_FIND_INCOMES);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setMessage(MessageConstants.FOUND_INCOMES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(allIncomes);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    /**
     * The method delete an income by income id
     *
     * @param incomeId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> removeIncomeByUserId(Long incomeId) {

        log.info("IncomeImpl.removeIncomeByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO responseDTO = new ResponseDTO();
        boolean existsId = incomeRepository.existsById(incomeId);
        if (!existsId) {
            log.warn("IncomeImpl.removeIncomeByUserId Method : {}", MessageConstants.DOES_NOT_FOUND_EXPENSE);
            responseDTO.setMessage(MessageConstants.DOES_NOT_FOUND_EXPENSE);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        try {
            Income income = incomeRepository.findById(incomeId).orElse(null);
            incomeRepository.deleteByIncomeId(incomeId);
            responseDTO.setMessage(MessageConstants.SUCCESSFULLY_DELETED);
            responseDTO.setDetails(income.getIncomeDetails());
            responseDTO.setStatus(HttpStatus.OK);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException exception) {
            log.error("IncomeServiceImpl.removeIncomeByUserId Method : {}", exception.getMessage());
            responseDTO.setMessage(MessageConstants.FAILED_DELETING);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }

    /**
     * The method validate income details
     *
     * @param incomeDTO
     * @param formatter
     * @return ValidatorDTO
     * @author Lali..
     */
    private ValidatorDTO isValidateIncome(RequestIncomeDTO incomeDTO, DateTimeFormatter formatter) {
        log.info("IncomeServiceImpl.isValidateIncome Method : {}", MessageConstants.ACCESSED);
        boolean isInvalidBudgetCategory = CommonUtilities.isNullEmptyBlank(incomeDTO.getIncomeDetails());
        DateValidator validator = new DateValidatorDateTimeFormatter(formatter);
        boolean isValidIncomeDate = !validator.isValid(incomeDTO.getIncomeDate());
        ValidatorDTO validatorDTO = new ValidatorDTO();

        if (isInvalidBudgetCategory) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_INCOME_DETAILS);
            return validatorDTO;
        }
        if (incomeDTO.getIncomeAmount() <= 0) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_INCOME_AMOUNT);
            return validatorDTO;
        }

        validatorDTO.setStatus(isValidIncomeDate);
        validatorDTO.setMessage(MessageConstants.INVALID_DATE);
        return validatorDTO;
    }

    /**
     * The method provide a  LocalDateTime object of income date
     *
     * @param incomeDate
     * @param formatter
     * @return LocalDateTime
     * @author Lali..
     */
    private LocalDateTime getIncomeDateTime(String incomeDate, DateTimeFormatter formatter) {
        log.info("IncomeServiceImpl.getIncomeDateTime Method : {}", MessageConstants.ACCESSED);
        return CommonUtilities.convertStringToLocalDateTime(incomeDate, formatter);
    }

}
