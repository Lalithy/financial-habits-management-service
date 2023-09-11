package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: ExpensesService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import org.springframework.http.ResponseEntity;

public interface ExpensesService {

    /**
     * The method creates a budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    ResponseEntity<String> addBudgetCategory(RequestBudgetCategoryDTO budgetCategoryDTO);
}
