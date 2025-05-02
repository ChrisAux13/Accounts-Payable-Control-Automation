Feature: AP clerk initiates automated payment run for approved invoices

  Rule: Only approved invoices should be included in payment run

    Example: Payment run processes only approved invoices
      Given there are approved and unapproved invoices in the system
      When the AP clerk initiates a payment run
      Then only approved invoices are selected for processing

    Example: No approved invoices available
      Given there are no approved invoices in the system
      When the AP clerk initiates a payment run
      Then the system returns a "no eligible invoices" message

  Rule: Payment run must respect payment due dates

    Example: Process only invoices due for payment
      Given there are approved invoices in the system
      And some invoices are due for payment
      And some invoices are not yet due
      When the AP clerk initiates a payment run
      Then only invoices due for payment are processed

  Rule: Payment run must enforce payment amount limits

    Example: Total payment run amount within daily limit
      Given there are approved invoices ready for payment
      And the total amount is within daily payment limits
      When the AP clerk initiates a payment run
      Then all eligible invoices are processed

    Example: Total payment run amount exceeds daily limit
      Given there are approved invoices ready for payment
      And the total amount exceeds daily payment limits
      When the AP clerk initiates a payment run
      Then the system generates a limit exceeded warning
      And requires additional authorization

  Rule: Payment run must prevent duplicate payments

    Example: Prevent processing of already paid invoices
      Given there are approved invoices in the system
      And some invoices have been previously paid
      When the AP clerk initiates a payment run
      Then only unpaid invoices are processed
      And previously paid invoices are excluded

  Rule: Payment run must generate an audit trail

    Example: Record payment run details
      Given there are approved invoices ready for payment
      When the AP clerk initiates a payment run
      Then the system creates a payment run record
      And logs the details of all processed invoices
      And records the identity of the initiating clerk

  Rule: Payment run must validate vendor payment information

    Example: Process only invoices with valid vendor payment details
      Given there are approved invoices ready for payment
      And some vendors have incomplete payment information
      When the AP clerk initiates a payment run
      Then only invoices with complete vendor payment details are processed
      And invoices with incomplete vendor details are flagged for review
