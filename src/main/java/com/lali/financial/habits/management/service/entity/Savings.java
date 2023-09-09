package com.lali.financial.habits.management.service.entity;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: Savings
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
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savingsId;
    private String savingsDetails;
    private String savingsAmount;
    private LocalDateTime savingsDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private GuestUser user;

}
