Feature: System creates positive pay files for check payments

  Rule: Positive pay files must be generated for all check payments in a payment run

    Example: Generate positive pay file for single check payment
      Given a payment run contains a check payment for vendor "ABC Corp"
      When the system processes the payment run
      Then a positive pay file should be generated containing the check payment details

    Example: Generate positive pay file for multiple check payments
      Given a payment run contains check payments for multiple vendors
      When the system processes the payment run
      Then a single positive pay file should be generated containing all check payment details

  Rule: Positive pay files must include required check payment information

    Example: Verify required check payment details in positive pay file
      Given a check payment is processed
      When the positive pay file is generated
      Then the file should contain the check number
      And the file should contain the payment amount
      And the file should contain the payee name
      And the file should contain the payment date

  Rule: Positive pay files should only include check payments

    Example: Payment run with mixed payment methods
      Given a payment run contains both check and ACH payments
      When the system generates the positive pay file
      Then only the check payments should be included in the positive pay file

  Rule: Positive pay files must follow bank-specific format requirements

    Example: Generate positive pay file for specific bank format
      Given the company's bank requires a specific file format
      When the positive pay file is generated
      Then the file should conform to the bank's required format specification

  Rule: System must maintain audit trail of positive pay file generation

    Example: Record positive pay file generation details
      Given a positive pay file is generated
      When the generation process completes
      Then the system should record the generation timestamp
      And the system should record the number of checks included
      And the system should record the total value of checks included

  Rule: System must handle failed positive pay file generation

    Example: Handle file generation failure
      Given a payment run contains check payments
      When the positive pay file generation fails
      Then the system should log the error
      And the system should notify the appropriate personnel
      And the payment run status should reflect the failure
