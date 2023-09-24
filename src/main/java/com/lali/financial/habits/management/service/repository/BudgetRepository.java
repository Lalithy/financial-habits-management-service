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

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    /**
     * The API provide budget by user id
     *
     * @param userId
     * @return
     * @author Lali..
     */
    List<BudgetDTOI> findByUserUserIdOrderByBudgetIdAsc(Integer userId);

}
