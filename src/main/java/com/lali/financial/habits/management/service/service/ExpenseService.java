package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: ExpenseService
 * ==================================================
 **/


import com.lali.financial.habits.management.service.dto.ExpenseDTO;
import com.lali.financial.habits.management.service.dto.RequestExpenseDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.dtoi.ExpenseDTOI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpenseService {

    /**
     * The method add an expense
     *
     * @param expenseDTO -> {expenseDetails, expenseAmount, expenseDate, location, budgetCategoryId, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> addExpense(RequestExpenseDTO expenseDTO);

    /**
     * The method provide all expense by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getExpenseByUserId(Integer userId);

    /**
     * The method provide list of formatted expenses by expenses list
     *
     * @param allExpenses
     * @return List<ExpenseDTO>
     * @author Lali..
     */
    List<ExpenseDTO> getExpenseList(List<ExpenseDTOI> allExpenses);

    /**
     * The method delete an expense expense id
     *
     * @param expenseId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> removeExpenseByUserId(Integer expenseId);
}
