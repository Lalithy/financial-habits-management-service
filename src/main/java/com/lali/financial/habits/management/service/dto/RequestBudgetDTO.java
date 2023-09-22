package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: RequestBudgetDTO
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
public class RequestBudgetDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2633698247493389610L;
    private Double budgetAmount;
    private String budgetDate;
    private Integer budgetCategoryId;
    private Integer userId;
}
