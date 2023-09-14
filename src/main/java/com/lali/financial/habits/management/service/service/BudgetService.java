package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface BudgetService {

    /**
     * The method creates a budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    ResponseEntity<String> addBudgetCategory(RequestBudgetCategoryDTO budgetCategoryDTO);

    ResponseEntity<ResponseDTO> getAllBudgetCategories();
}
