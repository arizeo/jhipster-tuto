package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Habitat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Habitat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {

}
