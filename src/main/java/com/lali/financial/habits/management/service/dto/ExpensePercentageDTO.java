package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/29/2023
 * Project: financial-habits-management-service
 * Description: ExpensePercentageDTO
 * ==================================================
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpensePercentageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -930344823499706209L;
    private String budgetCategory;
    private Double expensePercentage;
}
