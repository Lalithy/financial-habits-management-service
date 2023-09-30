package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/30/2023
 * Project: financial-habits-management-service
 * Description: StatisticsTotalDTO
 * ==================================================
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsTotalDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1256528280145380156L;
    private String month;
    private List<StatisticsDTO> statistics;
}
