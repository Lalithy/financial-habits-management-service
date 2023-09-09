package com.lali.financial_abits_management_service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: GuestUserRepository
 * ==================================================
 **/

import com.lali.financial_abits_management_service.entity.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestUserRepository extends JpaRepository<GuestUser, Integer> {
}
