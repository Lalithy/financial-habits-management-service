package com.lali.financial.habits.management.service.dto;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/26/2023
 * Project: financial-habits-management-service
 * Description: FromToDateDTO
 * ==================================================
 **/

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FromToDateDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 762100371238402687L;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
