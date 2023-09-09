package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: ExpensesCategoryRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.entity.ExpensesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesCategoryRepository extends JpaRepository<ExpensesCategory, Integer> {

    /**
     * The method checked existing expenses category by category name
     *
     * @param expensesCategoryName
     * @return boolean
     * @author Lali..
     */
    boolean existsByExpensesCategoryNameIgnoreCase(String expensesCategoryName);

}
