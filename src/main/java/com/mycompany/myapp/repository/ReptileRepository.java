package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reptile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reptile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReptileRepository extends JpaRepository<Reptile, Long> {

}
