package com.lali.financial.habits.management.service.util;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: CommonUtilities
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.FromToDateDTO;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lali.financial.habits.management.service.constants.MessageConstants.ACCESSED;

@Slf4j
public class CommonUtilities {

    /**
     * The method checked null, empty & blank value by string
     *
     * @param value
     * @return boolean
     * @author Lali..
     */
    public static boolean isNullEmptyBlank(String value) {
        log.info("CommonUtilities.isNullEmptyBlank Method : {}", ACCESSED);
        if (value == null || value.isEmpty() || value.isBlank()) {
            return true;
        }
        return false;
    }

    /**
     * The method validate email address
     *
     * @param email
     * @return boolean
     * @author Lali..
     */
    public static boolean isValidEmailAddress(String email) {
        log.info("CommonUtilities.isValidEmailAddress Method : {}", ACCESSED);
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * The method convert string date to LocalDateTime object by date & formatter
     *
     * @param date
     * @param formatter
     * @return LocalDateTime
     * @author Lali..
     */
    public static LocalDateTime convertStringToLocalDateTime(String date, DateTimeFormatter formatter) {
        log.info("CommonUtilities.convertStringToLocalDateTime Method : {}", ACCESSED);
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * The method convert LocalDateTime to string date object by date & formatter
     *
     * @param date
     * @param formatter
     * @return
     * @author Lali..
     */
    public static String convertLocalDateTimeToString(LocalDateTime date, DateTimeFormatter formatter) {
        log.info("CommonUtilities.convertLocalDateTimeToString Method : {}", ACCESSED);
        return formatter.format(date);
    }

    /**
     * The method provide a date pattern -> yyyy-MM-dd HH:mm:ss
     *
     * @return DateTimeFormatter
     * @author Lali..
     */
    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        log.info("CommonUtilities.getDateTimeFormatter Method : {}", ACCESSED);
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * The method provide formatted Value as ywo decimal point
     *
     * @param value
     * @return
     * @author Lali..
     */
    public static String formatDoubleToStringTwoDecimalPoint(Double value) {
        log.info("CommonUtilities.formatDoubleToStringTwoDecimalPoint Method : {}", ACCESSED);
        return String.format("%.2f", value);
    }

    /**
     * The method provide 1st of month to today fromDate & toDate
     *
     * @return FromToDateDTO
     * @author Lali..
     */
    public static FromToDateDTO getFirstOfCurrentMonthToCurrentDateTime() {
        log.info("CommonUtilities.getFirstOfCurrentMonthToCurrentDateTime Method : {}", ACCESSED);
        LocalDateTime fromDate = LocalDateTime
                .of(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1), LocalTime.of(0, 0, 0));
        LocalDateTime toDate = LocalDateTime.now();

        return FromToDateDTO.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
    }

    /**
     * The method provide 1st of month to today fromDate & toDate
     *
     * @return FromToDateDTO
     * @author Lali..
     */
    public static FromToDateDTO getFromAndToDateByMonth(Integer month) {
        log.info("CommonUtilities.getFromAndToDateByMonth Method : {}", ACCESSED);

        LocalDateTime fromDate = LocalDateTime
                .of(LocalDate.of(LocalDate.now().getYear(), month, 1), LocalTime.of(0, 0, 0));

        YearMonth yearMonth = YearMonth.from(fromDate);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime toDate = LocalDateTime
                .of(endOfMonth, LocalTime.of(23, 59, 59));

        return FromToDateDTO.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
    }


    /**
     * The method provide "from date" and "to date" of  current date by provided year and month
     *
     * @param year
     * @param month
     * @return FromToDateDTO
     * @author Lali..
     */
    public static FromToDateDTO getFromAndToDateByMonthAndYear(Integer year, Integer month) {
        log.info("CommonUtilities.getFromAndToDateByMonth Method : {}", ACCESSED);

        LocalDateTime fromDate = LocalDateTime
                .of(LocalDate.of(year, month, 1), LocalTime.of(0, 0, 0));

        LocalDateTime toDate = LocalDateTime.now(ZoneId.of("Asia/Colombo"));

        return FromToDateDTO.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
    }
}
