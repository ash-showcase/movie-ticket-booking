package com.example.moviebookingservice.controller;

import com.example.moviebookingservice.dto.PlaceOrderRequestDTO;
import com.example.moviebookingservice.dto.SeatBookingConfirmationRequestDTO;
import com.example.moviebookingservice.dto.SeatBookingResponseDTO;
import com.example.moviebookingservice.exception.SeatAlreadyReservedException;
import com.example.moviebookingservice.service.MovieBookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/booking")
@Validated
public class MovieBookingsController {

    @Autowired
    private MovieBookingService movieBookingService;

    @PostMapping(path = "/selectSeats")
    @ResponseBody
    public List<SeatBookingResponseDTO> reserveSeats(@Valid SeatBookingConfirmationRequestDTO request) {
        return movieBookingService.reserveSeats(request);
    }

    @PostMapping(path = "/order")
    @ResponseBody
    public List<SeatBookingResponseDTO> placeOrder(@Valid PlaceOrderRequestDTO request) {
        return movieBookingService.placeOrder(request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleRedirectException(final Exception e, final HttpServletRequest request) throws Exception {
        log.error("Error handling request", e);
        //Probably not send the internal error message in the response.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error handling request");
    }

    @ExceptionHandler(SeatAlreadyReservedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleRedirectException(final SeatAlreadyReservedException e, final HttpServletRequest request) throws Exception {
        log.error("Seats already reserved", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Seats you are trying to book may not be available.");
    }

}
