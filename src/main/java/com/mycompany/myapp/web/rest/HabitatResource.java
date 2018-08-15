package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Habitat;
import com.mycompany.myapp.repository.HabitatRepository;
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
 * REST controller for managing Habitat.
 */
@RestController
@RequestMapping("/api")
public class HabitatResource {

    private final Logger log = LoggerFactory.getLogger(HabitatResource.class);

    private static final String ENTITY_NAME = "habitat";

    private final HabitatRepository habitatRepository;

    public HabitatResource(HabitatRepository habitatRepository) {
        this.habitatRepository = habitatRepository;
    }

    /**
     * POST  /habitats : Create a new habitat.
     *
     * @param habitat the habitat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new habitat, or with status 400 (Bad Request) if the habitat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/habitats")
    @Timed
    public ResponseEntity<Habitat> createHabitat(@Valid @RequestBody Habitat habitat) throws URISyntaxException {
        log.debug("REST request to save Habitat : {}", habitat);
        if (habitat.getId() != null) {
            throw new BadRequestAlertException("A new habitat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Habitat result = habitatRepository.save(habitat);
        return ResponseEntity.created(new URI("/api/habitats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /habitats : Updates an existing habitat.
     *
     * @param habitat the habitat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated habitat,
     * or with status 400 (Bad Request) if the habitat is not valid,
     * or with status 500 (Internal Server Error) if the habitat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/habitats")
    @Timed
    public ResponseEntity<Habitat> updateHabitat(@Valid @RequestBody Habitat habitat) throws URISyntaxException {
        log.debug("REST request to update Habitat : {}", habitat);
        if (habitat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Habitat result = habitatRepository.save(habitat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, habitat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /habitats : get all the habitats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of habitats in body
     */
    @GetMapping("/habitats")
    @Timed
    public ResponseEntity<List<Habitat>> getAllHabitats(Pageable pageable) {
        log.debug("REST request to get a page of Habitats");
        Page<Habitat> page = habitatRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/habitats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /habitats/:id : get the "id" habitat.
     *
     * @param id the id of the habitat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the habitat, or with status 404 (Not Found)
     */
    @GetMapping("/habitats/{id}")
    @Timed
    public ResponseEntity<Habitat> getHabitat(@PathVariable Long id) {
        log.debug("REST request to get Habitat : {}", id);
        Optional<Habitat> habitat = habitatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(habitat);
    }

    /**
     * DELETE  /habitats/:id : delete the "id" habitat.
     *
     * @param id the id of the habitat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/habitats/{id}")
    @Timed
    public ResponseEntity<Void> deleteHabitat(@PathVariable Long id) {
        log.debug("REST request to delete Habitat : {}", id);

        habitatRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
