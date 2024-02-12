package com.example.moviebookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
public class SeatBookingResponseDTO {

    private List<SeatDTO> seat;
    private Long reservationId;
    private String movieName;
    private String theaterName;
    private LocalDateTime showTime;
    private BookingStatus bookingStatus;
    private PaymentDetailsDTO paymentDetails;

    @Data
    @AllArgsConstructor
    public static class TheaterDTO {
        private String name;
    }


    @Data
    @AllArgsConstructor
    public static class ShowDTO {
        private LocalDateTime time;
    }

    @Data
    @AllArgsConstructor
    public static class PaymentDetailsDTO {
        private Double total;
        private Double seatPrice;
        private Double convenienceFee;
        private Double tax;
    }


    public enum BookingStatus {
        RESERVED, CONFIRMED, FAILED, REFUNDED
    }
}
