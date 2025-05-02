Feature: System automatically generates payment files for different payment methods

  Rule: Payment files must be generated in the correct format for each payment method

    Example: Generating ACH payment file
      Given there are approved invoices for ACH payments
      When the system generates the payment file
      Then the file should be in NACHA format
      And contain all required ACH payment details

    Example: Generating Wire transfer file
      Given there are approved invoices for wire transfers
      When the system generates the payment file
      Then the file should be in MT103 format
      And contain all required wire transfer details

    Example: Generating SWIFT payment file
      Given there are approved invoices for SWIFT payments
      When the system generates the payment file
      Then the file should be in ISO20022 format
      And contain all required SWIFT payment details

    Example: Generating Check payment file
      Given there are approved invoices for check payments
      When the system generates the payment file
      Then the file should be in check printing format
      And contain all required check payment details

  Rule: Payment files must include only approved invoices

    @not-implemented
    Example: Excluding unapproved invoices
      Given there are both approved and unapproved invoices
      When the system generates payment files
      Then only approved invoices should be included in the files

    @not-implemented
    Example: Processing multiple approved invoices
      Given there are multiple approved invoices for the same payment method
      When the system generates the payment file
      Then all approved invoices should be consolidated in a single file

  Rule: Payment files must include correct payment amounts and vendor details

    @not-implemented
    Example: Verifying payment details
      Given there is an approved invoice for payment
      When the system generates the payment file
      Then the payment amount should match the invoice amount
      And the vendor banking details should be correctly included

    @not-implemented
    Example: Handling multiple currencies
      Given there are approved invoices in different currencies
      When the system generates payment files
      Then each payment should be in the correct currency
      And the appropriate exchange rates should be applied

  Rule: Generated files must be properly validated before completion

    @not-implemented
    Example: Validating file totals
      Given a payment file has been generated
      When the system performs validation
      Then the total amount should match the sum of all included invoices
      And the count of payments should match the number of processed invoices

    @not-implemented
    Example: Handling validation failures
      Given a payment file contains invalid data
      When the system performs validation
      Then the file generation should be marked as failed
      And an error report should be generated
