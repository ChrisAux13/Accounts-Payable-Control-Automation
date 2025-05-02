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
)