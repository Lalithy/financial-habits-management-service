package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: BudgetServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.*;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetCategoryDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.ExpenseDTOI;
import com.lali.financial.habits.management.service.entity.Budget;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.repository.BudgetCategoryRepository;
import com.lali.financial.habits.management.service.repository.BudgetRepository;
import com.lali.financial.habits.management.service.repository.ExpenseRepository;
import com.lali.financial.habits.management.service.service.BudgetCategoryService;
import com.lali.financial.habits.management.service.service.BudgetService;
import com.lali.financial.habits.management.service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.lali.financial.habits.management.service.util.CommonUtilities.getFirstOfCurrentMonthToCurrentDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetCategoryService budgetCategoryService;
    private final UserService userService;
    private final ExpenseRepository expenseRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;

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

            ValidatorDTO validateBudget = isValidateBudget(budgetDTO);
            if (validateBudget.isStatus()) {
                log.warn("BudgetServiceImpl.addBudget Method : {}", MessageConstants.VALIDATION_FAILED);
                response.setMessage(validateBudget.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            FromToDateDTO fromToDate = getFirstOfCurrentMonthToCurrentDateTime();
            LocalDateTime fromDate = fromToDate.getFromDate();
            LocalDateTime toDate = fromToDate.getToDate();

            Integer budgetCategoryId = budgetDTO.getBudgetCategoryId();
            Integer userId = budgetDTO.getUserId();
            Double budgetAmount = budgetDTO.getBudgetAmount();

            List<Budget> existsBudgetIdList = budgetRepository
                    .findByBudgetCategoryBudgetCategoryIdAndUserUserIdAndBudgetDateBetween(budgetCategoryId, userId, fromDate, toDate);

            if (!existsBudgetIdList.isEmpty()) {
                Budget budget = existsBudgetIdList.stream().findAny().get();
                budget.setBudgetAmount(budgetAmount);
                budgetRepository.save(budget);

                response.setMessage(MessageConstants.SUCCESSFULLY_CREATED);
                response.setStatus(HttpStatus.OK);
                response.setTimestamp(LocalDateTime.now());
                response.setDetails(budget);
                return ResponseEntity.status(HttpStatus.OK).body(response);

            }

            LocalDateTime budgetDateTime = LocalDateTime.now();

            GuestUser user = userService.findUserById(userId);

            BudgetCategory budgetCategory = budgetCategoryService.findBudgetCategoryById(budgetCategoryId);

            Budget budget = Budget.builder()
                    .budgetAmount(budgetAmount)
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
        List<BudgeExpenseDTO> budgetExpenseList = new ArrayList<>();
        List<BudgetDTO> budgetDTOList = new ArrayList<>();

        FromToDateDTO fromToDate = getFirstOfCurrentMonthToCurrentDateTime();
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<BudgetDTOI> allBudget = budgetRepository
                .findByUserUserIdAndBudgetDateBetweenOrderByBudgetIdAsc(userId, fromDate, toDate);

        if (allBudget.isEmpty()) {
            log.warn("BudgetImpl.getBudgetByUserId Method : {}", MessageConstants.BUDGET_IS_EMPTY);
            response.setMessage(MessageConstants.CAN_NOT_FIND_BUDGET);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<ExpenseDTOI> allExpenses = expenseRepository
                .findByUserUserIdAndExpenseDateBetweenOrderByExpenseIdDesc(userId, fromDate, toDate);
        List<BudgetCategoryDTOI> allBudgetCategories = budgetCategoryRepository.findBudgetCategoriesByUserId(userId);

        allBudgetCategories.forEach(budgetCategory -> {
            BudgeExpenseDTO budgeExpenseDTO = new BudgeExpenseDTO();
            budgeExpenseDTO.setBudgetCategory(budgetCategory.getBudgetCategoryName());
            AtomicReference<Double> expenseAmount = new AtomicReference<>(0.0);
            allExpenses.forEach(expense -> {
                if (Objects.equals(budgetCategory.getBudgetCategoryId(),
                        expense.getBudgetCategory().getBudgetCategoryId())) {
                    expenseAmount.updateAndGet(value -> value + expense.getExpenseAmount());
                    budgeExpenseDTO.setAmount(expenseAmount.get());
                }
            });
            budgetExpenseList.add(budgeExpenseDTO);
        });

        allBudget.forEach(budgetDTOI -> {
                    BudgetDTO budgetDTO = new BudgetDTO();
                    budgetDTO.setBudgetCategoryId(budgetDTOI.getBudgetCategory().getBudgetCategoryId());
                    budgetDTO.setBudgetCategoryName(budgetDTOI.getBudgetCategory().getBudgetCategoryName());
                    Double budgetAmount = budgetDTOI.getBudgetAmount();

                    budgetDTO.setEstimatedBudget(0.00);
                    budgetDTO.setEstimatedBudget(budgetAmount);

                    AtomicReference<Double> tempExpense = new AtomicReference<>(0.0);
                    AtomicReference<Double> tempBudgetAmount = new AtomicReference<>(budgetAmount);
                    budgetExpenseList.forEach(budgeExpense -> {
                        if (budgeExpense.getBudgetCategory()
                                .equals(budgetDTOI.getBudgetCategory().getBudgetCategoryName())) {
                            Double expense = budgeExpense.getAmount();
                            if (expense != null && budgetAmount != null) {
                                tempExpense.set(expense);
                                tempBudgetAmount.set(budgetAmount);
                            }
                        }
                    });
                    budgetDTO.setExpense(tempExpense.get());
                    budgetDTO.setRemainingBudget(tempBudgetAmount.get() - tempExpense.get());
                    budgetDTOList.add(budgetDTO);
                }
        );

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
     * @return ValidatorDTO
     * @author Lali..
     */
    private ValidatorDTO isValidateBudget(RequestBudgetDTO budgetDTO) {
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
