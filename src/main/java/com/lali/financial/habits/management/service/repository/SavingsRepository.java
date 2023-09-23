package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: SavingsRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.dtoi.SavingsDTOI;
import com.lali.financial.habits.management.service.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    /**
     * The method provide expenses by user id
     *
     * @param userId
     * @return List<Expense>
     * @author Lali..
     */
    List<SavingsDTOI> findByUserUserId(Integer userId);

}
