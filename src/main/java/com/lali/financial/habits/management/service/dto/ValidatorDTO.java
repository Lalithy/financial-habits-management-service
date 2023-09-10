package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/10/2023
 * Project: financial-habits-management-service
 * Description: ValidatorDTO
 * ==================================================
 **/

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7248182737913859410L;

    private boolean status;
    private String message;

}
