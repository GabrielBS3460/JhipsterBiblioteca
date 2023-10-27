package com.jhipster.org.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.org.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstudanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estudante.class);
        Estudante estudante1 = new Estudante();
        estudante1.setId(1L);
        Estudante estudante2 = new Estudante();
        estudante2.setId(estudante1.getId());
        assertThat(estudante1).isEqualTo(estudante2);
        estudante2.setId(2L);
        assertThat(estudante1).isNotEqualTo(estudante2);
        estudante1.setId(null);
        assertThat(estudante1).isNotEqualTo(estudante2);
    }
}
