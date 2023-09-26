package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/26/2023
 * Project: financial-habits-management-service
 * Description: ReportService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    /**
     * The method provide expense incomes by user id & requested a month
     *
     * @param userId
     * @param month
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getExpenseDetailReport(Integer userId, Integer month);
}
