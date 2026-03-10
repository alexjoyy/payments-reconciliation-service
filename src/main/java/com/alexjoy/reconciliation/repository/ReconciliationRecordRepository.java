package com.alexjoy.reconciliation.repository;

import com.alexjoy.reconciliation.model.ReconciliationRecord;
import com.alexjoy.reconciliation.model.ReconciliationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconciliationRecordRepository extends JpaRepository<ReconciliationRecord, Long> {
  List<ReconciliationRecord> findByRunIdAndStatus(Long runId, ReconciliationStatus status);
}
