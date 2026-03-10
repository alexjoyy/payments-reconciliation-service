# Payments Reconciliation Service (Java + Spring Boot)

Backend service that reconciles payment gateway transactions against internal ledger entries, stores reconciliation runs, and exposes unmatched reports (JSON + CSV).

## Stack

- Java 21
- Spring Boot 3
- PostgreSQL
- Docker Compose

## Run

```bash
cd "/Users/alexjoy/Documents/codex projects/payments-reconciliation-service"
docker compose up -d
mvn spring-boot:run
```

## Endpoints

### Ingest gateway transaction

```bash
curl -X POST http://localhost:8083/api/reconciliation/gateway-transactions \
  -H 'Content-Type: application/json' \
  -d '{
    "transactionId":"TXN-2001",
    "amount":999.00,
    "occurredAt":"2026-03-11T00:00:00+05:30"
  }'
```

### Ingest ledger entry

```bash
curl -X POST http://localhost:8083/api/reconciliation/ledger-entries \
  -H 'Content-Type: application/json' \
  -d '{
    "transactionId":"TXN-2001",
    "amount":999.00,
    "bookedAt":"2026-03-11T00:05:00+05:30"
  }'
```

### Run reconciliation manually

```bash
curl -X POST http://localhost:8083/api/reconciliation/run
```

### Get latest run summary

```bash
curl http://localhost:8083/api/reconciliation/runs/latest
```

### Get unmatched JSON report

```bash
curl http://localhost:8083/api/reconciliation/reports/unmatched
```

### Download unmatched CSV report

```bash
curl -L -o unmatched-report.csv http://localhost:8083/api/reconciliation/reports/unmatched.csv
```

## Notes

- Scheduler runs every hour by default (`reconciliation.schedule.cron`).
- Seed data is included to generate matched and unmatched cases quickly.
