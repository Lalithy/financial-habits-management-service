package com.lali.financial.habits.management.service.util;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: CommonUtilities
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        log.info("CommonUtilities.isNullEmptyBlank Method : {}", MessageConstants.ACCESSED);
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
        log.info("CommonUtilities.isValidEmailAddress Method : {}", MessageConstants.ACCESSED);
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
        log.info("CommonUtilities.convertStringToLocalDateTime Method : {}", MessageConstants.ACCESSED);
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
        log.info("CommonUtilities.convertLocalDateTimeToString Method : {}", MessageConstants.ACCESSED);
        return formatter.format(date);
    }

    /**
     * The method provide a date pattern -> yyyy-MM-dd HH:mm:ss
     *
     * @return DateTimeFormatter
     * @author Lali..
     */
    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        log.info("CommonUtilities.getDateTimeFormatter Method : {}", MessageConstants.ACCESSED);
        return DateTimeFormatter.ofPattern(pattern);
    }
}
