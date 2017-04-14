package atos.esp.web.rest;

import atos.esp.SmsbankingApp;

import atos.esp.domain.Banque;
import atos.esp.domain.GroupeBancaire;
import atos.esp.repository.BanqueRepository;
import atos.esp.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BanqueResource REST controller.
 *
 * @see BanqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsbankingApp.class)
public class BanqueResourceIntTest {

    private static final String DEFAULT_LIBELLE_B = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_B = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE_B = 5;
    private static final Integer UPDATED_CODE_B = 4;

    private static final Integer DEFAULT_CODE_P = 2;
    private static final Integer UPDATED_CODE_P = 1;

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBanqueMockMvc;

    private Banque banque;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BanqueResource banqueResource = new BanqueResource(banqueRepository);
        this.restBanqueMockMvc = MockMvcBuilders.standaloneSetup(banqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banque createEntity(EntityManager em) {
        Banque banque = new Banque()
            .libelleB(DEFAULT_LIBELLE_B)
            .codeB(DEFAULT_CODE_B)
            .codeP(DEFAULT_CODE_P);
        // Add required entity
        GroupeBancaire groupeBancaire = GroupeBancaireResourceIntTest.createEntity(em);
        em.persist(groupeBancaire);
        em.flush();
        banque.setGroupeBancaire(groupeBancaire);
        return banque;
    }

    @Before
    public void initTest() {
        banque = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanque() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isCreated());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate + 1);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getLibelleB()).isEqualTo(DEFAULT_LIBELLE_B);
        assertThat(testBanque.getCodeB()).isEqualTo(DEFAULT_CODE_B);
        assertThat(testBanque.getCodeP()).isEqualTo(DEFAULT_CODE_P);
    }

    @Test
    @Transactional
    public void createBanqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque with an existing ID
        banque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleBIsRequired() throws Exception {
        int databaseSizeBeforeTest = banqueRepository.findAll().size();
        // set the field null
        banque.setLibelleB(null);

        // Create the Banque, which fails.

        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeBIsRequired() throws Exception {
        int databaseSizeBeforeTest = banqueRepository.findAll().size();
        // set the field null
        banque.setCodeB(null);

        // Create the Banque, which fails.

        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodePIsRequired() throws Exception {
        int databaseSizeBeforeTest = banqueRepository.findAll().size();
        // set the field null
        banque.setCodeP(null);

        // Create the Banque, which fails.

        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBanques() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList
        restBanqueMockMvc.perform(get("/api/banques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banque.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleB").value(hasItem(DEFAULT_LIBELLE_B.toString())))
            .andExpect(jsonPath("$.[*].codeB").value(hasItem(DEFAULT_CODE_B)))
            .andExpect(jsonPath("$.[*].codeP").value(hasItem(DEFAULT_CODE_P)));
    }

    @Test
    @Transactional
    public void getBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", banque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banque.getId().intValue()))
            .andExpect(jsonPath("$.libelleB").value(DEFAULT_LIBELLE_B.toString()))
            .andExpect(jsonPath("$.codeB").value(DEFAULT_CODE_B))
            .andExpect(jsonPath("$.codeP").value(DEFAULT_CODE_P));
    }

    @Test
    @Transactional
    public void getNonExistingBanque() throws Exception {
        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);
        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Update the banque
        Banque updatedBanque = banqueRepository.findOne(banque.getId());
        updatedBanque
            .libelleB(UPDATED_LIBELLE_B)
            .codeB(UPDATED_CODE_B)
            .codeP(UPDATED_CODE_P);

        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanque)))
            .andExpect(status().isOk());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getLibelleB()).isEqualTo(UPDATED_LIBELLE_B);
        assertThat(testBanque.getCodeB()).isEqualTo(UPDATED_CODE_B);
        assertThat(testBanque.getCodeP()).isEqualTo(UPDATED_CODE_P);
    }

    @Test
    @Transactional
    public void updateNonExistingBanque() throws Exception {
        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Create the Banque

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isCreated());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);
        int databaseSizeBeforeDelete = banqueRepository.findAll().size();

        // Get the banque
        restBanqueMockMvc.perform(delete("/api/banques/{id}", banque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banque.class);
    }
}
