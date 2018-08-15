package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Reptile;
import com.mycompany.myapp.repository.ReptileRepository;
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
 * REST controller for managing Reptile.
 */
@RestController
@RequestMapping("/api")
public class ReptileResource {

    private final Logger log = LoggerFactory.getLogger(ReptileResource.class);

    private static final String ENTITY_NAME = "reptile";

    private final ReptileRepository reptileRepository;

    public ReptileResource(ReptileRepository reptileRepository) {
        this.reptileRepository = reptileRepository;
    }

    /**
     * POST  /reptiles : Create a new reptile.
     *
     * @param reptile the reptile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reptile, or with status 400 (Bad Request) if the reptile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reptiles")
    @Timed
    public ResponseEntity<Reptile> createReptile(@Valid @RequestBody Reptile reptile) throws URISyntaxException {
        log.debug("REST request to save Reptile : {}", reptile);
        if (reptile.getId() != null) {
            throw new BadRequestAlertException("A new reptile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reptile result = reptileRepository.save(reptile);
        return ResponseEntity.created(new URI("/api/reptiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reptiles : Updates an existing reptile.
     *
     * @param reptile the reptile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reptile,
     * or with status 400 (Bad Request) if the reptile is not valid,
     * or with status 500 (Internal Server Error) if the reptile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reptiles")
    @Timed
    public ResponseEntity<Reptile> updateReptile(@Valid @RequestBody Reptile reptile) throws URISyntaxException {
        log.debug("REST request to update Reptile : {}", reptile);
        if (reptile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reptile result = reptileRepository.save(reptile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reptile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reptiles : get all the reptiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reptiles in body
     */
    @GetMapping("/reptiles")
    @Timed
    public ResponseEntity<List<Reptile>> getAllReptiles(Pageable pageable) {
        log.debug("REST request to get a page of Reptiles");
        Page<Reptile> page = reptileRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reptiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reptiles/:id : get the "id" reptile.
     *
     * @param id the id of the reptile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reptile, or with status 404 (Not Found)
     */
    @GetMapping("/reptiles/{id}")
    @Timed
    public ResponseEntity<Reptile> getReptile(@PathVariable Long id) {
        log.debug("REST request to get Reptile : {}", id);
        Optional<Reptile> reptile = reptileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reptile);
    }

    /**
     * DELETE  /reptiles/:id : delete the "id" reptile.
     *
     * @param id the id of the reptile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reptiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteReptile(@PathVariable Long id) {
        log.debug("REST request to delete Reptile : {}", id);

        reptileRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
