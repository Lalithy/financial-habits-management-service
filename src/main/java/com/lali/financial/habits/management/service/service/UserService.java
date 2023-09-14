package com.lali.financial.habits.management.service.service;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/10/2023
 * Project: financial-habits-management-service
 * Description: UserService
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.ResponseDTO;
import com.lali.financial.habits.management.service.dto.RequestUserDTO;
import com.lali.financial.habits.management.service.dto.RequestUserLoginDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * The method creates a user
     *
     * @param userDTO -> {email, password, confirmPassword}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> registerUser(RequestUserDTO userDTO);

    /**
     * The method provides login authentication
     *
     * @param userDTO -> {email, password}
     * @return ResponseEntity<ResponseDTO>
     */
    ResponseEntity<ResponseDTO> loginUser(RequestUserLoginDTO userDTO);
}
