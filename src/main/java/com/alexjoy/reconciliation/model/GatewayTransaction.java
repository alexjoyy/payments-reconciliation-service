package com.alexjoy.reconciliation.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "gateway_transactions")
public class GatewayTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String transactionId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private OffsetDateTime occurredAt;

  public Long getId() { return id; }
  public String getTransactionId() { return transactionId; }
  public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public OffsetDateTime getOccurredAt() { return occurredAt; }
  public void setOccurredAt(OffsetDateTime occurredAt) { this.occurredAt = occurredAt; }
}
