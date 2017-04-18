package atos.esp.web.rest;

import atos.esp.SmsbankingApp;

import atos.esp.domain.GroupeBancaire;
import atos.esp.repository.GroupeBancaireRepository;
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
 * Test class for the GroupeBancaireResource REST controller.
 *
 * @see GroupeBancaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsbankingApp.class)
public class GroupeBancaireResourceIntTest {

    private static final String DEFAULT_LIBELLE_G = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_G = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_G = "AAAAA";
    private static final String UPDATED_CODE_G = "BBBBB";

    @Autowired
    private GroupeBancaireRepository groupeBancaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroupeBancaireMockMvc;

    private GroupeBancaire groupeBancaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroupeBancaireResource groupeBancaireResource = new GroupeBancaireResource(groupeBancaireRepository);
        this.restGroupeBancaireMockMvc = MockMvcBuilders.standaloneSetup(groupeBancaireResource)
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
    public static GroupeBancaire createEntity(EntityManager em) {
        GroupeBancaire groupeBancaire = new GroupeBancaire()
            .libelleG(DEFAULT_LIBELLE_G)
            .codeG(DEFAULT_CODE_G);
        return groupeBancaire;
    }

    @Before
    public void initTest() {
        groupeBancaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupeBancaire() throws Exception {
        int databaseSizeBeforeCreate = groupeBancaireRepository.findAll().size();

        // Create the GroupeBancaire
        restGroupeBancaireMockMvc.perform(post("/api/groupe-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeBancaire)))
            .andExpect(status().isCreated());

        // Validate the GroupeBancaire in the database
        List<GroupeBancaire> groupeBancaireList = groupeBancaireRepository.findAll();
        assertThat(groupeBancaireList).hasSize(databaseSizeBeforeCreate + 1);
        GroupeBancaire testGroupeBancaire = groupeBancaireList.get(groupeBancaireList.size() - 1);
        assertThat(testGroupeBancaire.getLibelleG()).isEqualTo(DEFAULT_LIBELLE_G);
        assertThat(testGroupeBancaire.getCodeG()).isEqualTo(DEFAULT_CODE_G);
    }

    @Test
    @Transactional
    public void createGroupeBancaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupeBancaireRepository.findAll().size();

        // Create the GroupeBancaire with an existing ID
        groupeBancaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeBancaireMockMvc.perform(post("/api/groupe-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeBancaire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GroupeBancaire> groupeBancaireList = groupeBancaireRepository.findAll();
        assertThat(groupeBancaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleGIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupeBancaireRepository.findAll().size();
        // set the field null
        groupeBancaire.setLibelleG(null);

        // Create the GroupeBancaire, which fails.

        restGroupeBancaireMockMvc.perform(post("/api/groupe-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeBancaire)))
            .andExpect(status().isBadRequest());

        List<GroupeBancaire> groupeBancaireList = groupeBancaireRepository.findAll();
        assertThat(groupeBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeGIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupeBancaireRepository.findAll().size();
        // set the field null
        groupeBancaire.setCodeG(null);

        // Create the GroupeBancaire, which fails.

        restGroupeBancaireMockMvc.perform(post("/api/groupe-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeBancaire)))
            .andExpect(status().isBadRequest());

        List<GroupeBancaire> groupeBancaireList = groupeBancaireRepository.findAll();
        assertThat(groupeBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupeBancaires() throws Exception {
        // Initialize the database
        groupeBancaireRepository.saveAndFlush(groupeBancaire);

        // Get all the groupeBancaireList
        restGroupeBancaireMockMvc.perform(get("/api/groupe-bancaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupeBancaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleG").value(hasItem(DEFAULT_LIBELLE_G.toString())))
            .andExpect(jsonPath("$.[*].codeG").value(hasItem(DEFAULT_CODE_G.toString())));
    }

    @Test
    @Transactional
    public void getGroupeBancaire() throws Exception {
        // Initialize the database
        groupeBancaireRepository.saveAndFlush(groupeBancaire);

        // Get the groupeBancaire
        restGroupeBancaireMockMvc.perform(get("/api/groupe-bancaires/{id}", groupeBancaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupeBancaire.getId().intValue()))
            .andExpect(jsonPath("$.libelleG").value(DEFAULT_LIBELLE_G.toString()))
            .andExpect(jsonPath("$.codeG").value(DEFAULT_CODE_G.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupeBancaire() throws Exception {
        // Get the groupeBancaire
        restGroupeBancaireMockMvc.perform(get("/api/groupe-bancaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupeBancaire() throws Exception {
        // Initialize the database
        groupeBancaireRepository.saveAndFlush(groupeBancaire);
        int databaseSizeBeforeUpdate = groupeBancaireRepository.findAll().size();

        // Update the groupeBancaire
        GroupeBancaire updatedGroupeBancaire = groupeBancaireRepository.findOne(groupeBancaire.getId());
        updatedGroupeBancaire
            .libelleG(UPDATED_LIBELLE_G)
            .codeG(UPDATED_CODE_G);

        restGroupeBancaireMockMvc.perform(put("/api/groupe-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupeBancaire)))
            .andExpect(status().isOk());

        // Validate the GroupeBancaire in the database
        List<GroupeBancaire> groupeBancaireList = groupeBancaireRepository.findAll();
        assertThat(groupeBancaireList).hasSize(databaseSizeBeforeUpdate);
        GroupeBancaire testGroupeBancaire = groupeBancaireList.get(groupeBancaireList.size() - 1);
        assertThat(testGroupeBancaire.getLibelleG()).isEqualTo(UPDATED_LIBELLE_G);
        assertThat(testGroupeBancaire.getCodeG()).isEqualTo(UPDATED_CODE_G);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupeBancaire() throws Exception {
        int databaseSizeBeforeUpdate = groupeBancaireRepository.findAll().size();

        // Create the GroupeBancaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupeBancaireMockMvc.perform(put("/api/groupe-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeBancaire)))
            .andExpect(status().isCreated());

        // Validate the GroupeBancaire in the database
        List<GroupeBancaire> groupeBancaireList = groupeBancaireRepository.findAll();
        assertThat(groupeBancaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGroupeBancaire() throws Exception {
        // Initialize the database
        groupeBancaireRepository.saveAndFlush(groupeBancaire);
        int databaseSizeBeforeDelete = groupeBancaireRepository.findAll().size();

        // Get the groupeBancaire
        restGroupeBancaireMockMvc.perform(delete("/api/groupe-bancaires/{id}", groupeBancaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupeBancaire> groupeBancaireList = groupeBancaireRepository.findAll();
        assertThat(groupeBancaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeBancaire.class);
    }
}
