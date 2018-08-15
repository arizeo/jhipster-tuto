package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AnimalCarer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalCarer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalCarerRepository extends JpaRepository<AnimalCarer, Long> {

}
