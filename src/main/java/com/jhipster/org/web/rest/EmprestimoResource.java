package com.jhipster.org.web.rest;

import com.jhipster.org.domain.Emprestimo;
import com.jhipster.org.repository.EmprestimoRepository;
import com.jhipster.org.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.org.domain.Emprestimo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmprestimoResource {

    private final Logger log = LoggerFactory.getLogger(EmprestimoResource.class);

    private static final String ENTITY_NAME = "emprestimo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoResource(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    /**
     * {@code POST  /emprestimos} : Create a new emprestimo.
     *
     * @param emprestimo the emprestimo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emprestimo, or with status {@code 400 (Bad Request)} if the emprestimo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emprestimos")
    public ResponseEntity<Emprestimo> createEmprestimo(@RequestBody Emprestimo emprestimo) throws URISyntaxException {
        log.debug("REST request to save Emprestimo : {}", emprestimo);
        if (emprestimo.getId() != null) {
            throw new BadRequestAlertException("A new emprestimo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emprestimo result = emprestimoRepository.save(emprestimo);
        return ResponseEntity
            .created(new URI("/api/emprestimos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emprestimos/:id} : Updates an existing emprestimo.
     *
     * @param id the id of the emprestimo to save.
     * @param emprestimo the emprestimo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emprestimo,
     * or with status {@code 400 (Bad Request)} if the emprestimo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emprestimo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emprestimos/{id}")
    public ResponseEntity<Emprestimo> updateEmprestimo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Emprestimo emprestimo
    ) throws URISyntaxException {
        log.debug("REST request to update Emprestimo : {}, {}", id, emprestimo);
        if (emprestimo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emprestimo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emprestimoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Emprestimo result = emprestimoRepository.save(emprestimo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emprestimo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emprestimos/:id} : Partial updates given fields of an existing emprestimo, field will ignore if it is null
     *
     * @param id the id of the emprestimo to save.
     * @param emprestimo the emprestimo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emprestimo,
     * or with status {@code 400 (Bad Request)} if the emprestimo is not valid,
     * or with status {@code 404 (Not Found)} if the emprestimo is not found,
     * or with status {@code 500 (Internal Server Error)} if the emprestimo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emprestimos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Emprestimo> partialUpdateEmprestimo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Emprestimo emprestimo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Emprestimo partially : {}, {}", id, emprestimo);
        if (emprestimo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emprestimo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emprestimoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Emprestimo> result = emprestimoRepository
            .findById(emprestimo.getId())
            .map(existingEmprestimo -> {
                if (emprestimo.getDataEmprestimo() != null) {
                    existingEmprestimo.setDataEmprestimo(emprestimo.getDataEmprestimo());
                }

                return existingEmprestimo;
            })
            .map(emprestimoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emprestimo.getId().toString())
        );
    }

    /**
     * {@code GET  /emprestimos} : get all the emprestimos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emprestimos in body.
     */
    @GetMapping("/emprestimos")
    public ResponseEntity<List<Emprestimo>> getAllEmprestimos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Emprestimos");
        Page<Emprestimo> page = emprestimoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emprestimos/:id} : get the "id" emprestimo.
     *
     * @param id the id of the emprestimo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emprestimo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emprestimos/{id}")
    public ResponseEntity<Emprestimo> getEmprestimo(@PathVariable Long id) {
        log.debug("REST request to get Emprestimo : {}", id);
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emprestimo);
    }

    /**
     * {@code DELETE  /emprestimos/:id} : delete the "id" emprestimo.
     *
     * @param id the id of the emprestimo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emprestimos/{id}")
    public ResponseEntity<Void> deleteEmprestimo(@PathVariable Long id) {
        log.debug("REST request to delete Emprestimo : {}", id);
        emprestimoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
