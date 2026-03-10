package com.alexjoy.reconciliation.service;

import com.alexjoy.reconciliation.dto.IngestTransactionRequest;
import com.alexjoy.reconciliation.model.GatewayTransaction;
import com.alexjoy.reconciliation.repository.GatewayTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class GatewayIngestionService {

  private final GatewayTransactionRepository repository;

  public GatewayIngestionService(GatewayTransactionRepository repository) {
    this.repository = repository;
  }

  public GatewayTransaction ingest(IngestTransactionRequest request) {
    GatewayTransaction tx = repository.findByTransactionId(request.transactionId())
        .orElseGet(GatewayTransaction::new);

    tx.setTransactionId(request.transactionId());
    tx.setAmount(request.amount());
    tx.setOccurredAt(request.occurredAt());
    return repository.save(tx);
  }
}
