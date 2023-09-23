package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: BudgetServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestBudgetDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.ValidatorDTO;
import com.lali.financial.habits.management.service.entity.Budget;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.repository.BudgetRepository;
import com.lali.financial.habits.management.service.service.BudgetCategoryService;
import com.lali.financial.habits.management.service.service.BudgetService;
import com.lali.financial.habits.management.service.service.UserService;
import com.lali.financial.habits.management.service.util.CommonUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetCategoryService budgetCategoryService;
    private final UserService userService;

    /**
     * The method add a budget
     *
     * @param budgetDTO -> {budgetAmount, budgetDate, budgetCategoryId, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> addBudget(RequestBudgetDTO budgetDTO) {

        log.info("BudgetServiceImpl.addBudget Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        try {

            DateTimeFormatter formatter = CommonUtilities.getDateTimeFormatter();
            ValidatorDTO validateBudget = isValidateBudget(budgetDTO, formatter);
            if (validateBudget.isStatus()) {
                log.warn("BudgetServiceImpl.addBudget Method : {}", MessageConstants.VALIDATION_FAILED);
                response.setMessage(validateBudget.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            LocalDateTime budgetDateTime = LocalDateTime.now();

            GuestUser user = userService.findUserById(budgetDTO.getUserId());

            BudgetCategory budgetCategory = budgetCategoryService.findBudgetCategoryById(budgetDTO.getBudgetCategoryId());

            Budget budget = Budget.builder()
                    .budgetAmount(budgetDTO.getBudgetAmount())
                    .budgetDate(budgetDateTime)
                    .user(user)
                    .budgetCategory(budgetCategory)
                    .build();
            budgetRepository.save(budget);

            response.setMessage(MessageConstants.SUCCESSFULLY_CREATED);
            response.setStatus(HttpStatus.OK);
            response.setTimestamp(LocalDateTime.now());
            response.setDetails(budget);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException exception) {
            log.error("BudgetServiceImpl.addBudget Method : {}", exception.getMessage());
            response.setMessage(MessageConstants.FAILED_INSERTING);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    /**
     * The method validate budget details
     *
     * @param budgetDTO
     * @param formatter
     * @return ValidatorDTO
     * @author Lali..
     */
    private ValidatorDTO isValidateBudget(RequestBudgetDTO budgetDTO, DateTimeFormatter formatter) {
        log.info("BudgetServiceImpl.isValidateBudget Method : {}", MessageConstants.ACCESSED);
        ValidatorDTO validatorDTO = new ValidatorDTO();

        if (budgetDTO.getBudgetAmount() <= 0) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_EXPENSE_AMOUNT);
            return validatorDTO;
        }

        validatorDTO.setStatus(false);
        validatorDTO.setMessage(MessageConstants.VALID);
        return validatorDTO;
    }

    /**
     * The method provide a  LocalDateTime object of budget date
     *
     * @param budgetDate
     * @param formatter
     * @return LocalDateTime
     * @author Lali..
     */
    private LocalDateTime getBudgetDateTime(String budgetDate, DateTimeFormatter formatter) {
        log.info("BudgetServiceImpl.getBudgetDateTime Method : {}", MessageConstants.ACCESSED);
        return CommonUtilities.convertStringToLocalDateTime(budgetDate, formatter);
    }

}
