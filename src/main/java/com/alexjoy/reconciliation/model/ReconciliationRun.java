package com.alexjoy.reconciliation.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reconciliation_runs")
public class ReconciliationRun {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int totalGateway;

  @Column(nullable = false)
  private int totalLedger;

  @Column(nullable = false)
  private int matchedCount;

  @Column(nullable = false)
  private int unmatchedCount;

  @Column(nullable = false)
  private OffsetDateTime startedAt;

  @Column(nullable = false)
  private OffsetDateTime completedAt;

  public Long getId() { return id; }
  public int getTotalGateway() { return totalGateway; }
  public void setTotalGateway(int totalGateway) { this.totalGateway = totalGateway; }
  public int getTotalLedger() { return totalLedger; }
  public void setTotalLedger(int totalLedger) { this.totalLedger = totalLedger; }
  public int getMatchedCount() { return matchedCount; }
  public void setMatchedCount(int matchedCount) { this.matchedCount = matchedCount; }
  public int getUnmatchedCount() { return unmatchedCount; }
  public void setUnmatchedCount(int unmatchedCount) { this.unmatchedCount = unmatchedCount; }
  public OffsetDateTime getStartedAt() { return startedAt; }
  public void setStartedAt(OffsetDateTime startedAt) { this.startedAt = startedAt; }
  public OffsetDateTime getCompletedAt() { return completedAt; }
  public void setCompletedAt(OffsetDateTime completedAt) { this.completedAt = completedAt; }
}
