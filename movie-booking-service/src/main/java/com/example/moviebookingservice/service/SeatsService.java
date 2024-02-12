package com.example.moviebookingservice.service;

import com.example.moviebookingservice.dto.SeatDTO;
import com.example.moviebookingservice.repository.entities.bookings.Seat;

import java.util.List;
import java.util.Set;

public interface SeatsService {

    Seat getSeat(Integer seatNumber, String rowNumber, Long screeningId);

    Set<Seat> getSeats(List<SeatDTO> seats, Long screeningId);

}
