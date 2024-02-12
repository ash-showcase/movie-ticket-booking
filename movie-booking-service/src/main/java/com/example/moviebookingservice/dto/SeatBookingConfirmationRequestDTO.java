package com.example.moviebookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatBookingConfirmationRequestDTO {

    @NotBlank
    private Long screeningId;

    private Double amount;

    @NotEmpty
    private List<SeatDTO> seats;

}
