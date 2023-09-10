package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/10/2023
 * Project: financial-habits-management-service
 * Description: RequestUserDTO
 * ==================================================
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5578179853056214731L;

    private String email;
    private String password;
    private String confirmPassword;

}
