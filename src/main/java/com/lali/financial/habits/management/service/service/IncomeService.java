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
     * @param incomeDTO
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> addIncome(RequestIncomeDTO incomeDTO);
}
