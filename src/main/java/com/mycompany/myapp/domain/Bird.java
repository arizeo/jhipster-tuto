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
 * A Bird.
 */
@Entity
@Table(name = "bird")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bird implements Serializable {

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
    @Column(name = "can_fly", nullable = false)
    private Boolean canFly;

    @NotNull
    @Column(name = "migratory", nullable = false)
    private Boolean migratory;

    @OneToMany(mappedBy = "bird")
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

    public Bird subspecies(String subspecies) {
        this.subspecies = subspecies;
        return this;
    }

    public void setSubspecies(String subspecies) {
        this.subspecies = subspecies;
    }

    public Boolean isCanFly() {
        return canFly;
    }

    public Bird canFly(Boolean canFly) {
        this.canFly = canFly;
        return this;
    }

    public void setCanFly(Boolean canFly) {
        this.canFly = canFly;
    }

    public Boolean isMigratory() {
        return migratory;
    }

    public Bird migratory(Boolean migratory) {
        this.migratory = migratory;
        return this;
    }

    public void setMigratory(Boolean migratory) {
        this.migratory = migratory;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public Bird animals(Set<Animal> animals) {
        this.animals = animals;
        return this;
    }

    public Bird addAnimal(Animal animal) {
        this.animals.add(animal);
        animal.setBird(this);
        return this;
    }

    public Bird removeAnimal(Animal animal) {
        this.animals.remove(animal);
        animal.setBird(null);
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
        Bird bird = (Bird) o;
        if (bird.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bird.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bird{" +
            "id=" + getId() +
            ", subspecies='" + getSubspecies() + "'" +
            ", canFly='" + isCanFly() + "'" +
            ", migratory='" + isMigratory() + "'" +
            "}";
    }
}
