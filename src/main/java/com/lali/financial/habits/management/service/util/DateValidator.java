package com.lali.financial.habits.management.service.util;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/18/2023
 * Project: financial-habits-management-service
 * Description: DateValidator
 * ==================================================
 **/

public interface DateValidator {

    /**
     * The method validate LocalDateTime by string date
     *
     * @param dateString
     * @return boolean
     * @author Lali..
     */
    boolean isValid(String dateString);

}
