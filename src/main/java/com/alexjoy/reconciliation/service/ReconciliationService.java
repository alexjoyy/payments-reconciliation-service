package com.alexjoy.reconciliation.service;

import com.alexjoy.reconciliation.dto.ReconciliationRunResponse;
import com.alexjoy.reconciliation.dto.UnmatchedRecordResponse;
import com.alexjoy.reconciliation.model.GatewayTransaction;
import com.alexjoy.reconciliation.model.LedgerEntry;
import com.alexjoy.reconciliation.model.ReconciliationRecord;
import com.alexjoy.reconciliation.model.ReconciliationRun;
import com.alexjoy.reconciliation.model.ReconciliationStatus;
import com.alexjoy.reconciliation.repository.GatewayTransactionRepository;
import com.alexjoy.reconciliation.repository.LedgerEntryRepository;
import com.alexjoy.reconciliation.repository.ReconciliationRecordRepository;
import com.alexjoy.reconciliation.repository.ReconciliationRunRepository;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReconciliationService {

  private final GatewayTransactionRepository gatewayRepository;
  private final LedgerEntryRepository ledgerRepository;
  private final ReconciliationRunRepository runRepository;
  private final ReconciliationRecordRepository recordRepository;

  public ReconciliationService(
      GatewayTransactionRepository gatewayRepository,
      LedgerEntryRepository ledgerRepository,
      ReconciliationRunRepository runRepository,
      ReconciliationRecordRepository recordRepository
  ) {
    this.gatewayRepository = gatewayRepository;
    this.ledgerRepository = ledgerRepository;
    this.runRepository = runRepository;
    this.recordRepository = recordRepository;
  }

  @Transactional
  public ReconciliationRunResponse reconcileNow() {
    List<GatewayTransaction> gatewayTransactions = gatewayRepository.findAll();
    List<LedgerEntry> ledgerEntries = ledgerRepository.findAll();

    Map<String, LedgerEntry> ledgerMap = new HashMap<>();
    for (LedgerEntry ledgerEntry : ledgerEntries) {
      ledgerMap.put(ledgerEntry.getTransactionId(), ledgerEntry);
    }

    ReconciliationRun run = new ReconciliationRun();
    run.setStartedAt(OffsetDateTime.now());
    run.setTotalGateway(gatewayTransactions.size());
    run.setTotalLedger(ledgerEntries.size());
    run = runRepository.save(run);

    int matched = 0;
    int unmatched = 0;

    for (GatewayTransaction gateway : gatewayTransactions) {
      LedgerEntry ledger = ledgerMap.get(gateway.getTransactionId());

      ReconciliationRecord record = new ReconciliationRecord();
      record.setRunId(run.getId());
      record.setTransactionId(gateway.getTransactionId());
      record.setGatewayAmount(gateway.getAmount());
      record.setLedgerAmount(ledger != null ? ledger.getAmount() : BigDecimal.ZERO);

      if (ledger == null) {
        record.setStatus(ReconciliationStatus.UNMATCHED);
        record.setReason("Missing ledger entry");
        unmatched++;
      } else if (gateway.getAmount().compareTo(ledger.getAmount()) != 0) {
        record.setStatus(ReconciliationStatus.UNMATCHED);
        record.setReason("Amount mismatch");
        unmatched++;
      } else {
        record.setStatus(ReconciliationStatus.MATCHED);
        record.setReason("Matched");
        matched++;
      }

      recordRepository.save(record);
    }

    run.setMatchedCount(matched);
    run.setUnmatchedCount(unmatched);
    run.setCompletedAt(OffsetDateTime.now());
    runRepository.save(run);

    return toResponse(run);
  }

  public ReconciliationRunResponse latestRun() {
    ReconciliationRun run = runRepository.findTopByOrderByIdDesc()
        .orElseThrow(() -> new IllegalArgumentException("No reconciliation run found"));
    return toResponse(run);
  }

  public List<UnmatchedRecordResponse> unmatchedForLatestRun() {
    Long runId = runRepository.findTopByOrderByIdDesc()
        .map(ReconciliationRun::getId)
        .orElseThrow(() -> new IllegalArgumentException("No reconciliation run found"));

    List<ReconciliationRecord> records = recordRepository.findByRunIdAndStatus(runId, ReconciliationStatus.UNMATCHED);
    List<UnmatchedRecordResponse> response = new ArrayList<>();

    for (ReconciliationRecord record : records) {
      response.add(new UnmatchedRecordResponse(
          record.getTransactionId(),
          record.getGatewayAmount(),
          record.getLedgerAmount(),
          record.getReason()
      ));
    }

    return response;
  }

  public String unmatchedCsvForLatestRun() {
    List<UnmatchedRecordResponse> unmatched = unmatchedForLatestRun();
    StringBuilder sb = new StringBuilder("transaction_id,gateway_amount,ledger_amount,reason\n");

    for (UnmatchedRecordResponse row : unmatched) {
      sb.append(row.transactionId()).append(',')
          .append(row.gatewayAmount().toPlainString()).append(',')
          .append(row.ledgerAmount().toPlainString()).append(',')
          .append(row.reason().replace(",", " "))
          .append('\n');
    }

    return sb.toString();
  }

  private ReconciliationRunResponse toResponse(ReconciliationRun run) {
    return new ReconciliationRunResponse(
        run.getId(),
        run.getTotalGateway(),
        run.getTotalLedger(),
        run.getMatchedCount(),
        run.getUnmatchedCount(),
        run.getStartedAt(),
        run.getCompletedAt()
    );
  }
}
