package com.example.moviescheduleservice.repository.entity.movie;

import com.example.moviescheduleservice.repository.entity.audit.Audited;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "screenings")
public class MovieScreening extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    private Boolean active;
    private LocalDateTime startTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theater_id")
    private Theater theater;

}
