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
import org.springframework.stereotype.Repository;

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
    List<ExpenseDTOI> findByUserUserId(Integer userId);
    
}
