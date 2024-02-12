package com.example.moviebookingservice.service.impl;

import com.example.moviebookingservice.client.PaymentClient;
import com.example.moviebookingservice.dto.PaymentClientRequestDTO;
import com.example.moviebookingservice.dto.PaymentClientResponseDTO;
import com.example.moviebookingservice.exception.PaymentReversalFailedException;
import com.example.moviebookingservice.repository.FailedPaymentReversalRepository;
import com.example.moviebookingservice.repository.entities.payments.FailedPaymentReversalRecord;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentsRecoveryService {

    public PaymentsRecoveryService(PaymentClient paymentClient, FailedPaymentReversalRepository failedPaymentReversalRepository) {
        this.paymentClient = paymentClient;
        this.failedPaymentReversalRepository = failedPaymentReversalRepository;
    }

    private final PaymentClient paymentClient;
    private final FailedPaymentReversalRepository failedPaymentReversalRepository;

    private static final PaymentReversalFailedException PAYMENT_REVERSAL_FAILED_EXCEPTION = new PaymentReversalFailedException();

    @Retryable(retryFor = PaymentReversalFailedException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    PaymentClientResponseDTO retryPaymentReversal(PaymentClientRequestDTO request) throws PaymentReversalFailedException{
        PaymentClientResponseDTO response = paymentClient.reversePayment(request);
        if(!response.getStatus().equals(PaymentClientResponseDTO.PaymentStatus.REVERSED)){
            throw PAYMENT_REVERSAL_FAILED_EXCEPTION;
        }
        return response;
    }

    @Recover
    PaymentClientResponseDTO recover(PaymentClientRequestDTO request){
        FailedPaymentReversalRecord failedPaymentReversalRecord = new FailedPaymentReversalRecord();
        failedPaymentReversalRecord.setTransactionId(request.getTransactionId());
        failedPaymentReversalRecord.setLastAttempted(LocalDateTime.now());

        failedPaymentReversalRepository.save(failedPaymentReversalRecord);

        PaymentClientResponseDTO response = new PaymentClientResponseDTO();
        response.setStatus(PaymentClientResponseDTO.PaymentStatus.PENDING);
        return response;
    }
}
