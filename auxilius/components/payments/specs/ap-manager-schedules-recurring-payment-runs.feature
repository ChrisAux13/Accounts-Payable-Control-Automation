Feature: AP manager schedules recurring payment runs

  Rule: Recurring payment runs must have valid frequency settings

    Example: Schedule daily payment run
      Given an AP manager has access to schedule payment runs
      When they create a recurring payment run with daily frequency
      Then the system should save the recurring schedule
      And confirm the first payment run is scheduled for the next business day

    Example: Invalid frequency settings
      Given an AP manager has access to schedule payment runs
      When they attempt to create a recurring payment run with invalid frequency
      Then the system should reject the schedule
      And provide an error message explaining valid frequency options

  Rule: Recurring payment runs must have defined payment criteria

    Example: Schedule recurring run with specific vendor groups
      Given an AP manager has access to schedule payment runs
      When they create a recurring payment run for specific vendor groups
      And set payment criteria for approved invoices only
      Then the system should save the schedule with the specified criteria

    Example: Missing payment criteria
      Given an AP manager has access to schedule payment runs
      When they attempt to create a recurring payment run without payment criteria
      Then the system should reject the schedule
      And indicate that payment criteria is required

  Rule: Recurring payment runs can be modified or cancelled

    Example: Modify existing schedule
      Given a recurring payment run exists in the system
      When the AP manager updates the schedule frequency
      Then the system should apply the changes to future payment runs
      And maintain the history of previous runs

    Example: Cancel recurring schedule
      Given a recurring payment run exists in the system
      When the AP manager cancels the recurring schedule
      Then the system should prevent future payment runs from executing
      And maintain the history of completed runs

  Rule: Recurring payment runs must respect business calendar

    Example: Schedule respects holidays
      Given an AP manager schedules a daily recurring payment run
      When a holiday occurs on a scheduled run date
      Then the system should automatically adjust the run to the next business day

    Example: Schedule respects weekend processing rules
      Given an AP manager schedules a weekly recurring payment run
      When the scheduled date falls on a weekend
      Then the system should adjust the run according to weekend processing rules
