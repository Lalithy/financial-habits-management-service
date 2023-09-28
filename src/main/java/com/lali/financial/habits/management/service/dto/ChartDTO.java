package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/27/2023
 * Project: financial-habits-management-service
 * Description: ChartDTO
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
public class ChartDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3765876873920857754L;

    private Integer expenseCategoryId;
    private String expenseCategoryName;
    private Double expenseTotal;
}
