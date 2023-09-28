package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/27/2023
 * Project: financial-habits-management-service
 * Description: DashboardController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * The API provide details of current month expenses for chart by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @GetMapping("/chart")
    public ResponseEntity<ResponseDTO> getExpensesChartDataByUserId(@Valid @RequestParam Integer userId) {
        log.info("DashboardController.getExpenseChartDataByUserId API : {}", MessageConstants.ACCESSED);
        return dashboardService.getExpensesChartDataByUserId(userId);
    }

    /**
     * The API provide details of expenses statistics for last six months by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @GetMapping("/expenses-statistics")
    public ResponseEntity<ResponseDTO> getExpensesStatisticsLastSixMonthsByUserId(@Valid @RequestParam Integer userId) {
        log.info("DashboardController.getExpensesStatisticsLastSixMonthsByUserId API : {}", MessageConstants.ACCESSED);
        return dashboardService.getExpensesStatisticsLastSixMonthsByUserId(userId);
    }

}
