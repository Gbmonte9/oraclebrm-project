#!/bin/bash

USER="system"
PASS="root"
DB="localhost/XEPDB1"

sqlplus -S ${USER}/${PASS}@${DB} <<EOF
INSERT INTO transactions (transaction_id, account_id, transaction_type, amount, transaction_date)
VALUES (transactions_seq.NEXTVAL, 1, 'Pagamento', 150.00, CURRENT_TIMESTAMP);
COMMIT;

-- Atualizar saldo na conta
UPDATE accounts SET balance = balance - 150.00 WHERE account_id = 1;
COMMIT;

-- Exibir todas as transações da conta 1 para conferência
SET PAGESIZE 50
SET LINESIZE 100
COLUMN transaction_type FORMAT A20
COLUMN amount FORMAT 999999.99
COLUMN transaction_date FORMAT A20

SELECT transaction_id, transaction_type, amount, transaction_date
FROM transactions
WHERE account_id = 1
ORDER BY transaction_date DESC;

EXIT;
EOF