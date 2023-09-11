package com.lali.financial.habits.management.service.entity;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: BudgetCategory
 * ==================================================
 **/

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer budgetCategoryId;
    private String budgetCategoryName;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<GuestUser> users;

}
