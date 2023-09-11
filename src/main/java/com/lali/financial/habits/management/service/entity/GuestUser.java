package com.lali.financial.habits.management.service.entity;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: User
 * ==================================================
 **/

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GuestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String email;
    private String password;
    private LocalDateTime userCreateDate;
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_expenses_category",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "expenses_category_id"))
    private Set<BudgetCategory> categories;

}
