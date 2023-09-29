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
import com.lali.financial.habits.management.service.entity.GuestUser;
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
     * @author Lali..
     */
    ResponseEntity<ResponseDTO> loginUser(RequestUserLoginDTO userDTO);

    /**
     * The method provide a user by user id¿
     *
     * @param userId
     * @return GuestUser
     * @author Lali..
     */
    GuestUser findUserById(Integer userId);

}
