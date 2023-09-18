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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param incomeDTO
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addIncome(@Valid @RequestBody RequestIncomeDTO incomeDTO) {
        log.info("ExpensesController.addIncome API : {}", MessageConstants.ACCESSED);
        return incomeService.addIncome(incomeDTO);
    }

}
