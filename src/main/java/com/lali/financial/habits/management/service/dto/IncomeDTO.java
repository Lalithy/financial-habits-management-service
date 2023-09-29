package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/24/2023
 * Project: financial-habits-management-service
 * Description: IncomeDTO
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
public class IncomeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8661516330765448964L;
    private Long incomeId;
    private String incomeDetails;
    private Double incomeAmount;
    private String incomeDate;

}
