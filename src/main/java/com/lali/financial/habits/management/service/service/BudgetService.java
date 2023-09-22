package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: BudgetService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestBudgetDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface BudgetService {

    /**
     * The method add a budget
     *
     * @param budgetDTO -> {budgetAmount, budgetDate, budgetCategoryId, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> addBudget(RequestBudgetDTO budgetDTO);

}
