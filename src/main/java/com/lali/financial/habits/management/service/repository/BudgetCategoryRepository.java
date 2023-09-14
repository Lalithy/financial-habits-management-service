package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetCategoryRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.DTOI.BudgetCategoryDTOI;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Integer> {

    /**
     * The method checked existing budget category by category name
     *
     * @param budgetCategoryName
     * @return boolean
     * @author Lali..
     */
    boolean existsByBudgetCategoryNameIgnoreCase(String budgetCategoryName);

    List<BudgetCategoryDTOI> findAllByOrderByBudgetCategoryName();

}
