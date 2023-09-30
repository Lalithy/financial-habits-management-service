package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/30/2023
 * Project: financial-habits-management-service
 * Description: StatisticsDTO
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
public class StatisticsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5032517796967748349L;
    private String name;
    private double totalValue;
}
