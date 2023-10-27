package com.jhipster.org.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Estudante.
 */
@Entity
@Table(name = "estudante")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Estudante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "primeiro_nome", nullable = false)
    private String primeiroNome;

    @NotNull
    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "idade", nullable = false)
    private Integer idade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estudante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimeiroNome() {
        return this.primeiroNome;
    }

    public Estudante primeiroNome(String primeiroNome) {
        this.setPrimeiroNome(primeiroNome);
        return this;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public Estudante sobrenome(String sobrenome) {
        this.setSobrenome(sobrenome);
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return this.email;
    }

    public Estudante email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdade() {
        return this.idade;
    }

    public Estudante idade(Integer idade) {
        this.setIdade(idade);
        return this;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estudante)) {
            return false;
        }
        return id != null && id.equals(((Estudante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estudante{" +
            "id=" + getId() +
            ", primeiroNome='" + getPrimeiroNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", email='" + getEmail() + "'" +
            ", idade=" + getIdade() +
            "}";
    }
}
