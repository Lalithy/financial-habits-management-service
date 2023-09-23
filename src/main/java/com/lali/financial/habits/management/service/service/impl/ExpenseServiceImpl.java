package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: ExpenseServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestExpenseDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.ValidatorDTO;
import com.lali.financial.habits.management.service.dto.dtoi.ExpenseDTOI;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import com.lali.financial.habits.management.service.entity.Expense;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.entity.Location;
import com.lali.financial.habits.management.service.repository.ExpenseRepository;
import com.lali.financial.habits.management.service.repository.LocationRepository;
import com.lali.financial.habits.management.service.service.BudgetCategoryService;
import com.lali.financial.habits.management.service.service.ExpenseService;
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
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetCategoryService budgetCategoryService;
    private final UserService userService;
    private final LocationRepository locationRepository;

    /**
     * The method add an expense
     *
     * @param expenseDTO -> {expenseDetails, expenseAmount, expenseDate, location, budgetCategoryId, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> addExpense(RequestExpenseDTO expenseDTO) {

        log.info("ExpenseServiceImpl.addExpense Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        try {

            DateTimeFormatter formatter = CommonUtilities.getDateTimeFormatter();
            ValidatorDTO validateExpense = isValidateExpense(expenseDTO, formatter);
            if (validateExpense.isStatus()) {
                log.warn("ExpenseServiceImpl.addExpense Method : {}", MessageConstants.VALIDATION_FAILED);
                response.setMessage(validateExpense.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            LocalDateTime expenseDateTime = getExpenseDateTime(expenseDTO.getExpenseDate(), formatter);

            GuestUser user = userService.findUserById(expenseDTO.getUserId());

            Location location = saveLocation(expenseDTO.getLocation());

            BudgetCategory budgetCategory = budgetCategoryService.findBudgetCategoryById(expenseDTO.getBudgetCategoryId());

            Expense expense = Expense.builder()
                    .expenseDetails(expenseDTO.getExpenseDetails())
                    .expenseAmount(expenseDTO.getExpenseAmount())
                    .expenseDate(expenseDateTime)
                    .user(user)
                    .location(location)
                    .budgetCategory(budgetCategory)
                    .build();
            expenseRepository.save(expense);

            response.setMessage(MessageConstants.SUCCESSFULLY_CREATED);
            response.setStatus(HttpStatus.OK);
            response.setTimestamp(LocalDateTime.now());
            response.setDetails(expense);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException exception) {
            log.error("ExpenseServiceImpl.addExpense Method : {}", exception.getMessage());
            response.setMessage(MessageConstants.FAILED_INSERTING);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * The method provide all expense by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getExpenseByUserId(Integer userId) {

        log.info("ExpensesImpl.getExpensesByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        List<ExpenseDTOI> allExpenses = expenseRepository.findByUserUserId(userId);

        if (allExpenses.isEmpty()) {
            log.warn("ExpensesImpl.getExpensesByUserId Method : {}", MessageConstants.EXPENSES_IS_EMPTY);
            response.setMessage(MessageConstants.CAN_NOT_FIND_EXPENSES);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setMessage(MessageConstants.FOUND_EXPENSES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(allExpenses);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    /**
     * The method if not exist save a location
     *
     * @param locationName
     * @return Location
     * @author Lali..
     */
    private Location saveLocation(String locationName) {

        log.info("ExpenseServiceImpl.saveLocation Method : {}", MessageConstants.ACCESSED);
        boolean existsLocation = locationRepository.existsByLocationNameIgnoreCase(locationName);
        if (existsLocation) {
            return locationRepository.findByLocationName(locationName)
                    .orElseThrow(() -> new RuntimeException("Location not found!"));
        }
        Location location = Location.builder()
                .locationName(locationName)
                .build();
        return locationRepository.save(location);
    }

    /**
     * The method validate expense details
     *
     * @param expenseDTO
     * @param formatter
     * @return ValidatorDTO
     * @author Lali..
     */
    private ValidatorDTO isValidateExpense(RequestExpenseDTO expenseDTO, DateTimeFormatter formatter) {
        log.info("ExpenseServiceImpl.isValidateExpense Method : {}", MessageConstants.ACCESSED);
        DateValidator validator = new DateValidatorDateTimeFormatter(formatter);
        boolean isValidExpenseDate = !validator.isValid(expenseDTO.getExpenseDate());
        ValidatorDTO validatorDTO = new ValidatorDTO();

        if (expenseDTO.getExpenseAmount() <= 0) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_EXPENSE_AMOUNT);
            return validatorDTO;
        }

        validatorDTO.setStatus(isValidExpenseDate);
        validatorDTO.setMessage(MessageConstants.INVALID_DATE);
        return validatorDTO;
    }

    /**
     * The method provide a  LocalDateTime object of expense date
     *
     * @param expenseDate
     * @param formatter
     * @return LocalDateTime
     * @author Lali..
     */
    private LocalDateTime getExpenseDateTime(String expenseDate, DateTimeFormatter formatter) {
        log.info("ExpenseServiceImpl.getExpenseDateTime Method : {}", MessageConstants.ACCESSED);
        return CommonUtilities.convertStringToLocalDateTime(expenseDate, formatter);
    }
}
