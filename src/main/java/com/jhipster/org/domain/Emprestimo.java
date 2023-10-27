package com.jhipster.org.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Emprestimo.
 */
@Entity
@Table(name = "emprestimo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_emprestimo")
    private LocalDate dataEmprestimo;

    @OneToOne
    @JoinColumn(unique = true)
    private Estudante estudante;

    @OneToOne
    @JoinColumn(unique = true)
    private Livro livro;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Emprestimo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEmprestimo() {
        return this.dataEmprestimo;
    }

    public Emprestimo dataEmprestimo(LocalDate dataEmprestimo) {
        this.setDataEmprestimo(dataEmprestimo);
        return this;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Estudante getEstudante() {
        return this.estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Emprestimo estudante(Estudante estudante) {
        this.setEstudante(estudante);
        return this;
    }

    public Livro getLivro() {
        return this.livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Emprestimo livro(Livro livro) {
        this.setLivro(livro);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emprestimo)) {
            return false;
        }
        return id != null && id.equals(((Emprestimo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emprestimo{" +
            "id=" + getId() +
            ", dataEmprestimo='" + getDataEmprestimo() + "'" +
            "}";
    }
}
