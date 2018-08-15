package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterTutoApp;

import com.mycompany.myapp.domain.Statistics;
import com.mycompany.myapp.repository.StatisticsRepository;
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
 * Test class for the StatisticsResource REST controller.
 *
 * @see StatisticsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTutoApp.class)
public class StatisticsResourceIntTest {

    private static final Double DEFAULT_BMI = 1D;
    private static final Double UPDATED_BMI = 2D;

    @Autowired
    private StatisticsRepository statisticsRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatisticsMockMvc;

    private Statistics statistics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatisticsResource statisticsResource = new StatisticsResource(statisticsRepository);
        this.restStatisticsMockMvc = MockMvcBuilders.standaloneSetup(statisticsResource)
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
    public static Statistics createEntity(EntityManager em) {
        Statistics statistics = new Statistics()
            .bmi(DEFAULT_BMI);
        return statistics;
    }

    @Before
    public void initTest() {
        statistics = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatistics() throws Exception {
        int databaseSizeBeforeCreate = statisticsRepository.findAll().size();

        // Create the Statistics
        restStatisticsMockMvc.perform(post("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statistics)))
            .andExpect(status().isCreated());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeCreate + 1);
        Statistics testStatistics = statisticsList.get(statisticsList.size() - 1);
        assertThat(testStatistics.getBmi()).isEqualTo(DEFAULT_BMI);
    }

    @Test
    @Transactional
    public void createStatisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statisticsRepository.findAll().size();

        // Create the Statistics with an existing ID
        statistics.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticsMockMvc.perform(post("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statistics)))
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBmiIsRequired() throws Exception {
        int databaseSizeBeforeTest = statisticsRepository.findAll().size();
        // set the field null
        statistics.setBmi(null);

        // Create the Statistics, which fails.

        restStatisticsMockMvc.perform(post("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statistics)))
            .andExpect(status().isBadRequest());

        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList
        restStatisticsMockMvc.perform(get("/api/statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].bmi").value(hasItem(DEFAULT_BMI.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get the statistics
        restStatisticsMockMvc.perform(get("/api/statistics/{id}", statistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statistics.getId().intValue()))
            .andExpect(jsonPath("$.bmi").value(DEFAULT_BMI.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStatistics() throws Exception {
        // Get the statistics
        restStatisticsMockMvc.perform(get("/api/statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();

        // Update the statistics
        Statistics updatedStatistics = statisticsRepository.findById(statistics.getId()).get();
        // Disconnect from session so that the updates on updatedStatistics are not directly saved in db
        em.detach(updatedStatistics);
        updatedStatistics
            .bmi(UPDATED_BMI);

        restStatisticsMockMvc.perform(put("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatistics)))
            .andExpect(status().isOk());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
        Statistics testStatistics = statisticsList.get(statisticsList.size() - 1);
        assertThat(testStatistics.getBmi()).isEqualTo(UPDATED_BMI);
    }

    @Test
    @Transactional
    public void updateNonExistingStatistics() throws Exception {
        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();

        // Create the Statistics

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatisticsMockMvc.perform(put("/api/statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statistics)))
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        int databaseSizeBeforeDelete = statisticsRepository.findAll().size();

        // Get the statistics
        restStatisticsMockMvc.perform(delete("/api/statistics/{id}", statistics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statistics.class);
        Statistics statistics1 = new Statistics();
        statistics1.setId(1L);
        Statistics statistics2 = new Statistics();
        statistics2.setId(statistics1.getId());
        assertThat(statistics1).isEqualTo(statistics2);
        statistics2.setId(2L);
        assertThat(statistics1).isNotEqualTo(statistics2);
        statistics1.setId(null);
        assertThat(statistics1).isNotEqualTo(statistics2);
    }
}
