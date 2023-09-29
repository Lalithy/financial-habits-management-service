package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/26/2023
 * Project: financial-habits-management-service
 * Description: ReportController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/report")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;


    /**
     * The API provide expense incomes by user id & requested a month
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @GetMapping("/expense-detail")
    public ResponseEntity<ResponseDTO> getExpenseDetailReport(@Valid @RequestParam Integer userId, Integer month) {
        log.info("ReportController.getExpenseDetailReport API : {}", MessageConstants.ACCESSED);
        return reportService.getExpenseDetailReport(userId, month);
    }

    /**
     * The API provide previous monthList fromCurrent month
     *
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @GetMapping("/months")
    public ResponseEntity<ResponseDTO> getPreviousMonthListFromCurrentMonth() {
        log.info("ReportController.getPreviousMonthListFromCurrentMonth API : {}", MessageConstants.ACCESSED);
        return reportService.getPreviousMonthListFromCurrentMonth();
    }


}
