Narrative:
In order to deal in multiple currencies,
As a prospective banking customer,
I want to open a multicurrency account

Scenario: Open multicurrency account for new customer
Given Max is a new customer
When he opens a new MULTICURRENCY account
Then he should receive an account number
And the account type should be MULTICURRENCY
And available currencies should be
| Currency |
|   SGD    |
|   INR    |
|   JPY    |
|   CNY    |
