package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/17/2023
 * Project: financial-habits-management-service
 * Description: RequestIncomeDTO
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
public class RequestIncomeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7737512323550735975L;

    private String incomeDetails;
    private Double incomeAmount;
    private String incomeDate;
    private Integer userId;

}
