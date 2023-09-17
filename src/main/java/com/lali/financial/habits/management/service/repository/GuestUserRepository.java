package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: GuestUserRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.dto.DTOI.UserDTOI;
import com.lali.financial.habits.management.service.entity.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestUserRepository extends JpaRepository<GuestUser, Integer> {

    /**
     * The method checked existing user by email
     *
     * @param email
     * @return boolean
     * @author Lali..
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * The method provide a user by email and is active status
     *
     * @param email
     * @param status
     * @return
     * @author Lali..
     */
    UserDTOI findGuestUserByEmailAndIsActive(String email, boolean status);

}
