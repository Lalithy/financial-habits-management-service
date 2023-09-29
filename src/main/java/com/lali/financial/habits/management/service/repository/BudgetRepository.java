package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: BudgetRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.dtoi.BudgetDTOI;
import com.lali.financial.habits.management.service.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    /**
     * The method provide budget by user id
     *
     * @param userId
     * @return
     * @author Lali..
     */
    List<BudgetDTOI> findByUserUserIdAndBudgetDateBetweenOrderByBudgetIdAsc(Integer userId, LocalDateTime fromDate, LocalDateTime toDate);

    /**
     * The method provide budget category id
     *
     * @param budgetCategoryId
     * @param userId
     * @param fromDate
     * @param toDate
     * @return List<Integer>
     * @author Lali..
     */
    List<Budget> findByBudgetCategoryBudgetCategoryIdAndUserUserIdAndBudgetDateBetween(Integer budgetCategoryId, Integer userId, LocalDateTime fromDate, LocalDateTime toDate);

}
