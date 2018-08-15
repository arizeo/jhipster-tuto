package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterTutoApp;

import com.mycompany.myapp.domain.Reptile;
import com.mycompany.myapp.repository.ReptileRepository;
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
 * Test class for the ReptileResource REST controller.
 *
 * @see ReptileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTutoApp.class)
public class ReptileResourceIntTest {

    private static final String DEFAULT_SUBSPECIES = "AAAAAAAAAA";
    private static final String UPDATED_SUBSPECIES = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEGS_NBR = 0;
    private static final Integer UPDATED_LEGS_NBR = 1;

    private static final Boolean DEFAULT_SHELL = false;
    private static final Boolean UPDATED_SHELL = true;

    @Autowired
    private ReptileRepository reptileRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReptileMockMvc;

    private Reptile reptile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReptileResource reptileResource = new ReptileResource(reptileRepository);
        this.restReptileMockMvc = MockMvcBuilders.standaloneSetup(reptileResource)
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
    public static Reptile createEntity(EntityManager em) {
        Reptile reptile = new Reptile()
            .subspecies(DEFAULT_SUBSPECIES)
            .legsNbr(DEFAULT_LEGS_NBR)
            .shell(DEFAULT_SHELL);
        return reptile;
    }

    @Before
    public void initTest() {
        reptile = createEntity(em);
    }

    @Test
    @Transactional
    public void createReptile() throws Exception {
        int databaseSizeBeforeCreate = reptileRepository.findAll().size();

        // Create the Reptile
        restReptileMockMvc.perform(post("/api/reptiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reptile)))
            .andExpect(status().isCreated());

        // Validate the Reptile in the database
        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeCreate + 1);
        Reptile testReptile = reptileList.get(reptileList.size() - 1);
        assertThat(testReptile.getSubspecies()).isEqualTo(DEFAULT_SUBSPECIES);
        assertThat(testReptile.getLegsNbr()).isEqualTo(DEFAULT_LEGS_NBR);
        assertThat(testReptile.isShell()).isEqualTo(DEFAULT_SHELL);
    }

    @Test
    @Transactional
    public void createReptileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reptileRepository.findAll().size();

        // Create the Reptile with an existing ID
        reptile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReptileMockMvc.perform(post("/api/reptiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reptile)))
            .andExpect(status().isBadRequest());

        // Validate the Reptile in the database
        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSubspeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = reptileRepository.findAll().size();
        // set the field null
        reptile.setSubspecies(null);

        // Create the Reptile, which fails.

        restReptileMockMvc.perform(post("/api/reptiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reptile)))
            .andExpect(status().isBadRequest());

        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegsNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = reptileRepository.findAll().size();
        // set the field null
        reptile.setLegsNbr(null);

        // Create the Reptile, which fails.

        restReptileMockMvc.perform(post("/api/reptiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reptile)))
            .andExpect(status().isBadRequest());

        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShellIsRequired() throws Exception {
        int databaseSizeBeforeTest = reptileRepository.findAll().size();
        // set the field null
        reptile.setShell(null);

        // Create the Reptile, which fails.

        restReptileMockMvc.perform(post("/api/reptiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reptile)))
            .andExpect(status().isBadRequest());

        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReptiles() throws Exception {
        // Initialize the database
        reptileRepository.saveAndFlush(reptile);

        // Get all the reptileList
        restReptileMockMvc.perform(get("/api/reptiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reptile.getId().intValue())))
            .andExpect(jsonPath("$.[*].subspecies").value(hasItem(DEFAULT_SUBSPECIES.toString())))
            .andExpect(jsonPath("$.[*].legsNbr").value(hasItem(DEFAULT_LEGS_NBR)))
            .andExpect(jsonPath("$.[*].shell").value(hasItem(DEFAULT_SHELL.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getReptile() throws Exception {
        // Initialize the database
        reptileRepository.saveAndFlush(reptile);

        // Get the reptile
        restReptileMockMvc.perform(get("/api/reptiles/{id}", reptile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reptile.getId().intValue()))
            .andExpect(jsonPath("$.subspecies").value(DEFAULT_SUBSPECIES.toString()))
            .andExpect(jsonPath("$.legsNbr").value(DEFAULT_LEGS_NBR))
            .andExpect(jsonPath("$.shell").value(DEFAULT_SHELL.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingReptile() throws Exception {
        // Get the reptile
        restReptileMockMvc.perform(get("/api/reptiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReptile() throws Exception {
        // Initialize the database
        reptileRepository.saveAndFlush(reptile);

        int databaseSizeBeforeUpdate = reptileRepository.findAll().size();

        // Update the reptile
        Reptile updatedReptile = reptileRepository.findById(reptile.getId()).get();
        // Disconnect from session so that the updates on updatedReptile are not directly saved in db
        em.detach(updatedReptile);
        updatedReptile
            .subspecies(UPDATED_SUBSPECIES)
            .legsNbr(UPDATED_LEGS_NBR)
            .shell(UPDATED_SHELL);

        restReptileMockMvc.perform(put("/api/reptiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReptile)))
            .andExpect(status().isOk());

        // Validate the Reptile in the database
        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeUpdate);
        Reptile testReptile = reptileList.get(reptileList.size() - 1);
        assertThat(testReptile.getSubspecies()).isEqualTo(UPDATED_SUBSPECIES);
        assertThat(testReptile.getLegsNbr()).isEqualTo(UPDATED_LEGS_NBR);
        assertThat(testReptile.isShell()).isEqualTo(UPDATED_SHELL);
    }

    @Test
    @Transactional
    public void updateNonExistingReptile() throws Exception {
        int databaseSizeBeforeUpdate = reptileRepository.findAll().size();

        // Create the Reptile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReptileMockMvc.perform(put("/api/reptiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reptile)))
            .andExpect(status().isBadRequest());

        // Validate the Reptile in the database
        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReptile() throws Exception {
        // Initialize the database
        reptileRepository.saveAndFlush(reptile);

        int databaseSizeBeforeDelete = reptileRepository.findAll().size();

        // Get the reptile
        restReptileMockMvc.perform(delete("/api/reptiles/{id}", reptile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reptile> reptileList = reptileRepository.findAll();
        assertThat(reptileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reptile.class);
        Reptile reptile1 = new Reptile();
        reptile1.setId(1L);
        Reptile reptile2 = new Reptile();
        reptile2.setId(reptile1.getId());
        assertThat(reptile1).isEqualTo(reptile2);
        reptile2.setId(2L);
        assertThat(reptile1).isNotEqualTo(reptile2);
        reptile1.setId(null);
        assertThat(reptile1).isNotEqualTo(reptile2);
    }
}
