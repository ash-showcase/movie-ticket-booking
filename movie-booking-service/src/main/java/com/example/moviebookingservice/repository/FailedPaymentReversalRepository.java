package com.example.moviebookingservice.repository;

import com.example.moviebookingservice.repository.entities.movie.Movie;
import com.example.moviebookingservice.repository.entities.payments.FailedPaymentReversalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedPaymentReversalRepository extends JpaRepository<FailedPaymentReversalRecord, Long> {
}
