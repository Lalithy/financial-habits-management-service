package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/24/2023
 * Project: financial-habits-management-service
 * Description: ExpenseDTO
 * ==================================================
 **/

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3222755781188586299L;

    private Integer expenseId;
    private String expenseDetails;
    private Double expenseAmount;
    private String expenseDate;
    private String expenseCategory;

}
