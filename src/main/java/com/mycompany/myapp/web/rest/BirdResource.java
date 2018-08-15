package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Bird;
import com.mycompany.myapp.repository.BirdRepository;
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
 * REST controller for managing Bird.
 */
@RestController
@RequestMapping("/api")
public class BirdResource {

    private final Logger log = LoggerFactory.getLogger(BirdResource.class);

    private static final String ENTITY_NAME = "bird";

    private final BirdRepository birdRepository;

    public BirdResource(BirdRepository birdRepository) {
        this.birdRepository = birdRepository;
    }

    /**
     * POST  /birds : Create a new bird.
     *
     * @param bird the bird to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bird, or with status 400 (Bad Request) if the bird has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/birds")
    @Timed
    public ResponseEntity<Bird> createBird(@Valid @RequestBody Bird bird) throws URISyntaxException {
        log.debug("REST request to save Bird : {}", bird);
        if (bird.getId() != null) {
            throw new BadRequestAlertException("A new bird cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bird result = birdRepository.save(bird);
        return ResponseEntity.created(new URI("/api/birds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /birds : Updates an existing bird.
     *
     * @param bird the bird to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bird,
     * or with status 400 (Bad Request) if the bird is not valid,
     * or with status 500 (Internal Server Error) if the bird couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/birds")
    @Timed
    public ResponseEntity<Bird> updateBird(@Valid @RequestBody Bird bird) throws URISyntaxException {
        log.debug("REST request to update Bird : {}", bird);
        if (bird.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bird result = birdRepository.save(bird);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bird.getId().toString()))
            .body(result);
    }

    /**
     * GET  /birds : get all the birds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of birds in body
     */
    @GetMapping("/birds")
    @Timed
    public ResponseEntity<List<Bird>> getAllBirds(Pageable pageable) {
        log.debug("REST request to get a page of Birds");
        Page<Bird> page = birdRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/birds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /birds/:id : get the "id" bird.
     *
     * @param id the id of the bird to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bird, or with status 404 (Not Found)
     */
    @GetMapping("/birds/{id}")
    @Timed
    public ResponseEntity<Bird> getBird(@PathVariable Long id) {
        log.debug("REST request to get Bird : {}", id);
        Optional<Bird> bird = birdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bird);
    }

    /**
     * DELETE  /birds/:id : delete the "id" bird.
     *
     * @param id the id of the bird to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/birds/{id}")
    @Timed
    public ResponseEntity<Void> deleteBird(@PathVariable Long id) {
        log.debug("REST request to delete Bird : {}", id);

        birdRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
