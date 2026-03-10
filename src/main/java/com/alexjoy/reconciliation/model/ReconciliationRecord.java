package com.alexjoy.reconciliation.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reconciliation_records")
public class ReconciliationRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long runId;

  @Column(nullable = false)
  private String transactionId;

  @Column(nullable = false)
  private BigDecimal gatewayAmount;

  @Column(nullable = false)
  private BigDecimal ledgerAmount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReconciliationStatus status;

  @Column(nullable = false)
  private String reason;

  @Column(nullable = false)
  private OffsetDateTime createdAt;

  @PrePersist
  void onCreate() { createdAt = OffsetDateTime.now(); }

  public Long getId() { return id; }
  public Long getRunId() { return runId; }
  public void setRunId(Long runId) { this.runId = runId; }
  public String getTransactionId() { return transactionId; }
  public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
  public BigDecimal getGatewayAmount() { return gatewayAmount; }
  public void setGatewayAmount(BigDecimal gatewayAmount) { this.gatewayAmount = gatewayAmount; }
  public BigDecimal getLedgerAmount() { return ledgerAmount; }
  public void setLedgerAmount(BigDecimal ledgerAmount) { this.ledgerAmount = ledgerAmount; }
  public ReconciliationStatus getStatus() { return status; }
  public void setStatus(ReconciliationStatus status) { this.status = status; }
  public String getReason() { return reason; }
  public void setReason(String reason) { this.reason = reason; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
