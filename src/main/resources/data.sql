INSERT INTO gateway_transactions (transaction_id, amount, occurred_at)
VALUES
  ('TXN-1001', 499.00, NOW() - INTERVAL '2 hours'),
  ('TXN-1002', 799.00, NOW() - INTERVAL '1 hours'),
  ('TXN-1003', 1200.00, NOW() - INTERVAL '45 minutes')
ON CONFLICT (transaction_id) DO NOTHING;

INSERT INTO ledger_entries (transaction_id, amount, booked_at)
VALUES
  ('TXN-1001', 499.00, NOW() - INTERVAL '2 hours'),
  ('TXN-1002', 780.00, NOW() - INTERVAL '1 hours')
ON CONFLICT (transaction_id) DO NOTHING;
