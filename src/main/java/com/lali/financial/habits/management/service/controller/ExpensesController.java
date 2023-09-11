package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: ExpensesController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import com.lali.financial.habits.management.service.service.ExpensesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/income")
@RequiredArgsConstructor
@Slf4j
public class ExpensesController {

    private final ExpensesService expensesService;

    /**
     * The API creates a budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    @PostMapping("/add_budget_category")
    public ResponseEntity<String> addBudgetCategory(@Valid @RequestBody RequestBudgetCategoryDTO budgetCategoryDTO) {
        log.info("ExpensesController.addBudgetCategory API : {}", MessageConstants.ACCESSED);
        return expensesService.addBudgetCategory(budgetCategoryDTO);
    }


}
