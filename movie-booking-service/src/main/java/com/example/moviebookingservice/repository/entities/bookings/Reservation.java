package com.example.moviebookingservice.repository.entities.bookings;

import com.example.moviebookingservice.repository.entities.audit.Audited;
import com.example.moviebookingservice.repository.entities.movie.MovieScreening;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation extends Audited {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReservedSeat> reservedSeats;

    private Long userId;
    private Double totalAmount;


}
