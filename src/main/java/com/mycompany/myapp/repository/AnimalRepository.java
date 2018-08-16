package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Animal entity.
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query(value = "select distinct animal from Animal animal left join fetch animal.animalcarers",
        countQuery = "select count(distinct animal) from Animal animal")
    Page<Animal> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct animal from Animal animal left join fetch animal.animalcarers")
    List<Animal> findAllWithEagerRelationships();

    @Query("select animal from Animal animal left join fetch animal.animalcarers where animal.id =:id")
    Optional<Animal> findOneWithEagerRelationships(@Param("id") Long id);
    
    Optional<Animal> findByName(String name);

}
