package com.jhipster.org.repository;

import com.jhipster.org.domain.Estudante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estudante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {}
