package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/25/2023
 * Project: financial-habits-management-service
 * Description: BudgeExpenseDTO
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
public class BudgeExpenseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5207981845114961914L;
    private String budgetCategory;
    private Double amount;
}
