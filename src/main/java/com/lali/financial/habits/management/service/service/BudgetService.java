package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BudgetService {

    /**
     * The method creates a budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    ResponseEntity<String> addBudgetCategory(RequestBudgetCategoryDTO budgetCategoryDTO);

    /**
     * The method provide all budget categories
     *
     * @returnResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getAllBudgetCategories();

    /**
     * The method find budget category by ID
     *
     * @param budgetCategoryId
     * @return BudgetCategory
     * @author Lali..
     */
    BudgetCategory findBudgetCategoryById(Integer budgetCategoryId);

    /**
     * The method find all budget categories
     *
     * @returnList<Integer>
     * @author Lali..
     */
    List<Integer> findAllCategoryID();

}
