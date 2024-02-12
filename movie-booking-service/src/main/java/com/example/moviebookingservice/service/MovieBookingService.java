package com.example.moviebookingservice.service;

import com.example.moviebookingservice.dto.PlaceOrderRequestDTO;
import com.example.moviebookingservice.dto.PlaceOrderResponseDTO;
import com.example.moviebookingservice.dto.SeatBookingConfirmationRequestDTO;
import com.example.moviebookingservice.dto.SeatBookingResponseDTO;

import java.util.List;

public interface MovieBookingService {

    List<SeatBookingResponseDTO> reserveSeats(SeatBookingConfirmationRequestDTO request);

    PlaceOrderResponseDTO placeOrder(PlaceOrderRequestDTO request);
}
