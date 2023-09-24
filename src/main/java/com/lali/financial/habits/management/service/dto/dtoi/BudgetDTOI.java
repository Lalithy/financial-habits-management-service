package com.lali.financial.habits.management.service.dto.dtoi;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/23/2023
 * Project: financial-habits-management-service
 * Description: BudgetDTOI
 * ==================================================
 **/

import com.lali.financial.habits.management.service.entity.BudgetCategory;

import java.time.LocalDateTime;

public interface BudgetDTOI {

    Integer getBudgetId();

    Double getBudgetAmount();

    LocalDateTime getBudgetDate();

    BudgetCategory getBudgetCategory();

}
