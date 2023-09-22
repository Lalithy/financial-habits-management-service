package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: RequestExpenseDTO
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
public class RequestExpenseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8819321574312833697L;
    private String expenseDetails;
    private Double expenseAmount;
    private String expenseDate;
    private String location;
    private Integer budgetCategoryId;
    private Integer userId;
}
