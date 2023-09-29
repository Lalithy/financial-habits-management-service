package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetCategoryService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BudgetCategoryService {

    /**
     * The method creates a budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> addBudgetCategory(RequestBudgetCategoryDTO budgetCategoryDTO);

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

    /**
     * The method provide all budget categories by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getBudgetCategoriesByUserId(Integer userId);
}
