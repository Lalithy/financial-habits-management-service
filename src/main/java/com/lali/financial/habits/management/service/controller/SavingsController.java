package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/22/2023
 * Project: financial-habits-management-service
 * Description: SavingsController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestSavingsDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.service.SavingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/savings")
@RequiredArgsConstructor
@Slf4j
public class SavingsController {

    private final SavingsService savingsService;

    /**
     * The API add a savings
     *
     * @param savingsDTO -> {savingsDetails, savingsAmount, savingsDate, userId}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addSavings(@Valid @RequestBody RequestSavingsDTO savingsDTO) {
        log.info("SavingsController.addSavings API : {}", MessageConstants.ACCESSED);
        return savingsService.addSavings(savingsDTO);
    }

}
