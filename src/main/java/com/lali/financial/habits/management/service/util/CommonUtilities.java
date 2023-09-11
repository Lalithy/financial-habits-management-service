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
}
