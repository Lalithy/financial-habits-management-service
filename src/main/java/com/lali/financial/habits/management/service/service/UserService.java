package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/10/2023
 * Project: financial-habits-management-service
 * Description: UserService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.RequestUserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * The method creates a user
     *
     * @param userDTO -> {email, password, confirmPassword}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    ResponseEntity<String> registerUser(RequestUserDTO userDTO);

}
