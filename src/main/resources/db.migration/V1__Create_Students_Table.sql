CREATE TABLE students (
                          student_id VARCHAR(50) PRIMARY KEY,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          email VARCHAR(150) UNIQUE NOT NULL,
                          phone_number VARCHAR(20),
                          enrollment_date DATE NOT NULL,
                          program VARCHAR(150) NOT NULL,
                          year_of_study INTEGER NOT NULL CHECK (year_of_study >= 1 AND year_of_study <= 5),
                          is_active BOOLEAN NOT NULL DEFAULT TRUE,
                          account_balance DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP
);


CREATE INDEX idx_students_email ON students(email);
CREATE INDEX idx_students_is_active ON students(is_active);
CREATE INDEX idx_students_program ON students(program);
CREATE INDEX idx_students_year ON students(year_of_study);


COMMENT ON TABLE students IS 'Stores information about enrolled students at Makini University';
COMMENT ON COLUMN students.student_id IS 'Unique student identifier (e.g., STU001)';
COMMENT ON COLUMN students.is_active IS 'Indicates if student account is active and can receive payments';
COMMENT ON COLUMN students.account_balance IS 'Total amount paid by student (in KES)';