package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/23/2023
 * Project: financial-habits-management-service
 * Description: SumSavingsDTO
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
public class SumSavingsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5627922387504641418L;

    private Double sumOfSavingsAmount;

}
