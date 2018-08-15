package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterTutoApp;

import com.mycompany.myapp.domain.Bird;
import com.mycompany.myapp.repository.BirdRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BirdResource REST controller.
 *
 * @see BirdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTutoApp.class)
public class BirdResourceIntTest {

    private static final String DEFAULT_SUBSPECIES = "AAAAAAAAAA";
    private static final String UPDATED_SUBSPECIES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAN_FLY = false;
    private static final Boolean UPDATED_CAN_FLY = true;

    private static final Boolean DEFAULT_MIGRATORY = false;
    private static final Boolean UPDATED_MIGRATORY = true;

    @Autowired
    private BirdRepository birdRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBirdMockMvc;

    private Bird bird;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BirdResource birdResource = new BirdResource(birdRepository);
        this.restBirdMockMvc = MockMvcBuilders.standaloneSetup(birdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bird createEntity(EntityManager em) {
        Bird bird = new Bird()
            .subspecies(DEFAULT_SUBSPECIES)
            .canFly(DEFAULT_CAN_FLY)
            .migratory(DEFAULT_MIGRATORY);
        return bird;
    }

    @Before
    public void initTest() {
        bird = createEntity(em);
    }

    @Test
    @Transactional
    public void createBird() throws Exception {
        int databaseSizeBeforeCreate = birdRepository.findAll().size();

        // Create the Bird
        restBirdMockMvc.perform(post("/api/birds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bird)))
            .andExpect(status().isCreated());

        // Validate the Bird in the database
        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeCreate + 1);
        Bird testBird = birdList.get(birdList.size() - 1);
        assertThat(testBird.getSubspecies()).isEqualTo(DEFAULT_SUBSPECIES);
        assertThat(testBird.isCanFly()).isEqualTo(DEFAULT_CAN_FLY);
        assertThat(testBird.isMigratory()).isEqualTo(DEFAULT_MIGRATORY);
    }

    @Test
    @Transactional
    public void createBirdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = birdRepository.findAll().size();

        // Create the Bird with an existing ID
        bird.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBirdMockMvc.perform(post("/api/birds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bird)))
            .andExpect(status().isBadRequest());

        // Validate the Bird in the database
        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSubspeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdRepository.findAll().size();
        // set the field null
        bird.setSubspecies(null);

        // Create the Bird, which fails.

        restBirdMockMvc.perform(post("/api/birds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bird)))
            .andExpect(status().isBadRequest());

        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCanFlyIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdRepository.findAll().size();
        // set the field null
        bird.setCanFly(null);

        // Create the Bird, which fails.

        restBirdMockMvc.perform(post("/api/birds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bird)))
            .andExpect(status().isBadRequest());

        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMigratoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdRepository.findAll().size();
        // set the field null
        bird.setMigratory(null);

        // Create the Bird, which fails.

        restBirdMockMvc.perform(post("/api/birds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bird)))
            .andExpect(status().isBadRequest());

        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBirds() throws Exception {
        // Initialize the database
        birdRepository.saveAndFlush(bird);

        // Get all the birdList
        restBirdMockMvc.perform(get("/api/birds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bird.getId().intValue())))
            .andExpect(jsonPath("$.[*].subspecies").value(hasItem(DEFAULT_SUBSPECIES.toString())))
            .andExpect(jsonPath("$.[*].canFly").value(hasItem(DEFAULT_CAN_FLY.booleanValue())))
            .andExpect(jsonPath("$.[*].migratory").value(hasItem(DEFAULT_MIGRATORY.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getBird() throws Exception {
        // Initialize the database
        birdRepository.saveAndFlush(bird);

        // Get the bird
        restBirdMockMvc.perform(get("/api/birds/{id}", bird.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bird.getId().intValue()))
            .andExpect(jsonPath("$.subspecies").value(DEFAULT_SUBSPECIES.toString()))
            .andExpect(jsonPath("$.canFly").value(DEFAULT_CAN_FLY.booleanValue()))
            .andExpect(jsonPath("$.migratory").value(DEFAULT_MIGRATORY.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBird() throws Exception {
        // Get the bird
        restBirdMockMvc.perform(get("/api/birds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBird() throws Exception {
        // Initialize the database
        birdRepository.saveAndFlush(bird);

        int databaseSizeBeforeUpdate = birdRepository.findAll().size();

        // Update the bird
        Bird updatedBird = birdRepository.findById(bird.getId()).get();
        // Disconnect from session so that the updates on updatedBird are not directly saved in db
        em.detach(updatedBird);
        updatedBird
            .subspecies(UPDATED_SUBSPECIES)
            .canFly(UPDATED_CAN_FLY)
            .migratory(UPDATED_MIGRATORY);

        restBirdMockMvc.perform(put("/api/birds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBird)))
            .andExpect(status().isOk());

        // Validate the Bird in the database
        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeUpdate);
        Bird testBird = birdList.get(birdList.size() - 1);
        assertThat(testBird.getSubspecies()).isEqualTo(UPDATED_SUBSPECIES);
        assertThat(testBird.isCanFly()).isEqualTo(UPDATED_CAN_FLY);
        assertThat(testBird.isMigratory()).isEqualTo(UPDATED_MIGRATORY);
    }

    @Test
    @Transactional
    public void updateNonExistingBird() throws Exception {
        int databaseSizeBeforeUpdate = birdRepository.findAll().size();

        // Create the Bird

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBirdMockMvc.perform(put("/api/birds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bird)))
            .andExpect(status().isBadRequest());

        // Validate the Bird in the database
        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBird() throws Exception {
        // Initialize the database
        birdRepository.saveAndFlush(bird);

        int databaseSizeBeforeDelete = birdRepository.findAll().size();

        // Get the bird
        restBirdMockMvc.perform(delete("/api/birds/{id}", bird.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bird> birdList = birdRepository.findAll();
        assertThat(birdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bird.class);
        Bird bird1 = new Bird();
        bird1.setId(1L);
        Bird bird2 = new Bird();
        bird2.setId(bird1.getId());
        assertThat(bird1).isEqualTo(bird2);
        bird2.setId(2L);
        assertThat(bird1).isNotEqualTo(bird2);
        bird1.setId(null);
        assertThat(bird1).isNotEqualTo(bird2);
    }
}
