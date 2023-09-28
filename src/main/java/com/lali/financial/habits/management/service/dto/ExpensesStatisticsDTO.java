package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/28/2023
 * Project: financial-habits-management-service
 * Description: ExpensesStatisticsDTO
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
public class ExpensesStatisticsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8442786548185819814L;
    private String month;
    private Double expenseTotal;

}
