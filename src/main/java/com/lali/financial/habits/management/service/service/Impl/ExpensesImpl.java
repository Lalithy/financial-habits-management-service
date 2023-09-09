package com.lali.financial.habits.management.service.service.Impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: ExpensesImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestExpensesCategoryDTO;
import com.lali.financial.habits.management.service.entity.ExpensesCategory;
import com.lali.financial.habits.management.service.repository.ExpensesCategoryRepository;
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

    private final ExpensesCategoryRepository expensesCategoryRepository;


    /**
     * The method creates an expenses category
     *
     * @param expensesCategoryDTO -> {expensesCategoryName}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    @Override
    public ResponseEntity<String> addExpensesCategory(RequestExpensesCategoryDTO expensesCategoryDTO) {

        log.info("ExpensesImpl.addExpensesCategory Method : {}", MessageConstants.ACCESSED);
        try {
            boolean isInvalidExpensesCategory = CommonUtilities.isNullEmptyBlank(expensesCategoryDTO.getExpensesCategoryName());
            if (isInvalidExpensesCategory) {
                log.warn("ExpensesImpl.addExpensesCategory Method : {}", MessageConstants.VALIDATION_FAILED);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.VALIDATION_FAILED);
            }

            boolean existsByExpensesCategoryName = expensesCategoryRepository.existsByExpensesCategoryNameIgnoreCase(expensesCategoryDTO.getExpensesCategoryName());
            if (existsByExpensesCategoryName) {
                log.warn("ExpensesImpl.addExpensesCategory Method : {}", MessageConstants.ALREADY_EXISTS_RECORD);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.ALREADY_EXISTS_RECORD);
            }

            ExpensesCategory expensesCategory = ExpensesCategory.builder()
                    .expensesCategoryName(expensesCategoryDTO.getExpensesCategoryName())
                    .build();
            expensesCategoryRepository.save(expensesCategory);
            return ResponseEntity.status(HttpStatus.OK).body(MessageConstants.SUCCESSFULLY_CREATED);

        } catch (RuntimeException exception) {
            log.error("ExpensesImpl.addExpensesCategory Method : {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.FAILED_INSERTING);
        }
    }

}
