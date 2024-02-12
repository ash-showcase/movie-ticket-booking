package com.example.moviebookingservice.service.impl;

import com.example.moviebookingservice.dto.SeatDTO;
import com.example.moviebookingservice.repository.SeatRepository;
import com.example.moviebookingservice.repository.entities.bookings.Seat;
import com.example.moviebookingservice.service.SeatsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultSeatsService implements SeatsService {

    public DefaultSeatsService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    private SeatRepository seatRepository;

    @Override
    public Seat getSeat(Integer seatNumber, String rowNumber, Long screeningId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Seat> getSeats(List<SeatDTO> seats, Long screeningId) {
        List<Object[]> seatTuple = seats.stream()
                .map(seat -> new Object[]{seat.getRowNum(), seat.getSeatNum()})
                .collect(Collectors.toList());
        return new HashSet<>(seatRepository.findSeatsByRowAndSeatNumber(seatTuple, screeningId));
    }
}
