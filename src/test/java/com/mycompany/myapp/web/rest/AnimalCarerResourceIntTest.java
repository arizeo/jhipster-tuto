package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterTutoApp;

import com.mycompany.myapp.domain.AnimalCarer;
import com.mycompany.myapp.repository.AnimalCarerRepository;
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
 * Test class for the AnimalCarerResource REST controller.
 *
 * @see AnimalCarerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTutoApp.class)
public class AnimalCarerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AnimalCarerRepository animalCarerRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnimalCarerMockMvc;

    private AnimalCarer animalCarer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalCarerResource animalCarerResource = new AnimalCarerResource(animalCarerRepository);
        this.restAnimalCarerMockMvc = MockMvcBuilders.standaloneSetup(animalCarerResource)
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
    public static AnimalCarer createEntity(EntityManager em) {
        AnimalCarer animalCarer = new AnimalCarer()
            .name(DEFAULT_NAME);
        return animalCarer;
    }

    @Before
    public void initTest() {
        animalCarer = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalCarer() throws Exception {
        int databaseSizeBeforeCreate = animalCarerRepository.findAll().size();

        // Create the AnimalCarer
        restAnimalCarerMockMvc.perform(post("/api/animal-carers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarer)))
            .andExpect(status().isCreated());

        // Validate the AnimalCarer in the database
        List<AnimalCarer> animalCarerList = animalCarerRepository.findAll();
        assertThat(animalCarerList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalCarer testAnimalCarer = animalCarerList.get(animalCarerList.size() - 1);
        assertThat(testAnimalCarer.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAnimalCarerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalCarerRepository.findAll().size();

        // Create the AnimalCarer with an existing ID
        animalCarer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalCarerMockMvc.perform(post("/api/animal-carers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarer)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCarer in the database
        List<AnimalCarer> animalCarerList = animalCarerRepository.findAll();
        assertThat(animalCarerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalCarerRepository.findAll().size();
        // set the field null
        animalCarer.setName(null);

        // Create the AnimalCarer, which fails.

        restAnimalCarerMockMvc.perform(post("/api/animal-carers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarer)))
            .andExpect(status().isBadRequest());

        List<AnimalCarer> animalCarerList = animalCarerRepository.findAll();
        assertThat(animalCarerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnimalCarers() throws Exception {
        // Initialize the database
        animalCarerRepository.saveAndFlush(animalCarer);

        // Get all the animalCarerList
        restAnimalCarerMockMvc.perform(get("/api/animal-carers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalCarer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getAnimalCarer() throws Exception {
        // Initialize the database
        animalCarerRepository.saveAndFlush(animalCarer);

        // Get the animalCarer
        restAnimalCarerMockMvc.perform(get("/api/animal-carers/{id}", animalCarer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalCarer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAnimalCarer() throws Exception {
        // Get the animalCarer
        restAnimalCarerMockMvc.perform(get("/api/animal-carers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalCarer() throws Exception {
        // Initialize the database
        animalCarerRepository.saveAndFlush(animalCarer);

        int databaseSizeBeforeUpdate = animalCarerRepository.findAll().size();

        // Update the animalCarer
        AnimalCarer updatedAnimalCarer = animalCarerRepository.findById(animalCarer.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalCarer are not directly saved in db
        em.detach(updatedAnimalCarer);
        updatedAnimalCarer
            .name(UPDATED_NAME);

        restAnimalCarerMockMvc.perform(put("/api/animal-carers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnimalCarer)))
            .andExpect(status().isOk());

        // Validate the AnimalCarer in the database
        List<AnimalCarer> animalCarerList = animalCarerRepository.findAll();
        assertThat(animalCarerList).hasSize(databaseSizeBeforeUpdate);
        AnimalCarer testAnimalCarer = animalCarerList.get(animalCarerList.size() - 1);
        assertThat(testAnimalCarer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalCarer() throws Exception {
        int databaseSizeBeforeUpdate = animalCarerRepository.findAll().size();

        // Create the AnimalCarer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnimalCarerMockMvc.perform(put("/api/animal-carers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalCarer)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCarer in the database
        List<AnimalCarer> animalCarerList = animalCarerRepository.findAll();
        assertThat(animalCarerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalCarer() throws Exception {
        // Initialize the database
        animalCarerRepository.saveAndFlush(animalCarer);

        int databaseSizeBeforeDelete = animalCarerRepository.findAll().size();

        // Get the animalCarer
        restAnimalCarerMockMvc.perform(delete("/api/animal-carers/{id}", animalCarer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnimalCarer> animalCarerList = animalCarerRepository.findAll();
        assertThat(animalCarerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalCarer.class);
        AnimalCarer animalCarer1 = new AnimalCarer();
        animalCarer1.setId(1L);
        AnimalCarer animalCarer2 = new AnimalCarer();
        animalCarer2.setId(animalCarer1.getId());
        assertThat(animalCarer1).isEqualTo(animalCarer2);
        animalCarer2.setId(2L);
        assertThat(animalCarer1).isNotEqualTo(animalCarer2);
        animalCarer1.setId(null);
        assertThat(animalCarer1).isNotEqualTo(animalCarer2);
    }
}
