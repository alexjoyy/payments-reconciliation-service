package com.alexjoy.reconciliation.controller;

import com.alexjoy.reconciliation.api.ApiResponse;
import com.alexjoy.reconciliation.dto.IngestLedgerRequest;
import com.alexjoy.reconciliation.dto.IngestTransactionRequest;
import com.alexjoy.reconciliation.dto.ReconciliationRunResponse;
import com.alexjoy.reconciliation.dto.UnmatchedRecordResponse;
import com.alexjoy.reconciliation.service.GatewayIngestionService;
import com.alexjoy.reconciliation.service.LedgerIngestionService;
import com.alexjoy.reconciliation.service.ReconciliationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

  private final GatewayIngestionService gatewayIngestionService;
  private final LedgerIngestionService ledgerIngestionService;
  private final ReconciliationService reconciliationService;

  public ReconciliationController(
      GatewayIngestionService gatewayIngestionService,
      LedgerIngestionService ledgerIngestionService,
      ReconciliationService reconciliationService
  ) {
    this.gatewayIngestionService = gatewayIngestionService;
    this.ledgerIngestionService = ledgerIngestionService;
    this.reconciliationService = reconciliationService;
  }

  @PostMapping("/gateway-transactions")
  public ApiResponse<String> ingestGateway(@RequestBody @Valid IngestTransactionRequest request, HttpServletRequest httpRequest) {
    gatewayIngestionService.ingest(request);
    return new ApiResponse<>(OffsetDateTime.now().toString(), HttpStatus.OK.value(), "Gateway transaction ingested", httpRequest.getRequestURI(), request.transactionId());
  }

  @PostMapping("/ledger-entries")
  public ApiResponse<String> ingestLedger(@RequestBody @Valid IngestLedgerRequest request, HttpServletRequest httpRequest) {
    ledgerIngestionService.ingest(request);
    return new ApiResponse<>(OffsetDateTime.now().toString(), HttpStatus.OK.value(), "Ledger entry ingested", httpRequest.getRequestURI(), request.transactionId());
  }

  @PostMapping("/run")
  public ApiResponse<ReconciliationRunResponse> runNow(HttpServletRequest httpRequest) {
    ReconciliationRunResponse run = reconciliationService.reconcileNow();
    return new ApiResponse<>(OffsetDateTime.now().toString(), HttpStatus.OK.value(), "Reconciliation completed", httpRequest.getRequestURI(), run);
  }

  @GetMapping("/runs/latest")
  public ApiResponse<ReconciliationRunResponse> latestRun(HttpServletRequest httpRequest) {
    ReconciliationRunResponse run = reconciliationService.latestRun();
    return new ApiResponse<>(OffsetDateTime.now().toString(), HttpStatus.OK.value(), "Latest run fetched", httpRequest.getRequestURI(), run);
  }

  @GetMapping("/reports/unmatched")
  public ApiResponse<List<UnmatchedRecordResponse>> unmatched(HttpServletRequest httpRequest) {
    List<UnmatchedRecordResponse> data = reconciliationService.unmatchedForLatestRun();
    return new ApiResponse<>(OffsetDateTime.now().toString(), HttpStatus.OK.value(), "Unmatched records fetched", httpRequest.getRequestURI(), data);
  }

  @GetMapping("/reports/unmatched.csv")
  public ResponseEntity<String> unmatchedCsv() {
    String csv = reconciliationService.unmatchedCsvForLatestRun();
    return ResponseEntity.ok()
        .contentType(MediaType.TEXT_PLAIN)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=unmatched-report.csv")
        .body(csv);
  }
}
