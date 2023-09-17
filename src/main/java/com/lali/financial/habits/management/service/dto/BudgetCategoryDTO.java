package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/13/2023
 * Project: financial-habits-management-service
 * Description: BudgetCategoryDTO
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
public class BudgetCategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6000807976949273933L;

    private Integer budgetCategoryId;
    private String budgetCategoryName;

}
