package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/23/2023
 * Project: financial-habits-management-service
 * Description: BudgetDTO
 * ==================================================
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8679050995821348569L;
    private Integer budgetCategoryId;
    private String budgetCategoryName;
    private Double estimatedBudget;
    private Double remainingBudget;
    private Double expense;
}
