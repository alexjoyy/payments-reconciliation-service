package com.alexjoy.reconciliation.repository;

import com.alexjoy.reconciliation.model.ReconciliationRun;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconciliationRunRepository extends JpaRepository<ReconciliationRun, Long> {
  Optional<ReconciliationRun> findTopByOrderByIdDesc();
}
