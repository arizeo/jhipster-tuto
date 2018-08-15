package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.HabitatType;

/**
 * A Habitat.
 */
@Entity
@Table(name = "habitat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Habitat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private HabitatType name;

    @NotNull
    @Size(max = 15)
    @Column(name = "temperature", length = 15, nullable = false)
    private String temperature;

    @ManyToOne
    @JsonIgnoreProperties("habitats")
    private Animal animal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HabitatType getName() {
        return name;
    }

    public Habitat name(HabitatType name) {
        this.name = name;
        return this;
    }

    public void setName(HabitatType name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public Habitat temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Habitat animal(Animal animal) {
        this.animal = animal;
        return this;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Habitat habitat = (Habitat) o;
        if (habitat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), habitat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Habitat{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", temperature='" + getTemperature() + "'" +
            "}";
    }
}
