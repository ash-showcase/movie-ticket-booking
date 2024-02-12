package com.example.moviebookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderResponseDTO {

    @NotBlank
    private Long bookingId;

    private SeatBookingResponseDTO.BookingStatus bookingStatus;

}
