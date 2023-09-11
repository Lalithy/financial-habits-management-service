package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/11/2023
 * Project: financial-habits-management-service
 * Description: RequestUserLoginDTO
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
public class RequestUserLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6880627048121960552L;

    private String email;
    private String password;
}
