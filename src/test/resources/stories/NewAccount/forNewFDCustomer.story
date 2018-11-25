Feature: Open an fixed deposit account for a new customer

Narrative:
In order to earn interest on my savings,
As a prospective banking customer,
I want to open a fixed deposit account

Scenario: Open fixed deposit account for new customer
Given Rob is a new customer
When he opens a new FIXED account
Then he should receive an account number
And the account type should be FIXED
