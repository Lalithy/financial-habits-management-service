package com.lali.financial.habits.management.service.dto.dtoi;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/23/2023
 * Project: financial-habits-management-service
 * Description: IncomeDTOI
 * ==================================================
 **/

import java.time.LocalDateTime;

public interface IncomeDTOI {

    Long getIncomeId();

    String getIncomeDetails();

    Double getIncomeAmount();

    LocalDateTime getIncomeDate();
}
