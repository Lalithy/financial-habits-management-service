package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/13/2023
 * Project: financial-habits-management-service
 * Description: ResponseDTO
 * ==================================================
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4381779974140973051L;

    private String message;
    private Object details;
    private HttpStatus status;
    private LocalDateTime timestamp;

}
