package com.lali.financial.habits.management.service.entity;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: Income
 * ==================================================
 **/

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incomeId;
    private String incomeDetails;
    private Double incomeAmount;
    private LocalDateTime incomeDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private GuestUser user;
}
