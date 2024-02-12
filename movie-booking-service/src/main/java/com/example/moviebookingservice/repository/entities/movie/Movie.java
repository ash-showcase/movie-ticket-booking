package com.example.moviebookingservice.repository.entities.movie;

import com.example.moviebookingservice.repository.entities.audit.Audited;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    private Integer durationMinutes;
    private String title;
    private Boolean active;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieScreening> movieScreenings;

}
