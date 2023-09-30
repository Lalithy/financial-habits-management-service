package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/27/2023
 * Project: financial-habits-management-service
 * Description: DashboardServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.CommonConstants;
import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.*;
import com.lali.financial.habits.management.service.dto.dtoi.BudgetCategoryDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.ExpenseDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.IncomeDTOI;
import com.lali.financial.habits.management.service.dto.dtoi.SavingsDTOI;
import com.lali.financial.habits.management.service.repository.BudgetCategoryRepository;
import com.lali.financial.habits.management.service.repository.ExpenseRepository;
import com.lali.financial.habits.management.service.repository.IncomeRepository;
import com.lali.financial.habits.management.service.repository.SavingsRepository;
import com.lali.financial.habits.management.service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
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
    private final IncomeRepository incomeRepository;
    private final SavingsRepository savingsRepository;

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
        List<ExpensePercentageDTO> budgetExpenseList = new ArrayList<>();

        FromToDateDTO fromToDate = getFirstOfCurrentMonthToCurrentDateTime();
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<ExpenseDTOI> allExpenses = getExpensesByUserAndBetweenDate(userId, fromDate, toDate);
        List<BudgetCategoryDTOI> allBudgetCategories = budgetCategoryRepository.findBudgetCategoriesByUserId(userId);

        allBudgetCategories.forEach(budgetCategory -> {
            ExpensePercentageDTO expensePercentage = getExpensePercentage(allExpenses, budgetCategory);
            budgetExpenseList.add(expensePercentage);
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
     * @return ExpensePercentageDTO
     * @author Lali..
     */
    private ExpensePercentageDTO getExpensePercentage(List<ExpenseDTOI> allExpenses, BudgetCategoryDTOI budgetCategory) {
        log.info("DashboardServiceImpl.getBudgeExpense Method : {}", MessageConstants.ACCESSED);
        ExpensePercentageDTO expensePercentageDTO = new ExpensePercentageDTO();
        expensePercentageDTO.setBudgetCategory(budgetCategory.getBudgetCategoryName());
        AtomicReference<Double> expenseAmount = new AtomicReference<>(0.0);
        expensePercentageDTO.setExpensePercentage(0.0);

        double totalExpenses = getTotalExpenses(allExpenses);

        allExpenses.forEach(expense -> {
            if (Objects.equals(budgetCategory.getBudgetCategoryId(),
                    expense.getBudgetCategory().getBudgetCategoryId())) {
                expenseAmount.updateAndGet(value -> value + expense.getExpenseAmount());
                double percentageExpense = calculatePercentage(expenseAmount.get(), totalExpenses);
                expensePercentageDTO.setExpensePercentage(percentageExpense);
            }
        });

        return expensePercentageDTO;
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

        YearMonth yearMonth = YearMonth.now();
        int lastSixMonthYear = yearMonth.minusMonths(6).getYear();
        int lastSixMonth = yearMonth.minusMonths(6).getMonthValue();

        FromToDateDTO fromToDate = getDatesToTodayByMonthAndYear(lastSixMonthYear, lastSixMonth);
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<ExpenseDTOI> allExpensesLastSixMonths = getExpensesByUserAndBetweenDate(userId, fromDate, toDate);

        for (int i = 5; i >= 0; i--) {
            YearMonth date = YearMonth.now().minusMonths(i);
            String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            int monthValue = date.getMonthValue();
            int year = date.getYear();

            FromToDateDTO tempFromToDate = getDatesByMonthAndYear(year, monthValue);
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
     * The method provide details of statistics for last six months by user id
     *
     * @param userId
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> getStatisticsLastSixMonthsByUserId(Integer userId) {

        log.info("DashboardServiceImpl.getStatisticsLastSixMonthsByUserId Method : {}", MessageConstants.ACCESSED);
        ResponseDTO response = new ResponseDTO();
        List<StatisticsTotalDTO> statisticsList = new ArrayList<>();

        YearMonth yearMonth = YearMonth.now();
        int lastSixMonthYear = yearMonth.minusMonths(6).getYear();
        int lastSixMonth = yearMonth.minusMonths(6).getMonthValue();

        FromToDateDTO fromToDate = getDatesToTodayByMonthAndYear(lastSixMonthYear, lastSixMonth);
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        List<ExpenseDTOI> allExpensesLastSixMonths = getExpensesByUserAndBetweenDate(userId, fromDate, toDate);

        List<IncomeDTOI> allIncomesLastSixMonths = getIncomesByUserAndBetweenDate(userId, fromDate, toDate);

        List<SavingsDTOI> allSavingsListLastSixMonths = getSavingsByUserAndBetweenDate(userId, fromDate, toDate);

        for (int i = 5; i >= 0; i--) {

            List<StatisticsDTO> allStatisticsList = new ArrayList<>();
            StatisticsTotalDTO statisticsTotal = new StatisticsTotalDTO();

            YearMonth date = YearMonth.now().minusMonths(i);
            String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            int monthValue = date.getMonthValue();
            int year = date.getYear();

            FromToDateDTO tempFromToDate = getDatesByMonthAndYear(year, monthValue);

            List<ExpenseDTOI> expenseByMonth = getExpenseByMonth(allExpensesLastSixMonths, tempFromToDate);
            double totalExpenses = getTotalExpenses(expenseByMonth);
            ExpensesStatisticsDTO expense = getExpensesStatistics(monthName, totalExpenses);
            String month = expense.getMonth();

            List<IncomeDTOI> incomesByMonth = getIncomesByMonth(allIncomesLastSixMonths, tempFromToDate);
            double totalIncomes = getTotalIncomes(incomesByMonth);

            List<SavingsDTOI> savingsByMonth = getSavingsByMonth(allSavingsListLastSixMonths, tempFromToDate);
            double totalSavings = getTotalSavings(savingsByMonth);

            addStatisticsByMonth(CommonConstants.EXPENSES, expense.getExpenseTotal(), allStatisticsList);

            addStatisticsByMonth(CommonConstants.INCOMES, totalIncomes, allStatisticsList);

            addStatisticsByMonth(CommonConstants.SAVINGS, totalSavings, allStatisticsList);

            statisticsTotal.setMonth(month);
            statisticsTotal.setStatistics(allStatisticsList);

            statisticsList.add(statisticsTotal);
        }

        setDescendingOrderStatistics(statisticsList);

        response.setMessage(MessageConstants.FOUND_EXPENSES);
        response.setStatus(HttpStatus.FOUND);
        response.setDetails(statisticsList);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    /**
     * The method add statistics by month
     *
     * @param name
     * @param amount
     * @param allStatisticsList
     * @author Lali..
     */
    private void addStatisticsByMonth(String name, Double amount, List<StatisticsDTO> allStatisticsList) {
        log.info("DashboardServiceImpl.addStaticsticByMonth Method : {}", MessageConstants.ACCESSED);
        StatisticsDTO statisticsExpense = new StatisticsDTO();
        statisticsExpense.setName(name);
        statisticsExpense.setTotalValue(amount);
        allStatisticsList.add(statisticsExpense);
    }

    /**
     * The method provide list of savings by user and between two dates
     *
     * @param userId
     * @param fromDate
     * @param toDate
     * @return List<SavingsDTOI>
     * @author Lali..
     */
    private List<SavingsDTOI> getSavingsByUserAndBetweenDate(Integer userId, LocalDateTime fromDate, LocalDateTime toDate) {
        log.info("DashboardServiceImpl.getSavingsByUserAndBetweenDate Method : {}", MessageConstants.ACCESSED);
        return savingsRepository
                .findByUserUserIdAndSavingsDateBetweenOrderBySavingsIdDesc(userId, fromDate, toDate);
    }

    /**
     * The method provide list of incomes by user and between two dates
     *
     * @param userId
     * @param fromDate
     * @param toDate
     * @return List<IncomeDTOI>
     * @author Lali..
     */
    private List<IncomeDTOI> getIncomesByUserAndBetweenDate(Integer userId, LocalDateTime fromDate, LocalDateTime toDate) {
        log.info("DashboardServiceImpl.getIncomesByUserAndBetweenDate Method : {}", MessageConstants.ACCESSED);
        return incomeRepository
                .findByUserUserIdAndIncomeDateBetweenOrderByIncomeIdDesc(userId, fromDate, toDate);
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
     * The method set statistics lis order by descending
     *
     * @param expensesStatisticsList
     * @author Lali..
     */
    private void setDescendingOrderStatistics(List<StatisticsTotalDTO> expensesStatisticsList) {
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
     * The method provide list incomes of expense by month
     *
     * @param allIncomesLastSixMonths
     * @param fromToDate
     * @return List<ExpenseDTOI>
     * @author Lali..
     */
    private List<IncomeDTOI> getIncomesByMonth(List<IncomeDTOI> allIncomesLastSixMonths, FromToDateDTO fromToDate) {
        log.info("getIncomeByMonth.getIncomesByMonth Method : {}", MessageConstants.ACCESSED);
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        return allIncomesLastSixMonths.stream()
                .filter(income -> income.getIncomeDate().isAfter(fromDate)
                        && income.getIncomeDate().isBefore(toDate)
                        || income.getIncomeDate().isEqual(fromDate)
                        || income.getIncomeDate().isEqual(toDate))
                .toList();
    }

    /**
     * The method provide list savings of expense by month
     *
     * @param allSavingsLastSixMonths
     * @param fromToDate
     * @return List<ExpenseDTOI>
     * @author Lali..
     */
    private List<SavingsDTOI> getSavingsByMonth(List<SavingsDTOI> allSavingsLastSixMonths, FromToDateDTO fromToDate) {
        log.info("getIncomeByMonth.getSavingsByMonth Method : {}", MessageConstants.ACCESSED);
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        return allSavingsLastSixMonths.stream()
                .filter(saving -> saving.getSavingsDate().isAfter(fromDate)
                        && saving.getSavingsDate().isBefore(toDate)
                        || saving.getSavingsDate().isEqual(fromDate)
                        || saving.getSavingsDate().isEqual(toDate))
                .toList();
    }

    /**
     * The method provide total expense by expense list
     *
     * @param expenseList
     * @return double
     * @author Lali..
     */
    private double getTotalExpenses(List<ExpenseDTOI> expenseList) {
        log.info("DashboardServiceImpl.getTotalExpenses Method : {}", MessageConstants.ACCESSED);
        return expenseList.stream()
                .mapToDouble(ExpenseDTOI::getExpenseAmount)
                .sum();
    }

    /**
     * The method provide total incomes by expense list
     *
     * @param incomeList
     * @return double
     * @author Lali..
     */
    private double getTotalIncomes(List<IncomeDTOI> incomeList) {
        log.info("DashboardServiceImpl.getTotalIncomes Method : {}", MessageConstants.ACCESSED);
        return incomeList.stream()
                .mapToDouble(IncomeDTOI::getIncomeAmount)
                .sum();
    }

    /**
     * The method provide total savings by expense list
     *
     * @param savingsList
     * @return double
     * @author Lali..
     */
    private double getTotalSavings(List<SavingsDTOI> savingsList) {
        log.info("DashboardServiceImpl.getTotalSavings Method : {}", MessageConstants.ACCESSED);
        return savingsList.stream()
                .mapToDouble(SavingsDTOI::getSavingsAmount)
                .sum();
    }
}
