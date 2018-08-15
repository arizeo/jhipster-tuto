package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Animal.
 */
@Entity
@Table(name = "animal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "weight", nullable = false)
    private Integer weight;

    @NotNull
    @Column(name = "has_owner", nullable = false)
    private Boolean hasOwner;

    @NotNull
    @Min(value = 0)
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "speed", nullable = false)
    private Double speed;

    @NotNull
    @Column(name = "height", nullable = false)
    private Float height;

    @NotNull
    @Size(max = 15)
    @Column(name = "diet", length = 15, nullable = false)
    private String diet;

    @NotNull
    @Column(name = "time_stamp", nullable = false)
    private ZonedDateTime timeStamp;

    @OneToOne
    @JoinColumn(unique = true)
    private Statistics statistics;

    @OneToMany(mappedBy = "animal")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Habitat> habitats = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "animal_animalcarer",
               joinColumns = @JoinColumn(name = "animals_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "animalcarers_id", referencedColumnName = "id"))
    private Set<AnimalCarer> animalcarers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("animals")
    private Bird bird;

    @ManyToOne
    @JsonIgnoreProperties("animals")
    private Reptile reptile;

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

    public Animal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public Animal weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean isHasOwner() {
        return hasOwner;
    }

    public Animal hasOwner(Boolean hasOwner) {
        this.hasOwner = hasOwner;
        return this;
    }

    public void setHasOwner(Boolean hasOwner) {
        this.hasOwner = hasOwner;
    }

    public Integer getAge() {
        return age;
    }

    public Animal age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSpeed() {
        return speed;
    }

    public Animal speed(Double speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Float getHeight() {
        return height;
    }

    public Animal height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getDiet() {
        return diet;
    }

    public Animal diet(String diet) {
        this.diet = diet;
        return this;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public Animal timeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public Animal statistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public Set<Habitat> getHabitats() {
        return habitats;
    }

    public Animal habitats(Set<Habitat> habitats) {
        this.habitats = habitats;
        return this;
    }

    public Animal addHabitat(Habitat habitat) {
        this.habitats.add(habitat);
        habitat.setAnimal(this);
        return this;
    }

    public Animal removeHabitat(Habitat habitat) {
        this.habitats.remove(habitat);
        habitat.setAnimal(null);
        return this;
    }

    public void setHabitats(Set<Habitat> habitats) {
        this.habitats = habitats;
    }

    public Set<AnimalCarer> getAnimalcarers() {
        return animalcarers;
    }

    public Animal animalcarers(Set<AnimalCarer> animalCarers) {
        this.animalcarers = animalCarers;
        return this;
    }

    public Animal addAnimalcarer(AnimalCarer animalCarer) {
        this.animalcarers.add(animalCarer);
        animalCarer.getAnimals().add(this);
        return this;
    }

    public Animal removeAnimalcarer(AnimalCarer animalCarer) {
        this.animalcarers.remove(animalCarer);
        animalCarer.getAnimals().remove(this);
        return this;
    }

    public void setAnimalcarers(Set<AnimalCarer> animalCarers) {
        this.animalcarers = animalCarers;
    }

    public Bird getBird() {
        return bird;
    }

    public Animal bird(Bird bird) {
        this.bird = bird;
        return this;
    }

    public void setBird(Bird bird) {
        this.bird = bird;
    }

    public Reptile getReptile() {
        return reptile;
    }

    public Animal reptile(Reptile reptile) {
        this.reptile = reptile;
        return this;
    }

    public void setReptile(Reptile reptile) {
        this.reptile = reptile;
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
        Animal animal = (Animal) o;
        if (animal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Animal{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", weight=" + getWeight() +
            ", hasOwner='" + isHasOwner() + "'" +
            ", age=" + getAge() +
            ", speed=" + getSpeed() +
            ", height=" + getHeight() +
            ", diet='" + getDiet() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            "}";
    }
}
