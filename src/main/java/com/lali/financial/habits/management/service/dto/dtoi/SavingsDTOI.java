package com.lali.financial.habits.management.service.dto.dtoi;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/23/2023
 * Project: financial-habits-management-service
 * Description: SavingsDTOI
 * ==================================================
 **/

import java.time.LocalDateTime;

public interface SavingsDTOI {

    Long getSavingsId();

    String getSavingsDetails();

    Double getSavingsAmount();

    LocalDateTime getSavingsDate();
}
