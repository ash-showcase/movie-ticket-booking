package com.example.moviebookingservice.client;

import com.example.moviebookingservice.dto.PaymentClientRequestDTO;
import com.example.moviebookingservice.dto.PaymentClientResponseDTO;
import com.example.moviebookingservice.dto.PlaceOrderRequestDTO;
import com.example.moviebookingservice.dto.SeatBookingResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-client", url = "${com.example.movie-booking-service.payment-client.url}")
public interface PaymentClient {

    @PostMapping(path = "/payments/createPayment")
    PaymentClientResponseDTO createPayment(@RequestBody PaymentClientRequestDTO requestDTO);

    @PostMapping(path = "/payments/reversePayment")
    PaymentClientResponseDTO reversePayment(@RequestBody PaymentClientRequestDTO requestDTO);

    @PostMapping(path = "/payments/checkStatus")
    PaymentClientResponseDTO checkStatus(@RequestBody PaymentClientRequestDTO requestDTO);

}
