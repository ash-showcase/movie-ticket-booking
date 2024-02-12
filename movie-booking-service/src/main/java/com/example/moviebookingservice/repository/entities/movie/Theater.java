package com.example.moviebookingservice.repository.entities.movie;

import com.example.moviebookingservice.repository.entities.audit.Audited;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "theaters")
public class Theater extends Audited {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Boolean active;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private Set<MovieScreening> screenings;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;

}
