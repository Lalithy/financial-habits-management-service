package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: IncomeService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestExpensesCategoryDTO;
import org.springframework.http.ResponseEntity;

public interface ExpensesService {

    /**
     * The method creates a expenses category
     *
     * @param expensesCategoryDTO -> {expensesCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    ResponseEntity<String> addExpensesCategory(RequestExpensesCategoryDTO expensesCategoryDTO);
}
