package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: ExpensesController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestExpensesCategoryDTO;
import com.lali.financial.habits.management.service.service.ExpensesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/income")
@RequiredArgsConstructor
public class ExpensesController {

    private final ExpensesService expensesService;

    /**
     * The API creates a expenses category
     *
     * @param expensesCategoryDTO -> {expensesCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    @PostMapping("/add_expenses_category")
    public ResponseEntity<String> addExpensesCategory(@Valid @RequestBody RequestExpensesCategoryDTO expensesCategoryDTO) {
        return expensesService.addExpensesCategory(expensesCategoryDTO);
    }


}
