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

@Slf4j
public class CommonUtilities {
    public static boolean isNullEmptyBlank(String value) {
        log.info("CommonUtilities.isNullEmptyBlank Method : {}", MessageConstants.ACCESSED);
        if (value == null || value.isEmpty() || value.isBlank()) {
            return true;
        }
        return false;
    }
}
