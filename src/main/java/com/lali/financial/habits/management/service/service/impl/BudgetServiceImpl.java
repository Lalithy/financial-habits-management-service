package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: BudgetServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.CommonConstants;
import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.BudgetDTO;
import com.lali.financial.habits.management.service.dto.RequestBudgetDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.ValidatorDTO;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetDTOI;
import com.lali.financial.habits.management.service.entity.Budget;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import com.lali.financial.habits.management.service.entity.Expense;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.repository.BudgetRepository;
import com.lali.financial.habits.management.service.repository.ExpenseRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetCategoryService budgetCategoryService;
    private final UserService userService;
    private final ExpenseRepository expenseRepository;

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

            DateTimeFormatter formatter = CommonUtilities.getDateTimeFormatter(CommonConstants.YYYY_MM_dd_HH_MM_SS);
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
     * The method provide all budget by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getBudgetByUserId(Integer userId) {

        log.info("BudgetImpl.getBudgetByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        List<BudgetDTOI> allBudget = budgetRepository.findByUserUserIdOrderByBudgetIdAsc(userId);
        List<Expense> allExpense = expenseRepository.findAll();

        List<BudgetDTO> budgetDTOList = new ArrayList<>();

        allBudget.forEach(budgetDTOI -> {
                    

                    BudgetDTO budgetDTO = new BudgetDTO();
                    budgetDTO.setBudgetCategoryName(budgetDTOI.getBudgetCategory().getBudgetCategoryName());
                    budgetDTO.setEstimatedBudget(budgetDTOI.getBudgetAmount());
                    budgetDTO.setExpense(100.0);
                    budgetDTO.setRemainingBudget(budgetDTOI.getBudgetAmount());
                    budgetDTOList.add(budgetDTO);
                }

        );

//        allBudget.forEach(budgetDTOI -> {
//                    BudgetDTO budgetDTO = new BudgetDTO();
//                    budgetDTO.setBudgetCategoryName("Test-" + budgetDTOI.getBudgetId());
//                    budgetDTO.setEstimatedBudget(budgetDTOI.getBudgetAmount());
//                    budgetDTO.setExpense(100.0);
//                    budgetDTO.setRemainingBudget(budgetDTOI.getBudgetAmount() - 100.0);
//                    budgetDTOList.add(budgetDTO);
//                }
//
//        );

        if (allBudget.isEmpty()) {
            log.warn("BudgetImpl.getBudgetByUserId Method : {}", MessageConstants.BUDGET_IS_EMPTY);
            response.setMessage(MessageConstants.CAN_NOT_FIND_BUDGET);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setMessage(MessageConstants.FOUND_BUDGET);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(budgetDTOList);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
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
            validatorDTO.setMessage(MessageConstants.INVALID_BUDGET_AMOUNT);
            return validatorDTO;
        }

        validatorDTO.setStatus(false);
        validatorDTO.setMessage(MessageConstants.VALID);
        return validatorDTO;
    }

}
