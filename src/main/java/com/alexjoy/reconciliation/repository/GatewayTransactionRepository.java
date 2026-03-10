package com.alexjoy.reconciliation.repository;

import com.alexjoy.reconciliation.model.GatewayTransaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayTransactionRepository extends JpaRepository<GatewayTransaction, Long> {
  Optional<GatewayTransaction> findByTransactionId(String transactionId);
}
