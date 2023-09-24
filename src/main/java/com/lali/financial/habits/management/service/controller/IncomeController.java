package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/17/2023
 * Project: financial-habits-management-service
 * Description: IncomeController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestIncomeDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/income")
@RequiredArgsConstructor
@Slf4j
public class IncomeController {

    private final IncomeService incomeService;

    /**
     * The API add an income
     *
     * @param incomeDTO -> {incomeDetails, incomeAmount, incomeDate, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addIncome(@Valid @RequestBody RequestIncomeDTO incomeDTO) {
        log.info("IncomeController.addIncome API : {}", MessageConstants.ACCESSED);
        return incomeService.addIncome(incomeDTO);
    }

    /**
     * The API provide all incomes by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @GetMapping("/get-by-user")
    public ResponseEntity<ResponseDTO> getIncomesByUserId(@Valid @RequestParam Integer userId) {
        log.info("IncomeController.getIncomesByUserId API : {}", MessageConstants.ACCESSED);
        return incomeService.getIncomesByUserId(userId);
    }

    /**
     * The API delete an income by income id
     *
     * @param incomeId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDTO> removeIncomeByUserId(@Valid @RequestParam Long incomeId) {
        log.info("IncomeController.removeIncomeByUserId API : {}", MessageConstants.ACCESSED);
        return incomeService.removeIncomeByUserId(incomeId);
    }

}
