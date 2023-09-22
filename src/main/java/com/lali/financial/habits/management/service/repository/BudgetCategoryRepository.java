package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetCategoryRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.dtoi.BudgetCategoryDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetCategoryIdOnlyDTOI;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * The method provide all budget categories by ascending order by budget category id
     *
     * @return
     * @author Lali..
     */
    List<BudgetCategoryDTOI> findAllByOrderByBudgetCategoryIdAsc();

    /**
     * The method provide all budget categories by user id and ascending order by budget category id
     *
     * @return
     * @author Lali..
     */
    @Query(value = "SELECT b.budget_category_id AS budgetCategoryId, b.budget_category_name AS budgetCategoryName FROM budget_category b INNER JOIN user_budget_category u ON b.budget_category_id = u.budget_category_id WHERE user_id = ?1 ORDER BY b.budget_category_id ASC", nativeQuery = true)
    List<BudgetCategoryDTOI> findBudgetCategoriesByUserId(Integer userId);

    /**
     * The method provide only budget category id
     *
     * @return
     * @author Lali..
     */
    List<BudgetCategoryIdOnlyDTOI> findAllByOrderByBudgetCategoryId();

}
