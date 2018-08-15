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
 * A Reptile.
 */
@Entity
@Table(name = "reptile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reptile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "subspecies", length = 15, nullable = false)
    private String subspecies;

    @NotNull
    @Min(value = 0)
    @Column(name = "legs_nbr", nullable = false)
    private Integer legsNbr;

    @NotNull
    @Column(name = "shell", nullable = false)
    private Boolean shell;

    @OneToMany(mappedBy = "reptile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Animal> animals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubspecies() {
        return subspecies;
    }

    public Reptile subspecies(String subspecies) {
        this.subspecies = subspecies;
        return this;
    }

    public void setSubspecies(String subspecies) {
        this.subspecies = subspecies;
    }

    public Integer getLegsNbr() {
        return legsNbr;
    }

    public Reptile legsNbr(Integer legsNbr) {
        this.legsNbr = legsNbr;
        return this;
    }

    public void setLegsNbr(Integer legsNbr) {
        this.legsNbr = legsNbr;
    }

    public Boolean isShell() {
        return shell;
    }

    public Reptile shell(Boolean shell) {
        this.shell = shell;
        return this;
    }

    public void setShell(Boolean shell) {
        this.shell = shell;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public Reptile animals(Set<Animal> animals) {
        this.animals = animals;
        return this;
    }

    public Reptile addAnimal(Animal animal) {
        this.animals.add(animal);
        animal.setReptile(this);
        return this;
    }

    public Reptile removeAnimal(Animal animal) {
        this.animals.remove(animal);
        animal.setReptile(null);
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
        Reptile reptile = (Reptile) o;
        if (reptile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reptile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reptile{" +
            "id=" + getId() +
            ", subspecies='" + getSubspecies() + "'" +
            ", legsNbr=" + getLegsNbr() +
            ", shell='" + isShell() + "'" +
            "}";
    }
}
