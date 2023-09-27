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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    List<SavingsDTOI> findByUserUserIdOrderBySavingsIdDesc(Integer userId);

    /**
     * The method delete saving by savings id
     *
     * @param savingsId
     * @author Lali..
     */
    @Modifying
    @Transactional
    @Query("delete from Savings a where a.savingsId=?1")
    void deleteBySavingsId(Long savingsId);

    /**
     * The method provide saving id by user id, between saving date
     *
     * @param userId
     * @param fromDate
     * @param toDate
     * @return List<Long>
     * @author Lali..
     */
    @Query("select a.savingsId from Savings a where a.user.userId = ?1 and a.savingsDate between ?2 and ?3")
    List<Long> findByUserIdAndSavingsDateBetween(Integer userId, LocalDateTime fromDate, LocalDateTime toDate);


}
