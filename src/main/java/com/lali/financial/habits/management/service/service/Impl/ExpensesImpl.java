package com.lali.financial.habits.management.service.service.Impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: ExpensesImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import com.lali.financial.habits.management.service.repository.BudgetCategoryRepository;
import com.lali.financial.habits.management.service.service.ExpensesService;
import com.lali.financial.habits.management.service.util.CommonUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpensesImpl implements ExpensesService {

    private final BudgetCategoryRepository budgetCategoryRepository;


    /**
     * The method creates an budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    @Override
    public ResponseEntity<String> addBudgetCategory(RequestBudgetCategoryDTO budgetCategoryDTO) {

        log.info("ExpensesImpl.addBudgetCategory Method : {}", MessageConstants.ACCESSED);
        try {
            boolean isInvalidBudgetCategory = CommonUtilities.isNullEmptyBlank(budgetCategoryDTO.getBudgetCategoryName());
            if (isInvalidBudgetCategory) {
                log.warn("ExpensesImpl.addBudgetCategory Method : {}", MessageConstants.VALIDATION_FAILED);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.VALIDATION_FAILED);
            }

            boolean existsByBudgetCategoryName = budgetCategoryRepository.existsByBudgetCategoryNameIgnoreCase(budgetCategoryDTO.getBudgetCategoryName());
            if (existsByBudgetCategoryName) {
                log.warn("ExpensesImpl.addBudgetCategory Method : {}", MessageConstants.ALREADY_EXISTS_RECORD);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.ALREADY_EXISTS_RECORD);
            }

            BudgetCategory budgetCategory = BudgetCategory.builder()
                    .budgetCategoryName(budgetCategoryDTO.getBudgetCategoryName())
                    .build();
            budgetCategoryRepository.save(budgetCategory);
            return ResponseEntity.status(HttpStatus.OK).body(MessageConstants.SUCCESSFULLY_CREATED);

        } catch (RuntimeException exception) {
            log.error("ExpensesImpl.addBudgetCategory Method : {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.FAILED_INSERTING);
        }
    }

}
