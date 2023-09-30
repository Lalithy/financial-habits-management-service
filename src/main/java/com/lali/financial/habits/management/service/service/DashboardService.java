package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/27/2023
 * Project: financial-habits-management-service
 * Description: DashboardService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface DashboardService {

    /**
     * The method provide details of current month expenses for chart by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getExpensesChartDataByUserId(Integer userId);

    /**
     * The method provide details of expenses statistics for last six months by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getExpensesStatisticsLastSixMonthsByUserId(Integer userId);

    /**
     * The method provide details of statistics for last six months by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> getStatisticsLastSixMonthsByUserId(Integer userId);
}
