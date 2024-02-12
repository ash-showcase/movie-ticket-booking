package com.example.moviebookingservice.repository.entities.payments;

import com.example.moviebookingservice.repository.entities.audit.Audited;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="failed_payment_reversal_record")
public class FailedPaymentReversalRecord extends Audited {

    private String transactionId;
    private LocalDateTime lastAttempted;

}
