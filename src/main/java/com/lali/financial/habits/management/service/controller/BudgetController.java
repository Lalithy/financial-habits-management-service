package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/budget")
@RequiredArgsConstructor
@Slf4j
public class BudgetController {

    private final BudgetService budgetService;

    /**
     * The API creates a budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addBudgetCategory(@Valid @RequestBody RequestBudgetCategoryDTO budgetCategoryDTO) {
        log.info("ExpensesController.addBudgetCategory API : {}", MessageConstants.ACCESSED);
        return budgetService.addBudgetCategory(budgetCategoryDTO);
    }

    /**
     * The API provide all budget categories
     *
     * @returnResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllBudgetCategories() {
        log.info("ExpensesController.getAllBudgetCategories API : {}", MessageConstants.ACCESSED);
        return budgetService.getAllBudgetCategories();
    }

}
