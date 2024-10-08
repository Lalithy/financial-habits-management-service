package com.lali.financial.habits.management.service.repository;

/* ==================================================
 * Author: Lali..
 * Created Date: 9/9/2023
 * Project: financial-habits-management-service
 * Description: LocationRepository
 * ==================================================
 **/

import com.lali.financial.habits.management.service.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    /**
     * The method checked exist recorde by location name
     *
     * @param locationName
     * @return boolean
     * @author Lali..
     */
    boolean existsByLocationNameIgnoreCase(String locationName);

    /**
     * The method provide a location object by location name
     *
     * @param locationName
     * @return Optional<Location>
     * @author Lali..
     */
    Optional<Location> findByLocationName(String locationName);
}
