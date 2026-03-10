package com.alexjoy.reconciliation.dto;

import java.math.BigDecimal;

public record UnmatchedRecordResponse(
    String transactionId,
    BigDecimal gatewayAmount,
    BigDecimal ledgerAmount,
    String reason
) {
}
