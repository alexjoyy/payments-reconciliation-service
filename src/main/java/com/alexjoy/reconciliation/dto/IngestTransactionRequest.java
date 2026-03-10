package com.alexjoy.reconciliation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record IngestTransactionRequest(
    @NotBlank String transactionId,
    @NotNull @DecimalMin("0.01") BigDecimal amount,
    @NotNull OffsetDateTime occurredAt
) {
}
