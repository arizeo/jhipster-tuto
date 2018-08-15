package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AnimalCarer.
 */
@Entity
@Table(name = "animal_carer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnimalCarer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "animalcarers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Animal> animals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AnimalCarer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public AnimalCarer animals(Set<Animal> animals) {
        this.animals = animals;
        return this;
    }

    public AnimalCarer addAnimal(Animal animal) {
        this.animals.add(animal);
        animal.getAnimalcarers().add(this);
        return this;
    }

    public AnimalCarer removeAnimal(Animal animal) {
        this.animals.remove(animal);
        animal.getAnimalcarers().remove(this);
        return this;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
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
        AnimalCarer animalCarer = (AnimalCarer) o;
        if (animalCarer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalCarer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalCarer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
