package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/17/2023
 * Project: financial-habits-management-service
 * Description: IncomeService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestIncomeDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IncomeService {

    /**
     * The method add an income
     *
     * @param incomeDTO -> {incomeDetails, incomeAmount, incomeDate, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> addIncome(RequestIncomeDTO incomeDTO);

    /**
     * The method provide all incomes by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getIncomesByUserId(Integer userId);

    /**
     * The method delete an income by income id
     *
     * @param incomeId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> removeIncomeByUserId(Long incomeId);
}
