CREATE TABLE payments (
                          payment_id NUMERIC(36) PRIMARY KEY,
                          student_id VARCHAR(50) NOT NULL,
                          amount DOUBLE PRECISION NOT NULL CHECK (amount > 0),
                          currency VARCHAR(3) NOT NULL DEFAULT 'KES',
                          payment_method VARCHAR(50) NOT NULL,
                          transaction_reference VARCHAR(100) UNIQUE NOT NULL,
                          payment_status VARCHAR(20) NOT NULL,
                          payment_date TIMESTAMP NOT NULL,
                          description VARCHAR(500),
                          customer_name VARCHAR(200),
                          customer_phone VARCHAR(20),
                          customer_email VARCHAR(150),
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraint
                          CONSTRAINT fk_payment_student
                              FOREIGN KEY (student_id)
                                  REFERENCES students(student_id)
                                  ON DELETE RESTRICT
                                  ON UPDATE CASCADE,

    -- Check constraints
                          CONSTRAINT chk_payment_status
                              CHECK (payment_status IN ('SUCCESS', 'FAILED', 'PENDING')),

                          CONSTRAINT chk_payment_method
                              CHECK (payment_method IN ('MPESA', 'CARD', 'BANK_TRANSFER', 'PESALINK', 'AIRTEL_MONEY', 'TKASH'))
);


CREATE INDEX idx_payments_student_id ON payments(student_id);
CREATE INDEX idx_payments_method ON payments(payment_method);
CREATE INDEX idx_payments_date ON payments(payment_date);
CREATE INDEX idx_payments_status ON payments(payment_status);
CREATE INDEX idx_payments_transaction_ref ON payments(transaction_reference);
CREATE INDEX idx_payments_created_at ON payments(created_at);


COMMENT ON TABLE payments IS 'Stores all payment transactions received through Cellulant gateway';
COMMENT ON COLUMN payments.payment_id IS 'Unique payment identifier (UUID)';
COMMENT ON COLUMN payments.transaction_reference IS 'Cellulant transaction reference - must be unique to prevent duplicates';
COMMENT ON COLUMN payments.payment_method IS 'Payment method used (MPESA, CARD, etc.)';
COMMENT ON COLUMN payments.payment_status IS 'Payment status: SUCCESS, FAILED, or PENDING';