package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: ExpenseController
 * ==================================================
 **/


import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestExpenseDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/expense")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * The API add an expense
     *
     * @param expenseDTO -> {expenseDetails, expenseAmount, expenseDate, location, budgetCategoryId, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addExpense(@Valid @RequestBody RequestExpenseDTO expenseDTO) {
        log.info("ExpensesController.addExpense API : {}", MessageConstants.ACCESSED);
        return expenseService.addExpense(expenseDTO);
    }

    /**
     * The API provide all expense by user id
     *
     * @param userId
     * @returnResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @GetMapping("/by-user")
    public ResponseEntity<ResponseDTO> getExpenseByUserId(@Valid @RequestParam Integer userId) {
        log.info("ExpensesController.getExpenseByUserId API : {}", MessageConstants.ACCESSED);
        return expenseService.getExpenseByUserId(userId);
    }

}
