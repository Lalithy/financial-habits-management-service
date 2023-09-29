package com.lali.financial.habits.management.service.service.impl;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/10/2023
 * Project: financial-habits-management-service
 * Description: UserServiceImpl
 * ==================================================
 **/


import com.lali.financial.habits.management.service.dto.*;
import com.lali.financial.habits.management.service.dto.dtoi.UserDTOI;
import com.lali.financial.habits.management.service.entity.BudgetCategory;
import com.lali.financial.habits.management.service.entity.GuestUser;
import com.lali.financial.habits.management.service.repository.GuestUserRepository;
import com.lali.financial.habits.management.service.service.BudgetCategoryService;
import com.lali.financial.habits.management.service.service.UserService;
import com.lali.financial.habits.management.service.util.CommonUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.lali.financial.habits.management.service.constants.MessageConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final GuestUserRepository userRepository;

    private final BudgetCategoryService budgetCategoryService;

    /**
     * The method creates a user
     *
     * @param userDTO -> {email, password, confirmPassword}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> registerUser(RequestUserDTO userDTO) {

        log.info("UserServiceImpl.registerUser Method : {}", ACCESSED);
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Integer> allCategoryID = budgetCategoryService.findAllCategoryID();
            ValidatorDTO validateUser = isValidateUser(userDTO, allCategoryID);
            if (validateUser.isStatus()) {
                log.warn("UserServiceImpl.registerUser Method : {}", validateUser.getMessage());
                responseDTO.setMessage(validateUser.getMessage());
                responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                responseDTO.setTimestamp(LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }

            GuestUser newUser = new GuestUser();
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(userDTO.getPassword());
            newUser.setUserCreateDate(LocalDateTime.now());
            newUser.setIsActive(true);

            newUser.getCategories()
                    .addAll(allCategoryID
                            .stream()
                            .map(budgetCategory -> {
                                BudgetCategory category = budgetCategoryService.findBudgetCategoryById(budgetCategory);
                                category.getUsers().add(newUser);
                                return category;
                            }).toList());
            GuestUser savedUser = userRepository.save(newUser);

            UserDTO userResponse = getUserResponse(savedUser.getUserId(), userDTO.getEmail());

            responseDTO.setMessage(USER_REGISTRATION_SUCCESSFUL);
            responseDTO.setStatus(HttpStatus.OK);
            responseDTO.setTimestamp(LocalDateTime.now());
            responseDTO.setDetails(userResponse);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        } catch (RuntimeException exception) {
            log.error("UserServiceImpl.registerUser Method : {}", exception.getMessage());
            responseDTO.setMessage(FAILED_USER_REGISTRATION);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }

    /**
     * The method provides login authentication
     *
     * @param userDTO -> {email, password}
     * @return ResponseEntity<ResponseDTO>
     * @author Lali..
     */
    @Override
    public ResponseEntity<ResponseDTO> loginUser(RequestUserLoginDTO userDTO) {

        log.info("UserServiceImpl.loginUser Method : {}", ACCESSED);
        UserDTOI userByEmailAndIsActive = userRepository.findGuestUserByEmailAndIsActive(userDTO.getEmail(), true);
        ResponseDTO responseDTO = new ResponseDTO();
        if (userByEmailAndIsActive != null
                && userByEmailAndIsActive.getPassword().equals(userDTO.getPassword())
                && userByEmailAndIsActive.getEmail().equals(userDTO.getEmail())) {
            Integer userId = userByEmailAndIsActive.getUserId();
            String email = userByEmailAndIsActive.getEmail();

            UserDTO userResponse = getUserResponse(userId, email);

            responseDTO.setMessage(LOGIN_SUCCESSFUL);
            responseDTO.setStatus(HttpStatus.OK);
            responseDTO.setTimestamp(LocalDateTime.now());
            responseDTO.setDetails(userResponse);
            return ResponseEntity.ok().body(responseDTO);
        } else {
            responseDTO.setMessage(AUTHENTICATION_FAILED);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setTimestamp(LocalDateTime.now());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @Override
    public GuestUser findUserById(Integer userId) {
        log.info("UserServiceImpl.findUserById Method : {}", ACCESSED);
        return userRepository.findById(userId).get();
    }

    /**
     * The method provide user response details by user id & email
     *
     * @param userId
     * @param email
     * @return
     * @author Lali..
     */
    private UserDTO getUserResponse(Integer userId, String email) {

        log.info("UserServiceImpl.getUserResponse Method : {}", ACCESSED);
        String displayName = getDisplayName(email);
        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setEmail(email);
        user.setDisplayName(displayName);
        return user;
    }

    /**
     * The method provide display name of user by email
     *
     * @param email
     * @return String
     * @author Lali..
     */
    private String getDisplayName(String email) {
        log.info("UserServiceImpl.getDisplayName Method : {}", ACCESSED);
        return Arrays.stream(email.split("\\@"))
                .map(s -> s.trim())
                .findFirst().get();
    }

    /**
     * The method validate a user
     *
     * @param userDTO
     * @param allCategoryID
     * @return ValidatorDTO
     * @author Lali..
     */
    private ValidatorDTO isValidateUser(RequestUserDTO userDTO, List<Integer> allCategoryID) {

        log.info("UserServiceImpl.isValidateUser Method : {}", ACCESSED);
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
            validatorDTO.setMessage(ALREADY_EXISTS_USER);
            return validatorDTO;
        } else if (isInvalidUserEmail || validEmail) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(INVALID_EMAIL);
            return validatorDTO;
        } else if (isInvalidPassword) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(INVALID_PASSWORD);
            return validatorDTO;
        } else if (isInvalidConfirmPassword) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(INVALID_CONFIRM_PASSWORD);
            return validatorDTO;
        } else if (allCategoryID.isEmpty()) {
            validatorDTO.setStatus(true);
            validatorDTO.setMessage(CAN_NOT_FIND_BUDGET_CATEGORIES);
            return validatorDTO;
        }

        boolean isConfirmPassword = !password.equals(confirmPassword);
        validatorDTO.setStatus(isConfirmPassword);
        validatorDTO.setMessage(PASSWORD_DOES_NOT_MATCH);
        return validatorDTO;
    }
}
