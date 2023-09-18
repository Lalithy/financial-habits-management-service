package com.lali.financial.habits.management.service.util;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/18/2023
 * Project: financial-habits-management-service
 * Description: DateValidatorDateTimeFormatter
 * ==================================================
 **/

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorDateTimeFormatter implements DateValidator {
    private DateTimeFormatter dateFormatter;

    public DateValidatorDateTimeFormatter(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    /**
     * The method validate LocalDateTime by string date
     *
     * @param dateString
     * @return boolean
     * @author Lali..
     */
    @Override
    public boolean isValid(String dateString) {
        try {
            this.dateFormatter.parse(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
