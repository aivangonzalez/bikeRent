
package com.app.bikeRent.repositories;

import com.app.bikeRent.entities.Bike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, String> {
    
    @Query("SELECT b FROM Bike b WHERE b.model= :model")
    public List<Bike> searchByModel(@Param("model") String model);
}
