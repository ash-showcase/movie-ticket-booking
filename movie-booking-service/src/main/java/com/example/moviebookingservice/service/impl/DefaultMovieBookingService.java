package com.example.moviebookingservice.service.impl;

import com.example.moviebookingservice.client.PaymentClient;
import com.example.moviebookingservice.dto.PaymentClientRequestDTO;
import com.example.moviebookingservice.dto.PaymentClientResponseDTO;
import com.example.moviebookingservice.dto.PlaceOrderRequestDTO;
import com.example.moviebookingservice.dto.PlaceOrderResponseDTO;
import com.example.moviebookingservice.dto.SeatBookingConfirmationRequestDTO;
import com.example.moviebookingservice.dto.SeatBookingResponseDTO;
import com.example.moviebookingservice.exception.PaymentFailedException;
import com.example.moviebookingservice.exception.SeatAlreadyReservedException;
import com.example.moviebookingservice.repository.BookingRepository;
import com.example.moviebookingservice.repository.MovieBookingRepository;
import com.example.moviebookingservice.repository.ReservationRepository;
import com.example.moviebookingservice.repository.entities.bookings.BookedSeat;
import com.example.moviebookingservice.repository.entities.bookings.Booking;
import com.example.moviebookingservice.repository.entities.bookings.Reservation;
import com.example.moviebookingservice.repository.entities.bookings.ReservedSeat;
import com.example.moviebookingservice.repository.entities.bookings.Seat;
import com.example.moviebookingservice.repository.entities.movie.MovieScreening;
import com.example.moviebookingservice.service.MovieBookingService;
import com.example.moviebookingservice.service.SeatsService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultMovieBookingService implements MovieBookingService {

    @Autowired
    public DefaultMovieBookingService(MovieBookingRepository movieScheduleRepository, SeatsService seatsService, ReservationRepository reservationRepository, PaymentClient paymentClient, PaymentsRecoveryService paymentsRecoveryService, BookingRepository bookingRepository) {
        this.movieScheduleRepository = movieScheduleRepository;
        this.seatsService = seatsService;
        this.reservationRepository = reservationRepository;
        this.paymentClient = paymentClient;
        this.paymentsRecoveryService = paymentsRecoveryService;
        this.bookingRepository = bookingRepository;
    }

    private final MovieBookingRepository movieScheduleRepository;

    private final SeatsService seatsService;

    private final ReservationRepository reservationRepository;

    private final PaymentClient paymentClient;

    private final PaymentsRecoveryService paymentsRecoveryService;

    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public List<SeatBookingResponseDTO> reserveSeats(SeatBookingConfirmationRequestDTO request) {
        Optional<MovieScreening> movieScreeningOptional = movieScheduleRepository.findById(request.getScreeningId());
        MovieScreening movieScreening = movieScreeningOptional.orElseThrow(
                () -> new IllegalArgumentException(String.format("Screening ID: %d does not exist", request.getScreeningId())));
        Set<Seat> selectedSeats = seatsService.getSeats(request.getSeats(), request.getScreeningId());

        Reservation reservation = new Reservation();
        reservation.setTotalAmount(request.getAmount());
        reservation.setReservedSeats(selectedSeats.stream().map(seat -> {
            ReservedSeat reservedSeat = new ReservedSeat();
            reservedSeat.setSeatNumber(seat.getSeatNumber());
            reservedSeat.setRowNumber(seat.getRowNumber());
            reservedSeat.setShow(movieScreening);
            return reservedSeat;
        }).collect(Collectors.toSet()));

        try{
            reservationRepository.save(reservation);
        } catch (DuplicateKeyException | ConstraintViolationException cve){
            throw new SeatAlreadyReservedException();
        }

        return Collections.singletonList(
                SeatBookingResponseDTO.builder()
                    .seat(request.getSeats())
                    .movieName(movieScreening.getMovie().getTitle())
                    .theaterName( movieScreening.getTheater().getName())
                    .showTime(movieScreening.getStartTime())
                    .bookingStatus(SeatBookingResponseDTO.BookingStatus.RESERVED)
                    .build()
        );
    }

    @Override
    @Transactional
    public PlaceOrderResponseDTO placeOrder(PlaceOrderRequestDTO request) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(request.getReservationId());
        Reservation reservation = reservationOptional.orElseThrow(
                () -> new IllegalArgumentException(String.format("Reservation ID: %d does not exist or is expired.", request.getReservationId())));

        //Validate request and booking confirmation request have same seats and prices

        PaymentClientRequestDTO paymentClientRequest = new PaymentClientRequestDTO();
        paymentClientRequest.setAction(PaymentClientRequestDTO.PaymentAction.CREATE);
        paymentClientRequest.setTransactionId(request.getReservationId());
        PaymentClientResponseDTO payment = paymentClient.createPayment(paymentClientRequest);
        if(payment.getStatus().equals(PaymentClientResponseDTO.PaymentStatus.FAILED)){
            throw new PaymentFailedException();
        }

        Long showId = reservation.getReservedSeats().iterator().next().getShow().getId();
        Optional<MovieScreening> movieScreeningOptional = movieScheduleRepository.findById(showId);
        MovieScreening movieScreening = movieScreeningOptional.orElseThrow(
                () -> new IllegalArgumentException(String.format("Screening ID: %d does not exist", showId)));

        Booking booking = new Booking();
        booking.setStatus(payment.getStatus().toString());
        booking.setShow(movieScreening);
        booking.setBookedSeats(reservation.getReservedSeats().stream().map(seat -> {
            BookedSeat bookedSeat = new BookedSeat();
            bookedSeat.setSeatNumber(seat.getSeatNumber());
            bookedSeat.setRowNumber(seat.getRowNumber());
            return bookedSeat;
        }).collect(Collectors.toSet()));

        try{
            bookingRepository.save(booking);
        } catch(Exception e){
            PaymentClientRequestDTO paymentClientReversalRequest = new PaymentClientRequestDTO();
            paymentClientReversalRequest.setAction(PaymentClientRequestDTO.PaymentAction.REVERSE);
            paymentClientReversalRequest.setTransactionId(request.getReservationId());
            paymentsRecoveryService.retryPaymentReversal(paymentClientReversalRequest);
        }

        PlaceOrderResponseDTO responseDTO = new PlaceOrderResponseDTO();
        responseDTO.setBookingId(booking.getId());
        responseDTO.setBookingStatus(SeatBookingResponseDTO.BookingStatus.CONFIRMED);
        return responseDTO;
    }
}
