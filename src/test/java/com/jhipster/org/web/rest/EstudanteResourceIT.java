package com.jhipster.org.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.org.IntegrationTest;
import com.jhipster.org.domain.Estudante;
import com.jhipster.org.repository.EstudanteRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EstudanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstudanteResourceIT {

    private static final String DEFAULT_PRIMEIRO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMEIRO_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDADE = 1;
    private static final Integer UPDATED_IDADE = 2;

    private static final String ENTITY_API_URL = "/api/estudantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstudanteMockMvc;

    private Estudante estudante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudante createEntity(EntityManager em) {
        Estudante estudante = new Estudante()
            .primeiroNome(DEFAULT_PRIMEIRO_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .email(DEFAULT_EMAIL)
            .idade(DEFAULT_IDADE);
        return estudante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudante createUpdatedEntity(EntityManager em) {
        Estudante estudante = new Estudante()
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .email(UPDATED_EMAIL)
            .idade(UPDATED_IDADE);
        return estudante;
    }

    @BeforeEach
    public void initTest() {
        estudante = createEntity(em);
    }

    @Test
    @Transactional
    void createEstudante() throws Exception {
        int databaseSizeBeforeCreate = estudanteRepository.findAll().size();
        // Create the Estudante
        restEstudanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudante)))
            .andExpect(status().isCreated());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeCreate + 1);
        Estudante testEstudante = estudanteList.get(estudanteList.size() - 1);
        assertThat(testEstudante.getPrimeiroNome()).isEqualTo(DEFAULT_PRIMEIRO_NOME);
        assertThat(testEstudante.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testEstudante.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEstudante.getIdade()).isEqualTo(DEFAULT_IDADE);
    }

    @Test
    @Transactional
    void createEstudanteWithExistingId() throws Exception {
        // Create the Estudante with an existing ID
        estudante.setId(1L);

        int databaseSizeBeforeCreate = estudanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudante)))
            .andExpect(status().isBadRequest());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrimeiroNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estudanteRepository.findAll().size();
        // set the field null
        estudante.setPrimeiroNome(null);

        // Create the Estudante, which fails.

        restEstudanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudante)))
            .andExpect(status().isBadRequest());

        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSobrenomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estudanteRepository.findAll().size();
        // set the field null
        estudante.setSobrenome(null);

        // Create the Estudante, which fails.

        restEstudanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudante)))
            .andExpect(status().isBadRequest());

        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = estudanteRepository.findAll().size();
        // set the field null
        estudante.setEmail(null);

        // Create the Estudante, which fails.

        restEstudanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudante)))
            .andExpect(status().isBadRequest());

        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estudanteRepository.findAll().size();
        // set the field null
        estudante.setIdade(null);

        // Create the Estudante, which fails.

        restEstudanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudante)))
            .andExpect(status().isBadRequest());

        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstudantes() throws Exception {
        // Initialize the database
        estudanteRepository.saveAndFlush(estudante);

        // Get all the estudanteList
        restEstudanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudante.getId().intValue())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)));
    }

    @Test
    @Transactional
    void getEstudante() throws Exception {
        // Initialize the database
        estudanteRepository.saveAndFlush(estudante);

        // Get the estudante
        restEstudanteMockMvc
            .perform(get(ENTITY_API_URL_ID, estudante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estudante.getId().intValue()))
            .andExpect(jsonPath("$.primeiroNome").value(DEFAULT_PRIMEIRO_NOME))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE));
    }

    @Test
    @Transactional
    void getNonExistingEstudante() throws Exception {
        // Get the estudante
        restEstudanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstudante() throws Exception {
        // Initialize the database
        estudanteRepository.saveAndFlush(estudante);

        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();

        // Update the estudante
        Estudante updatedEstudante = estudanteRepository.findById(estudante.getId()).get();
        // Disconnect from session so that the updates on updatedEstudante are not directly saved in db
        em.detach(updatedEstudante);
        updatedEstudante.primeiroNome(UPDATED_PRIMEIRO_NOME).sobrenome(UPDATED_SOBRENOME).email(UPDATED_EMAIL).idade(UPDATED_IDADE);

        restEstudanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstudante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstudante))
            )
            .andExpect(status().isOk());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
        Estudante testEstudante = estudanteList.get(estudanteList.size() - 1);
        assertThat(testEstudante.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testEstudante.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testEstudante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstudante.getIdade()).isEqualTo(UPDATED_IDADE);
    }

    @Test
    @Transactional
    void putNonExistingEstudante() throws Exception {
        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();
        estudante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstudante() throws Exception {
        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();
        estudante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstudante() throws Exception {
        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();
        estudante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudanteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstudanteWithPatch() throws Exception {
        // Initialize the database
        estudanteRepository.saveAndFlush(estudante);

        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();

        // Update the estudante using partial update
        Estudante partialUpdatedEstudante = new Estudante();
        partialUpdatedEstudante.setId(estudante.getId());

        partialUpdatedEstudante.primeiroNome(UPDATED_PRIMEIRO_NOME);

        restEstudanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudante))
            )
            .andExpect(status().isOk());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
        Estudante testEstudante = estudanteList.get(estudanteList.size() - 1);
        assertThat(testEstudante.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testEstudante.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testEstudante.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEstudante.getIdade()).isEqualTo(DEFAULT_IDADE);
    }

    @Test
    @Transactional
    void fullUpdateEstudanteWithPatch() throws Exception {
        // Initialize the database
        estudanteRepository.saveAndFlush(estudante);

        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();

        // Update the estudante using partial update
        Estudante partialUpdatedEstudante = new Estudante();
        partialUpdatedEstudante.setId(estudante.getId());

        partialUpdatedEstudante.primeiroNome(UPDATED_PRIMEIRO_NOME).sobrenome(UPDATED_SOBRENOME).email(UPDATED_EMAIL).idade(UPDATED_IDADE);

        restEstudanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudante))
            )
            .andExpect(status().isOk());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
        Estudante testEstudante = estudanteList.get(estudanteList.size() - 1);
        assertThat(testEstudante.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testEstudante.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testEstudante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstudante.getIdade()).isEqualTo(UPDATED_IDADE);
    }

    @Test
    @Transactional
    void patchNonExistingEstudante() throws Exception {
        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();
        estudante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estudante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstudante() throws Exception {
        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();
        estudante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstudante() throws Exception {
        int databaseSizeBeforeUpdate = estudanteRepository.findAll().size();
        estudante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudanteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estudante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estudante in the database
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstudante() throws Exception {
        // Initialize the database
        estudanteRepository.saveAndFlush(estudante);

        int databaseSizeBeforeDelete = estudanteRepository.findAll().size();

        // Delete the estudante
        restEstudanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, estudante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estudante> estudanteList = estudanteRepository.findAll();
        assertThat(estudanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
