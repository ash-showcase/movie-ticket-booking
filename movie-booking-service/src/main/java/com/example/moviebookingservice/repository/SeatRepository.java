package com.example.moviebookingservice.repository;

import com.example.moviebookingservice.repository.entities.bookings.Seat;
import com.example.moviebookingservice.repository.entities.movie.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class SeatRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Seat> findSeatsByRowAndSeatNumber(List<Object[]> seatNumberTuple, Long screeningId) {
        return entityManager.createQuery("SELECT s FROM Seat s WHERE (s.rowNumber, s.seatNumber) IN :seats", Seat.class)
                .setParameter("seats", seatNumberTuple)
                .getResultList();
    }
}
