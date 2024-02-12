package com.example.moviebookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PlaceOrderRequestDTO{

    @NotBlank
    private Long reservationId;

    private PaymentInfoDTO paymentInfoDTO;

    @Data
    @AllArgsConstructor
    public static class PaymentInfoDTO {
        private String cardNumber;
        private Integer expiryMonth;
        private Integer expiryYear;
        private  String vpa;
    }

}
