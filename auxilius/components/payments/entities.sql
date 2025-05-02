-- Payment batches table
CREATE TABLE payment_batches (
    batch_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    created_by uuid NOT NULL,
    status payment_status_type NOT NULL,
    payment_date date NOT NULL,
    is_recurring boolean DEFAULT false,
    recurring_schedule varchar(50),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Accounting periods table
CREATE TABLE accounting_periods (
    period_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    start_date date NOT NULL,
    end_date date NOT NULL,
    is_open boolean DEFAULT true,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Vendors table
CREATE TABLE vendors (
    vendor_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    vendor_name varchar(100) NOT NULL,
    bank_account_number varchar(50),
    bank_routing_number varchar(50),
    swift_code varchar(20),
    is_blocked boolean DEFAULT false,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Payments table
CREATE TABLE payments (
    payment_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    batch_id uuid REFERENCES payment_batches(batch_id),
    vendor_id uuid NOT NULL REFERENCES vendors(vendor_id),
    payment_method payment_method_type NOT NULL,
    amount decimal(15,2) NOT NULL,
    check_number varchar(20),
    status payment_status_type NOT NULL,
    accounting_period_id uuid REFERENCES accounting_periods(period_id),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Documents table
CREATE TABLE documents (
    document_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_id uuid REFERENCES payments(payment_id),
    document_type document_type NOT NULL,
    file_path varchar(255) NOT NULL,
    uploaded_by uuid NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Aging analysis table
CREATE TABLE aging_analysis (
    analysis_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_id uuid NOT NULL REFERENCES payments(payment_id),
    days_outstanding integer NOT NULL,
    aging_category varchar(20) NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Approval workflow table
CREATE TABLE approval_workflows (
    workflow_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_id uuid NOT NULL REFERENCES payments(payment_id),
    approver_id uuid NOT NULL,
    status payment_status_type NOT NULL,
    comments text,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Audit logs table
CREATE TABLE audit_logs (
    log_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    entity_type varchar(50) NOT NULL,
    entity_id uuid NOT NULL,
    action varchar(50) NOT NULL,
    actor_id uuid NOT NULL,
    details jsonb,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Payment validation errors table
CREATE TABLE validation_errors (
    error_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_id uuid NOT NULL REFERENCES payments(payment_id),
    error_type varchar(50) NOT NULL,
    error_message text NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
)
