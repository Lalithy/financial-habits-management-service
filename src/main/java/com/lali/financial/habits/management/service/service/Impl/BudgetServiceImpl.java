package com.lali.financial.habits.management.service.service.Impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetCategoryDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetCategoryIdOnlyDTOI;
import com.lali.financial.habits.management.service.dto.RequestBudgetCategoryDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import com.lali.financial.habits.management.service.repository.BudgetCategoryRepository;
import com.lali.financial.habits.management.service.repository.GuestUserRepository;
import com.lali.financial.habits.management.service.service.BudgetService;
import com.lali.financial.habits.management.service.util.CommonUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetService {

    private final BudgetCategoryRepository budgetCategoryRepository;

    private final GuestUserRepository userRepository;


    /**
     * The method creates an budget category
     *
     * @param budgetCategoryDTO -> {budgetCategoryName}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> addBudgetCategory(RequestBudgetCategoryDTO budgetCategoryDTO) {

        log.info("ExpensesImpl.addBudgetCategory Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        try {
            boolean isInvalidBudgetCategory = CommonUtilities.isNullEmptyBlank(budgetCategoryDTO.getBudgetCategoryName());
            if (isInvalidBudgetCategory) {
                log.warn("ExpensesImpl.addBudgetCategory Method : {}", MessageConstants.VALIDATION_FAILED);
                response.setMessage(MessageConstants.VALIDATION_FAILED);
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            boolean existsByBudgetCategoryName = budgetCategoryRepository.existsByBudgetCategoryNameIgnoreCase(budgetCategoryDTO.getBudgetCategoryName());
            if (existsByBudgetCategoryName) {
                log.warn("ExpensesImpl.addBudgetCategory Method : {}", MessageConstants.ALREADY_EXISTS_RECORD);
                response.setMessage(MessageConstants.ALREADY_EXISTS_RECORD);
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            BudgetCategory budgetCategory = BudgetCategory.builder()
                    .budgetCategoryName(budgetCategoryDTO.getBudgetCategoryName())
                    .build();
            budgetCategoryRepository.save(budgetCategory);

            response.setMessage(MessageConstants.SUCCESSFULLY_CREATED);
            response.setStatus(HttpStatus.OK);
            response.setTimestamp(LocalDateTime.now());
            response.setDetails(budgetCategory);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException exception) {
            log.error("ExpensesImpl.addBudgetCategory Method : {}", exception.getMessage());
            response.setMessage(MessageConstants.FAILED_INSERTING);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * The method provide all budget categories
     *
     * @returnResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getAllBudgetCategories() {

        log.info("ExpensesImpl.getAllBudgetCategories Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        List<BudgetCategoryDTOI> allBudgetCategories = budgetCategoryRepository.findAllByOrderByBudgetCategoryName();

        if (allBudgetCategories.isEmpty()) {
            log.warn("ExpensesImpl.getAllBudgetCategories Method : {}", MessageConstants.BUDGET_CATEGORY_IS_EMPTY);
            response.setMessage(MessageConstants.CAN_NOT_FIND_BUDGET_CATEGORIES);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setMessage(MessageConstants.FOUND_BUDGET_CATEGORIES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(allBudgetCategories);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }

    /**
     * The method find budget category by ID
     *
     * @param budgetCategoryId
     * @return BudgetCategory
     * @author Lali..
     */
    @Override
    public BudgetCategory findBudgetCategoryById(Integer budgetCategoryId) {
        log.info("ExpensesImpl.findBudgetCategoryById Method : {}", MessageConstants.ACCESSED);
        return budgetCategoryRepository.findById(budgetCategoryId).get();
    }

    /**
     * The method find all budget categories
     *
     * @returnList<Integer>
     * @author Lali..
     */
    @Override
    public List<Integer> findAllCategoryID() {
        log.info("ExpensesImpl.findAllCategoryID Method : {}", MessageConstants.ACCESSED);
        List<BudgetCategoryIdOnlyDTOI> budgetCategoryIdsList = budgetCategoryRepository.findAllByOrderByBudgetCategoryId();
        return budgetCategoryIdsList
                .stream()
                .map(BudgetCategoryIdOnlyDTOI::getBudgetCategoryId)
                .toList();
    }

}
