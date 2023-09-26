package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: IncomeRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.dtoi.IncomeDTOI;
import com.lali.financial.habits.management.service.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    /**
     * The method provide incomes by user id & between income date
     *
     * @param userId
     * @return List<Expense>
     * @author Lali..
     */
    List<IncomeDTOI> findByUserUserIdAndIncomeDateBetweenOrderByIncomeIdDesc(Integer userId, LocalDateTime fromDate, LocalDateTime toDate);

    /**
     * The method delete income by income id
     *
     * @param incomeId
     * @author Lali..
     */
    @Modifying
    @Transactional
    @Query("delete from Income a where a.incomeId=?1")
    void deleteByIncomeId(Long incomeId);
}
