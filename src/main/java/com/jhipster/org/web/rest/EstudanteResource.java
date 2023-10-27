package com.jhipster.org.web.rest;

import com.jhipster.org.domain.Estudante;
import com.jhipster.org.repository.EstudanteRepository;
import com.jhipster.org.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.jhipster.org.domain.Estudante}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstudanteResource {

    private final Logger log = LoggerFactory.getLogger(EstudanteResource.class);

    private static final String ENTITY_NAME = "estudante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstudanteRepository estudanteRepository;

    public EstudanteResource(EstudanteRepository estudanteRepository) {
        this.estudanteRepository = estudanteRepository;
    }

    /**
     * {@code POST  /estudantes} : Create a new estudante.
     *
     * @param estudante the estudante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estudante, or with status {@code 400 (Bad Request)} if the estudante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estudantes")
    public ResponseEntity<Estudante> createEstudante(@Valid @RequestBody Estudante estudante) throws URISyntaxException {
        log.debug("REST request to save Estudante : {}", estudante);
        if (estudante.getId() != null) {
            throw new BadRequestAlertException("A new estudante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estudante result = estudanteRepository.save(estudante);
        return ResponseEntity
            .created(new URI("/api/estudantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estudantes/:id} : Updates an existing estudante.
     *
     * @param id the id of the estudante to save.
     * @param estudante the estudante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudante,
     * or with status {@code 400 (Bad Request)} if the estudante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estudante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estudantes/{id}")
    public ResponseEntity<Estudante> updateEstudante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Estudante estudante
    ) throws URISyntaxException {
        log.debug("REST request to update Estudante : {}, {}", id, estudante);
        if (estudante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Estudante result = estudanteRepository.save(estudante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estudante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estudantes/:id} : Partial updates given fields of an existing estudante, field will ignore if it is null
     *
     * @param id the id of the estudante to save.
     * @param estudante the estudante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudante,
     * or with status {@code 400 (Bad Request)} if the estudante is not valid,
     * or with status {@code 404 (Not Found)} if the estudante is not found,
     * or with status {@code 500 (Internal Server Error)} if the estudante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estudantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Estudante> partialUpdateEstudante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Estudante estudante
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estudante partially : {}, {}", id, estudante);
        if (estudante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Estudante> result = estudanteRepository
            .findById(estudante.getId())
            .map(existingEstudante -> {
                if (estudante.getPrimeiroNome() != null) {
                    existingEstudante.setPrimeiroNome(estudante.getPrimeiroNome());
                }
                if (estudante.getSobrenome() != null) {
                    existingEstudante.setSobrenome(estudante.getSobrenome());
                }
                if (estudante.getEmail() != null) {
                    existingEstudante.setEmail(estudante.getEmail());
                }
                if (estudante.getIdade() != null) {
                    existingEstudante.setIdade(estudante.getIdade());
                }

                return existingEstudante;
            })
            .map(estudanteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estudante.getId().toString())
        );
    }

    /**
     * {@code GET  /estudantes} : get all the estudantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estudantes in body.
     */
    @GetMapping("/estudantes")
    public ResponseEntity<List<Estudante>> getAllEstudantes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Estudantes");
        Page<Estudante> page = estudanteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estudantes/:id} : get the "id" estudante.
     *
     * @param id the id of the estudante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estudante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estudantes/{id}")
    public ResponseEntity<Estudante> getEstudante(@PathVariable Long id) {
        log.debug("REST request to get Estudante : {}", id);
        Optional<Estudante> estudante = estudanteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estudante);
    }

    /**
     * {@code DELETE  /estudantes/:id} : delete the "id" estudante.
     *
     * @param id the id of the estudante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estudantes/{id}")
    public ResponseEntity<Void> deleteEstudante(@PathVariable Long id) {
        log.debug("REST request to delete Estudante : {}", id);
        estudanteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
