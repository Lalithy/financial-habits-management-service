package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/24/2023
 * Project: financial-habits-management-service
 * Description: IncomeResponseDTO
 * ==================================================
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 696165170598410310L;

    private String message;
    private String totalAmount;
    private Object details;
    private LocalDateTime timestamp;
}
