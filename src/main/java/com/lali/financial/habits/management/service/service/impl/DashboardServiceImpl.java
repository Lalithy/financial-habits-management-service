package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/27/2023
 * Project: financial-habits-management-service
 * Description: DashboardServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.BudgeExpenseDTO;
import com.lali.financial.habits.management.service.dto.ExpensesStatisticsDTO;
import com.lali.financial.habits.management.service.dto.FromToDateDTO;
import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetCategoryDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.ExpenseDTOI;
import com.lali.financial.habits.management.service.repository.BudgetCategoryRepository;
import com.lali.financial.habits.management.service.repository.ExpenseRepository;
import com.lali.financial.habits.management.service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.lali.financial.habits.management.service.util.CommonUtilities.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseRepository expenseRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;

    /**
     * The method provide details of current month expenses for chart by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getExpensesChartDataByUserId(Integer userId) {

        log.info("DashboardServiceImpl.getExpensesChartDataByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        List<BudgeExpenseDTO> budgetExpenseList = new ArrayList<>();

        FromToDateDTO fromToDate = getFirstOfCurrentMonthToCurrentDateTime();
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<ExpenseDTOI> allExpenses = getExpensesByUserAndBetweenDate(userId, fromDate, toDate);
        List<BudgetCategoryDTOI> allBudgetCategories = budgetCategoryRepository.findBudgetCategoriesByUserId(userId);

        allBudgetCategories.forEach(budgetCategory -> {
            BudgeExpenseDTO budgeExpense = getBudgeExpense(allExpenses, budgetCategory);
            budgetExpenseList.add(budgeExpense);
        });

        response.setMessage(MessageConstants.FOUND_EXPENSES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(budgetExpenseList);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }

    /**
     * The method provide budget expense
     *
     * @param allExpenses
     * @param budgetCategory
     * @return BudgeExpenseDTO
     * @author Lali..
     */
    private static BudgeExpenseDTO getBudgeExpense(List<ExpenseDTOI> allExpenses, BudgetCategoryDTOI budgetCategory) {
        log.info("DashboardServiceImpl.getBudgeExpense Method : {}", MessageConstants.ACCESSED);
        BudgeExpenseDTO budgeExpenseDTO = new BudgeExpenseDTO();
        budgeExpenseDTO.setBudgetCategory(budgetCategory.getBudgetCategoryName());
        AtomicReference<Double> expenseAmount = new AtomicReference<>(0.0);
        budgeExpenseDTO.setAmount(0.0);
        allExpenses.forEach(expense -> {
            if (Objects.equals(budgetCategory.getBudgetCategoryId(),
                    expense.getBudgetCategory().getBudgetCategoryId())) {
                expenseAmount.updateAndGet(value -> value + expense.getExpenseAmount());
                budgeExpenseDTO.setAmount(expenseAmount.get());
            }
        });
        return budgeExpenseDTO;
    }

    /**
     * The method provide list of expenses by user and between two dates
     *
     * @param userId
     * @param fromDate
     * @param toDate
     * @return List<ExpenseDTOI>
     * @author Lali..
     */
    private List<ExpenseDTOI> getExpensesByUserAndBetweenDate(Integer userId, LocalDateTime fromDate, LocalDateTime toDate) {
        log.info("DashboardServiceImpl.getExpensesByUserAndBetweenDate Method : {}", MessageConstants.ACCESSED);
        return expenseRepository
                .findByUserUserIdAndExpenseDateBetweenOrderByExpenseIdDesc(userId, fromDate, toDate);
    }

    /**
     * The method provide details of expenses statistics for last six months by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getExpensesStatisticsLastSixMonthsByUserId(Integer userId) {

        log.info("DashboardServiceImpl.getExpensesStatisticsLastSixMonthsByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        List<ExpensesStatisticsDTO> expensesStatisticsList = new ArrayList<>();

        YearMonth yearMonth = YearMonth.now(ZoneId.of("Asia/Colombo"));
        int lastSixMonthYear = yearMonth.minusMonths(6).getYear();
        int lastSixMonth = yearMonth.minusMonths(6).getMonthValue();

        FromToDateDTO fromToDate = getFromAndToDateByMonthAndYear(lastSixMonthYear, lastSixMonth);
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<ExpenseDTOI> allExpensesLastSixMonths = getExpensesByUserAndBetweenDate(userId, fromDate, toDate);

        for (int i = 5; i >= 0; i--) {
            YearMonth date = YearMonth.now().minusMonths(i);
            String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            int monthValue = date.getMonthValue();

            FromToDateDTO tempFromToDate = getFromAndToDateByMonth(monthValue);
            List<ExpenseDTOI> tempExpenseList = getExpenseByMonth(allExpensesLastSixMonths, tempFromToDate);
            double totalExpenses = getTotalExpenses(tempExpenseList);
            ExpensesStatisticsDTO expense = getExpensesStatistics(monthName, totalExpenses);
            expensesStatisticsList.add(expense);
        }

        setDescendingOrderExpensesStatistics(expensesStatisticsList);

        response.setMessage(MessageConstants.FOUND_EXPENSES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(expensesStatisticsList);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    /**
     * The method set expense statistics lis order by descending
     *
     * @param expensesStatisticsList
     * @author Lali..
     */
    private void setDescendingOrderExpensesStatistics(List<ExpensesStatisticsDTO> expensesStatisticsList) {
        log.info("DashboardServiceImpl.setDescendingOrderExpensesStatistics Method : {}", MessageConstants.ACCESSED);
        Collections.reverse(expensesStatisticsList);
    }

    /**
     * The method provide expenses statistics by month & total expenses
     *
     * @param monthName
     * @param totalExpenses
     * @return ExpensesStatisticsDTO
     * @author Lali..
     */
    private ExpensesStatisticsDTO getExpensesStatistics(String monthName, double totalExpenses) {
        log.info("DashboardServiceImpl.buildExpensesStatistics Method : {}", MessageConstants.ACCESSED);
        return ExpensesStatisticsDTO.builder()
                .month(monthName)
                .expenseTotal(totalExpenses)
                .build();
    }

    /**
     * The method provide list expenses of expense by month
     *
     * @param allExpensesLastSixMonths
     * @param fromToDate
     * @return List<ExpenseDTOI>
     * @author Lali..
     */
    private List<ExpenseDTOI> getExpenseByMonth(List<ExpenseDTOI> allExpensesLastSixMonths, FromToDateDTO fromToDate) {
        log.info("DashboardServiceImpl.getExpenseByMonth Method : {}", MessageConstants.ACCESSED);
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        return allExpensesLastSixMonths.stream()
                .filter(expense -> expense.getExpenseDate().isAfter(fromDate)
                        && expense.getExpenseDate().isBefore(toDate)
                        || expense.getExpenseDate().isEqual(fromDate)
                        || expense.getExpenseDate().isEqual(toDate))
                .toList();
    }

    /**
     * The method provide total expense by expense list
     *
     * @param tempExpenseList
     * @return double
     * @author Lali..
     */
    private double getTotalExpenses(List<ExpenseDTOI> tempExpenseList) {
        log.info("DashboardServiceImpl.getTotalExpenses Method : {}", MessageConstants.ACCESSED);
        return tempExpenseList.stream()
                .mapToDouble(ExpenseDTOI::getExpenseAmount)
                .sum();
    }
}
