package com.alexjoy.reconciliation.service;

import com.alexjoy.reconciliation.dto.IngestLedgerRequest;
import com.alexjoy.reconciliation.model.LedgerEntry;
import com.alexjoy.reconciliation.repository.LedgerEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class LedgerIngestionService {

  private final LedgerEntryRepository repository;

  public LedgerIngestionService(LedgerEntryRepository repository) {
    this.repository = repository;
  }

  public LedgerEntry ingest(IngestLedgerRequest request) {
    LedgerEntry entry = repository.findByTransactionId(request.transactionId())
        .orElseGet(LedgerEntry::new);

    entry.setTransactionId(request.transactionId());
    entry.setAmount(request.amount());
    entry.setBookedAt(request.bookedAt());
    return repository.save(entry);
  }
}
