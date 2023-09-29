package com.lali.financial.habits.management.service.dto.dtoi;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/23/2023
 * Project: financial-habits-management-service
 * Description: ExpenseDTOI
 * ==================================================
 **/

import com.lali.financial.habits.management.service.entity.BudgetCategory;

import java.time.LocalDateTime;

public interface ExpenseDTOI {

    Integer getExpenseId();

    String getExpenseDetails();

    Double getExpenseAmount();

    LocalDateTime getExpenseDate();

    BudgetCategory getBudgetCategory();
}
