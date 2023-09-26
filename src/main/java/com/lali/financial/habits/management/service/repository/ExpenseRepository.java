package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: ExpenseRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.dtoi.ExpenseDTOI;
import com.lali.financial.habits.management.service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    /**
     * The method provide expenses by user id
     *
     * @param userId
     * @return List<Expense>
     * @author Lali..
     */
    List<ExpenseDTOI> findByUserUserIdAndExpenseDateBetweenOrderByExpenseIdDesc(Integer userId, LocalDateTime fromDate, LocalDateTime toDate);

    /**
     * The method delete expense by expense id
     *
     * @param expenseId
     * @author Lali..
     */
    @Modifying
    @Transactional
    @Query("delete from Expense a where a.expenseId=?1")
    void deleteByExpenseId(Integer expenseId);
}
