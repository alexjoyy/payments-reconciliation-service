package com.alexjoy.reconciliation.dto;

import java.time.OffsetDateTime;

public record ReconciliationRunResponse(
    Long runId,
    int totalGateway,
    int totalLedger,
    int matchedCount,
    int unmatchedCount,
    OffsetDateTime startedAt,
    OffsetDateTime completedAt
) {
}
