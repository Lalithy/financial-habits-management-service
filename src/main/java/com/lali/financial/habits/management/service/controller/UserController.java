package com.lali.financial.habits.management.service.controller;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/10/2023
 * Project: financial-habits-management-service
 * Description: UserController
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.RequestUserDTO;
import com.lali.financial.habits.management.service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fhms/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * The API creates a user
     *
     * @param userDTO -> {email, password, confirmPassword}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    @PostMapping("/register_user")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RequestUserDTO userDTO) {
        log.info("UserController.registerUser API : {}", MessageConstants.ACCESSED);
        return userService.registerUser(userDTO);
    }
}
