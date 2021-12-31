
package com.app.bikeRent.repositories;

import com.app.bikeRent.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, String>{
    
}
