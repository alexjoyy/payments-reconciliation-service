package com.alexjoy.reconciliation.scheduler;

import com.alexjoy.reconciliation.service.ReconciliationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReconciliationScheduler {

  private final ReconciliationService reconciliationService;

  public ReconciliationScheduler(ReconciliationService reconciliationService) {
    this.reconciliationService = reconciliationService;
  }

  @Scheduled(cron = "${reconciliation.schedule.cron}")
  public void runScheduledReconciliation() {
    reconciliationService.reconcileNow();
  }
}
