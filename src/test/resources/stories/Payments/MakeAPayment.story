Feature: Make a payment from account

Story:
In order to make payments to contractors,
As a banking customer,
I want to initiate a funds transfer


Scenario: Transfer fund between accounts
Given Below customer accounts
| AccountHolder |   AccountType |   AccountNumber | Balance |
|   Martha      |   SAVINGS     |   103444       | 500     |
|   Steve        |   SAVINGS     |   101110       | 200     |
When Martha initiates a funds transfer of SGD 300 to Steve account 101110
Then Martha new balance for account 103444 should be SGD 200
And Steve new balance for account 101110 should be SGD 500


Scenario: Transfer fund between accounts with insufficient balance
Given Below customer accounts
| AccountHolder |   AccountType |   AccountNumber | Balance |
|   Martha      |   SAVINGS     |   103444       | 500     |
|   Steve        |   SAVINGS     |   101110       | 200     |
When Martha initiates a funds transfer of SGD 1000 to Steve account 101110
Then Martha receives error: You do not have sufficient balance for this transaction
And Martha new balance for account 103444 should be SGD 500
And Steve new balance for account 101110 should be SGD 200


Scenario: Transfer funds between accounts with examples
Given Below customer accounts
| AccountHolder |   AccountType |   AccountNumber | Balance |
|   Martha      |   SAVINGS     |   103444       | 500     |
|   Steve        |   SAVINGS     |   101110       | 200     |
When Martha initiates a funds transfer of SGD <amount> to <payee> account <payeeAccount>
Then <payer> new balance for account <accountNumber> should be SGD <payerBalance>
And <payee> new balance for account <payeeAccount> should be SGD <payeeBalance>

Examples:

|   payer   |   accountNumber   | payee     | payeeAccount  |amount |payerBalance| payeeBalance    |
|   Martha  |   103444          | Steve     |  101110       |100    |400         | 300             |
|   Martha  |   103444          | Steve     |  101110       |1000   |400         | 300             |
|   Martha  |   103444          | Steve     |  101110       |300    |100         | 600             |

