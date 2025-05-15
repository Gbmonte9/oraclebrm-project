
CREATE TABLE accounts (
    account_id NUMBER PRIMARY KEY,
    account_name VARCHAR2(100),
    balance NUMBER(10, 2)
);

CREATE TABLE transactions (
    transaction_id NUMBER PRIMARY KEY,
    account_id NUMBER,
    transaction_type VARCHAR2(50),
    amount NUMBER(10, 2),
    transaction_date TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);