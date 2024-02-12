package com.example.moviebookingservice.repository;

import com.example.moviebookingservice.repository.entities.bookings.Booking;
import com.example.moviebookingservice.repository.entities.payments.FailedPaymentReversalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
