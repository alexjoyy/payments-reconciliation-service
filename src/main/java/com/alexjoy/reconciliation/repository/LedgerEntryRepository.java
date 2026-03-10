package com.alexjoy.reconciliation.repository;

import com.alexjoy.reconciliation.model.LedgerEntry;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
  Optional<LedgerEntry> findByTransactionId(String transactionId);
}
