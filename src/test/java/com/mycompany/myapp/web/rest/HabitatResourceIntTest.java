package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterTutoApp;

import com.mycompany.myapp.domain.Habitat;
import com.mycompany.myapp.repository.HabitatRepository;
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

import com.mycompany.myapp.domain.enumeration.HabitatType;
/**
 * Test class for the HabitatResource REST controller.
 *
 * @see HabitatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTutoApp.class)
public class HabitatResourceIntTest {

    private static final HabitatType DEFAULT_NAME = HabitatType.FOREST;
    private static final HabitatType UPDATED_NAME = HabitatType.DESERT;

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    @Autowired
    private HabitatRepository habitatRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHabitatMockMvc;

    private Habitat habitat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HabitatResource habitatResource = new HabitatResource(habitatRepository);
        this.restHabitatMockMvc = MockMvcBuilders.standaloneSetup(habitatResource)
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
    public static Habitat createEntity(EntityManager em) {
        Habitat habitat = new Habitat()
            .name(DEFAULT_NAME)
            .temperature(DEFAULT_TEMPERATURE);
        return habitat;
    }

    @Before
    public void initTest() {
        habitat = createEntity(em);
    }

    @Test
    @Transactional
    public void createHabitat() throws Exception {
        int databaseSizeBeforeCreate = habitatRepository.findAll().size();

        // Create the Habitat
        restHabitatMockMvc.perform(post("/api/habitats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitat)))
            .andExpect(status().isCreated());

        // Validate the Habitat in the database
        List<Habitat> habitatList = habitatRepository.findAll();
        assertThat(habitatList).hasSize(databaseSizeBeforeCreate + 1);
        Habitat testHabitat = habitatList.get(habitatList.size() - 1);
        assertThat(testHabitat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHabitat.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
    }

    @Test
    @Transactional
    public void createHabitatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = habitatRepository.findAll().size();

        // Create the Habitat with an existing ID
        habitat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHabitatMockMvc.perform(post("/api/habitats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitat)))
            .andExpect(status().isBadRequest());

        // Validate the Habitat in the database
        List<Habitat> habitatList = habitatRepository.findAll();
        assertThat(habitatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = habitatRepository.findAll().size();
        // set the field null
        habitat.setTemperature(null);

        // Create the Habitat, which fails.

        restHabitatMockMvc.perform(post("/api/habitats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitat)))
            .andExpect(status().isBadRequest());

        List<Habitat> habitatList = habitatRepository.findAll();
        assertThat(habitatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHabitats() throws Exception {
        // Initialize the database
        habitatRepository.saveAndFlush(habitat);

        // Get all the habitatList
        restHabitatMockMvc.perform(get("/api/habitats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(habitat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.toString())));
    }
    

    @Test
    @Transactional
    public void getHabitat() throws Exception {
        // Initialize the database
        habitatRepository.saveAndFlush(habitat);

        // Get the habitat
        restHabitatMockMvc.perform(get("/api/habitats/{id}", habitat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(habitat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingHabitat() throws Exception {
        // Get the habitat
        restHabitatMockMvc.perform(get("/api/habitats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHabitat() throws Exception {
        // Initialize the database
        habitatRepository.saveAndFlush(habitat);

        int databaseSizeBeforeUpdate = habitatRepository.findAll().size();

        // Update the habitat
        Habitat updatedHabitat = habitatRepository.findById(habitat.getId()).get();
        // Disconnect from session so that the updates on updatedHabitat are not directly saved in db
        em.detach(updatedHabitat);
        updatedHabitat
            .name(UPDATED_NAME)
            .temperature(UPDATED_TEMPERATURE);

        restHabitatMockMvc.perform(put("/api/habitats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHabitat)))
            .andExpect(status().isOk());

        // Validate the Habitat in the database
        List<Habitat> habitatList = habitatRepository.findAll();
        assertThat(habitatList).hasSize(databaseSizeBeforeUpdate);
        Habitat testHabitat = habitatList.get(habitatList.size() - 1);
        assertThat(testHabitat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHabitat.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void updateNonExistingHabitat() throws Exception {
        int databaseSizeBeforeUpdate = habitatRepository.findAll().size();

        // Create the Habitat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHabitatMockMvc.perform(put("/api/habitats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(habitat)))
            .andExpect(status().isBadRequest());

        // Validate the Habitat in the database
        List<Habitat> habitatList = habitatRepository.findAll();
        assertThat(habitatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHabitat() throws Exception {
        // Initialize the database
        habitatRepository.saveAndFlush(habitat);

        int databaseSizeBeforeDelete = habitatRepository.findAll().size();

        // Get the habitat
        restHabitatMockMvc.perform(delete("/api/habitats/{id}", habitat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Habitat> habitatList = habitatRepository.findAll();
        assertThat(habitatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Habitat.class);
        Habitat habitat1 = new Habitat();
        habitat1.setId(1L);
        Habitat habitat2 = new Habitat();
        habitat2.setId(habitat1.getId());
        assertThat(habitat1).isEqualTo(habitat2);
        habitat2.setId(2L);
        assertThat(habitat1).isNotEqualTo(habitat2);
        habitat1.setId(null);
        assertThat(habitat1).isNotEqualTo(habitat2);
    }
}
