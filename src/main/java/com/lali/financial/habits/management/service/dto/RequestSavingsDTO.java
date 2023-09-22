package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: RequestSavingsDTO
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
public class RequestSavingsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3522406016073416947L;

    private String savingsDetails;
    private Double savingsAmount;
    private String savingsDate;
    private Integer userId;
}
