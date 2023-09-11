package com.lali.financial.habits.management.service.service.Impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/10/2023
 * Project: financial-habits-management-service
 * Description: UserServiceImpl
 * ==================================================
 **/

import com.lali.financial.habits.management.service.constants.MessageConstants;
import com.lali.financial.habits.management.service.dto.*;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.repository.GuestUserRepository;
import com.lali.financial.habits.management.service.service.UserService;
import com.lali.financial.habits.management.service.util.CommonUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final GuestUserRepository userRepository;


    /**
     * The method creates a user
     *
     * @param userDTO -> {email, password, confirmPassword}
     * @return ResponseEntity<String>
     * @author Lali..
     */
    @Override
    public ResponseEntity<String> registerUser(RequestUserDTO userDTO) {

        log.info("UserServiceImpl.registerUser Method : {}", MessageConstants.ACCESSED);
        try {
            ValidatorDTO validateUser = isValidateUser(userDTO);
            if (validateUser.isStatus()) {
                log.warn("UserServiceImpl.registerUser Method : {}", validateUser.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validateUser.getMessage());
            }

            GuestUser user = GuestUser.builder()
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .userCreateDate(LocalDateTime.now())
                    .isActive(true)
                    .build();
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(MessageConstants.SUCCESSFULLY_CREATED);

        } catch (RuntimeException exception) {
            log.error("UserServiceImpl.registerUser Method : {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.FAILED_USER_REGISTRATION);
        }
    }

    /**
     * The method provides login authentication
     *
     * @param userDTO -> {email, password}
     * @return ResponseEntity<String>
     */
    @Override
    public ResponseEntity<String> loginUser(RequestUserLoginDTO userDTO) {

        log.info("UserServiceImpl.loginUser Method : {}", MessageConstants.ACCESSED);
        UserDTOI userByEmailAndIsActive = userRepository.findGuestUserByEmailAndIsActive(userDTO.getEmail(), true);
        if (userByEmailAndIsActive.getPassword().equals(userDTO.getPassword())) {
            return ResponseEntity.ok(MessageConstants.LOGIN_SUCCESSFUL);
        } else {
            return ResponseEntity.badRequest().body(MessageConstants.AUTHENTICATION_FAILED);
        }

    }

    /**
     * The method validate a user
     *
     * @param userDTO
     * @return ValidatorDTO
     * @author Lali..
     */
    private ValidatorDTO isValidateUser(RequestUserDTO userDTO) {

        log.info("UserServiceImpl.isValidateUser Method : {}", MessageConstants.ACCESSED);
        String password = userDTO.getPassword();
        String confirmPassword = userDTO.getConfirmPassword();
        boolean isInvalidUserEmail = CommonUtilities.isNullEmptyBlank(userDTO.getEmail());
        boolean isInvalidPassword = CommonUtilities.isNullEmptyBlank(password);
        boolean isInvalidConfirmPassword = CommonUtilities.isNullEmptyBlank(confirmPassword);
        boolean existsByUserEmail = userRepository.existsByEmailIgnoreCase(userDTO.getEmail());
        boolean validEmail = !CommonUtilities.isValidEmailAddress(userDTO.getEmail());

        ValidatorDTO validatorDTO = new ValidatorDTO();
        if (existsByUserEmail) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.ALREADY_EXISTS_USER);
            return validatorDTO;
        } else if (isInvalidUserEmail || validEmail) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_EMAIL);
            return validatorDTO;
        } else if (isInvalidPassword) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_PASSWORD);
            return validatorDTO;
        } else if (isInvalidConfirmPassword) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(MessageConstants.INVALID_CONFIRM_PASSWORD);
            return validatorDTO;
        }

        boolean isConfirmPassword = !password.equals(confirmPassword);
        validatorDTO.setStatus(isConfirmPassword);
        validatorDTO.setMessage(MessageConstants.THE_PASSWORD_CONFIRMATION_DOES_NOT_MATCH);
        return validatorDTO;
    }
}
