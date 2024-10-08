package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/26/2023
 * Project: financial-habits-management-service
 * Description: ReportServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.ExpenseDTO;
import com.lali.financial.habits.management.service.dto.FromToDateDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.dtoi.ExpenseDTOI;
import com.lali.financial.habits.management.service.repository.ExpenseRepository;
import com.lali.financial.habits.management.service.service.ExpenseService;
import com.lali.financial.habits.management.service.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lali.financial.habits.management.service.util.CommonUtilities.getDatesByMonthAndYear;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;

    /**
     * The method provide expense incomes by user id & requested a month
     *
     * @param userId
     * @param month
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getExpenseDetailReport(Integer userId, Integer month) {

        log.info("ReportServiceImpl.getExpenseDetailReport Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();

        LocalDate localDate = LocalDate.now();
        int currentYear = localDate.getYear();

        FromToDateDTO fromToDate = getDatesByMonthAndYear(currentYear, month);
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<ExpenseDTOI> allExpenses = expenseRepository
                .findByUserUserIdAndExpenseDateBetweenOrderByExpenseIdDesc(userId, fromDate, toDate);

        if (allExpenses.isEmpty()) {
            log.warn("ReportServiceImpl.getExpenseDetailReport Method : {}", MessageConstants.EXPENSES_IS_EMPTY);
            response.setMessage(MessageConstants.CAN_NOT_FIND_EXPENSES);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<ExpenseDTO> expenseList = expenseService.getExpenseList(allExpenses);

        response.setMessage(MessageConstants.FOUND_EXPENSES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(expenseList);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    /**
     * The method provide previous monthList fromCurrent month
     *
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getPreviousMonthListFromCurrentMonth() {

        log.info("ReportServiceImpl.getPreviousMonthListFromCurrentMonth Method : {}", MessageConstants.ACCESSED);

        ResponseDTO response = new ResponseDTO();
        int monthValue = LocalDate.now().getMonthValue();
        List<String> months = Arrays
                .asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        List<String> filteredMonths = new ArrayList<>();

        for (int i = 0; i < monthValue; i++) {
            filteredMonths.add(months.get(i));
        }

        response.setMessage(MessageConstants.FOUND_MONTHS);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(filteredMonths);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}
