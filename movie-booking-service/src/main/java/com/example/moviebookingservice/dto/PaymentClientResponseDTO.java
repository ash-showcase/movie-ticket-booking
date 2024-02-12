package com.example.moviebookingservice.dto;

import lombok.Data;

@Data
public class PaymentClientResponseDTO {

    private String transactionId;

    private PaymentStatus status;

    public enum PaymentStatus{
        SUCCESS, PENDING, FAILED, REVERSED
    }

}
