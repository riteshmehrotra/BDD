Feature: Open an account for a new customer

Story:
In order to receive payments for my work,
As a prospective banking customer,
I want to open a savings account

Scenario: Open current account for new customer
Given Max is a new customer
When he opens a new CURRENT account
Then he should receive an account number
And the account type should be CURRENT

