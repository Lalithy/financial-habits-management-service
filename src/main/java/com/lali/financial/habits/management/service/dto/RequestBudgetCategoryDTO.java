package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: RequestBudgetCategoryDTO
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
public class RequestBudgetCategoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8935051445667381498L;

    private Integer budgetCategoryId;
    private String budgetCategoryName;

}
