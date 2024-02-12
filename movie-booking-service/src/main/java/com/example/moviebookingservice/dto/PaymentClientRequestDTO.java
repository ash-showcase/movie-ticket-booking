package com.example.moviebookingservice.dto;

import lombok.Data;

@Data
public class PaymentClientRequestDTO {

    private Long transactionId;

    private PaymentAction action;

    public static enum PaymentAction{
        CREATE, CHECK_STATUS, REVERSE
    }

}
