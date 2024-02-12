package com.example.moviebookingservice.repository.entities.bookings;

import com.example.moviebookingservice.repository.entities.audit.Audited;
import com.example.moviebookingservice.repository.entities.movie.Theater;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seats")
public class Seat extends Audited {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private Integer seatNumber;
    private String rowNumber;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

}
