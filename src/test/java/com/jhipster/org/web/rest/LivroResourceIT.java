package com.jhipster.org.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.org.IntegrationTest;
import com.jhipster.org.domain.Livro;
import com.jhipster.org.repository.LivroRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link LivroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LivroResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_LANCAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_LANCAMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/livros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivroMockMvc;

    private Livro livro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livro createEntity(EntityManager em) {
        Livro livro = new Livro().titulo(DEFAULT_TITULO).autor(DEFAULT_AUTOR).dataLancamento(DEFAULT_DATA_LANCAMENTO);
        return livro;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livro createUpdatedEntity(EntityManager em) {
        Livro livro = new Livro().titulo(UPDATED_TITULO).autor(UPDATED_AUTOR).dataLancamento(UPDATED_DATA_LANCAMENTO);
        return livro;
    }

    @BeforeEach
    public void initTest() {
        livro = createEntity(em);
    }

    @Test
    @Transactional
    void createLivro() throws Exception {
        int databaseSizeBeforeCreate = livroRepository.findAll().size();
        // Create the Livro
        restLivroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isCreated());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeCreate + 1);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testLivro.getAutor()).isEqualTo(DEFAULT_AUTOR);
        assertThat(testLivro.getDataLancamento()).isEqualTo(DEFAULT_DATA_LANCAMENTO);
    }

    @Test
    @Transactional
    void createLivroWithExistingId() throws Exception {
        // Create the Livro with an existing ID
        livro.setId(1L);

        int databaseSizeBeforeCreate = livroRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLivros() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList
        restLivroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livro.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR)))
            .andExpect(jsonPath("$.[*].dataLancamento").value(hasItem(DEFAULT_DATA_LANCAMENTO.toString())));
    }

    @Test
    @Transactional
    void getLivro() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get the livro
        restLivroMockMvc
            .perform(get(ENTITY_API_URL_ID, livro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livro.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.autor").value(DEFAULT_AUTOR))
            .andExpect(jsonPath("$.dataLancamento").value(DEFAULT_DATA_LANCAMENTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLivro() throws Exception {
        // Get the livro
        restLivroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLivro() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        int databaseSizeBeforeUpdate = livroRepository.findAll().size();

        // Update the livro
        Livro updatedLivro = livroRepository.findById(livro.getId()).get();
        // Disconnect from session so that the updates on updatedLivro are not directly saved in db
        em.detach(updatedLivro);
        updatedLivro.titulo(UPDATED_TITULO).autor(UPDATED_AUTOR).dataLancamento(UPDATED_DATA_LANCAMENTO);

        restLivroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLivro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLivro))
            )
            .andExpect(status().isOk());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testLivro.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testLivro.getDataLancamento()).isEqualTo(UPDATED_DATA_LANCAMENTO);
    }

    @Test
    @Transactional
    void putNonExistingLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();
        livro.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, livro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();
        livro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();
        livro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLivroWithPatch() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        int databaseSizeBeforeUpdate = livroRepository.findAll().size();

        // Update the livro using partial update
        Livro partialUpdatedLivro = new Livro();
        partialUpdatedLivro.setId(livro.getId());

        partialUpdatedLivro.autor(UPDATED_AUTOR);

        restLivroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivro))
            )
            .andExpect(status().isOk());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testLivro.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testLivro.getDataLancamento()).isEqualTo(DEFAULT_DATA_LANCAMENTO);
    }

    @Test
    @Transactional
    void fullUpdateLivroWithPatch() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        int databaseSizeBeforeUpdate = livroRepository.findAll().size();

        // Update the livro using partial update
        Livro partialUpdatedLivro = new Livro();
        partialUpdatedLivro.setId(livro.getId());

        partialUpdatedLivro.titulo(UPDATED_TITULO).autor(UPDATED_AUTOR).dataLancamento(UPDATED_DATA_LANCAMENTO);

        restLivroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivro))
            )
            .andExpect(status().isOk());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testLivro.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testLivro.getDataLancamento()).isEqualTo(UPDATED_DATA_LANCAMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();
        livro.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, livro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();
        livro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();
        livro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivroMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLivro() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        int databaseSizeBeforeDelete = livroRepository.findAll().size();

        // Delete the livro
        restLivroMockMvc
            .perform(delete(ENTITY_API_URL_ID, livro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
