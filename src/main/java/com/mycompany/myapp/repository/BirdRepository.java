package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Bird;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bird entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BirdRepository extends JpaRepository<Bird, Long> {

}
