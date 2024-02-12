package com.example.moviebookingservice.repository.entities.bookings;

import com.example.moviebookingservice.repository.entities.audit.Audited;
import com.example.moviebookingservice.repository.entities.movie.MovieScreening;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reserved_seats", uniqueConstraints = { @UniqueConstraint(columnNames = { "seatNumber", "rowNumber", "show_id" }) })
public class BookedSeat extends Audited {
    private Integer seatNumber;
    private String rowNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;


}
