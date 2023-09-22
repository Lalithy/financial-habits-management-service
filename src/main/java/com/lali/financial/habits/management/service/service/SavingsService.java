package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: SavingsService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestSavingsDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface SavingsService {

    /**
     * The method add a savings
     *
     * @param savingsDTO -> {savingsDetails, savingsAmount, savingsDate, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> addSavings(RequestSavingsDTO savingsDTO);

}
