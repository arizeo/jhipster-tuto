package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AnimalCarer;
import com.mycompany.myapp.repository.AnimalCarerRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AnimalCarer.
 */
@RestController
@RequestMapping("/api")
public class AnimalCarerResource {

    private final Logger log = LoggerFactory.getLogger(AnimalCarerResource.class);

    private static final String ENTITY_NAME = "animalCarer";

    private final AnimalCarerRepository animalCarerRepository;

    public AnimalCarerResource(AnimalCarerRepository animalCarerRepository) {
        this.animalCarerRepository = animalCarerRepository;
    }

    /**
     * POST  /animal-carers : Create a new animalCarer.
     *
     * @param animalCarer the animalCarer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new animalCarer, or with status 400 (Bad Request) if the animalCarer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/animal-carers")
    @Timed
    public ResponseEntity<AnimalCarer> createAnimalCarer(@Valid @RequestBody AnimalCarer animalCarer) throws URISyntaxException {
        log.debug("REST request to save AnimalCarer : {}", animalCarer);
        if (animalCarer.getId() != null) {
            throw new BadRequestAlertException("A new animalCarer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalCarer result = animalCarerRepository.save(animalCarer);
        return ResponseEntity.created(new URI("/api/animal-carers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /animal-carers : Updates an existing animalCarer.
     *
     * @param animalCarer the animalCarer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated animalCarer,
     * or with status 400 (Bad Request) if the animalCarer is not valid,
     * or with status 500 (Internal Server Error) if the animalCarer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/animal-carers")
    @Timed
    public ResponseEntity<AnimalCarer> updateAnimalCarer(@Valid @RequestBody AnimalCarer animalCarer) throws URISyntaxException {
        log.debug("REST request to update AnimalCarer : {}", animalCarer);
        if (animalCarer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalCarer result = animalCarerRepository.save(animalCarer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, animalCarer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /animal-carers : get all the animalCarers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of animalCarers in body
     */
    @GetMapping("/animal-carers")
    @Timed
    public ResponseEntity<List<AnimalCarer>> getAllAnimalCarers(Pageable pageable) {
        log.debug("REST request to get a page of AnimalCarers");
        Page<AnimalCarer> page = animalCarerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/animal-carers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /animal-carers/:id : get the "id" animalCarer.
     *
     * @param id the id of the animalCarer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the animalCarer, or with status 404 (Not Found)
     */
    @GetMapping("/animal-carers/{id}")
    @Timed
    public ResponseEntity<AnimalCarer> getAnimalCarer(@PathVariable Long id) {
        log.debug("REST request to get AnimalCarer : {}", id);
        Optional<AnimalCarer> animalCarer = animalCarerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(animalCarer);
    }

    /**
     * DELETE  /animal-carers/:id : delete the "id" animalCarer.
     *
     * @param id the id of the animalCarer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/animal-carers/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnimalCarer(@PathVariable Long id) {
        log.debug("REST request to delete AnimalCarer : {}", id);

        animalCarerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
