# CS611-A1
## Trianta Ena
---------------------------------------------------------------------------
Abhinav Srivastava
sabhinav@bu.edu
<Student BUID>

## Files
---------------------------------------------------------------------------
- Main.java
- Round.java

## Notes
---------------------------------------------------------------------------
You can quickly run the program by running `./start.sh`.
## How to compile and run
---------------------------------------------------------------------------
1. Navigate to the directory "pa2" after unzipping the files
2. Run the following instructions:
   ```shell
   mkdir bin && javac -d bin *.java
   javac ./bin/Main.java
   ```

## Input/Output Example
---------------------------------------------------------------------------
```shell
STARTING...
Enter number of players:
2
Enter minimum bet:
10
Enter the balance for all users:
100
Please enter the name of player 1:
A
Player 1 is A and has ID: 1 and balance: 100
Please enter the name of player 2:
B
Player 2 is B and has ID: 2 and balance: 100
It is A's turn.
Your hand: [7 of Spades]
Your hand value is: 7
Banker's hand: [Q of Clubs]
Banker's hand value is: 10
Fold or bet? (f/b)
b
How much would you like to bet?
1
You have bet 1 and your balance is now 99
It is B's turn.
Your hand: [3 of Spades]
Your hand value is: 3
Banker's hand: [Q of Clubs]
Banker's hand value is: 10
Fold or bet? (f/b)
b
How much would you like to bet?
3
You have bet 3 and your balance is now 97
Everyone's Hand:
A's hand:
A of Spades, 6 of Hearts,
B's hand:
5 of Diamonds, A of Diamonds,
It is A's turn.
Your hand value: 14 / 24
Every player is dealt two more cards
Would you like to hit or stand? (h/s)
h
You drew a 7 of Clubs
Your hand value is: 21 / 31
You have won!
It is B's turn.
Your hand value: 9 / 19
Every player is dealt two more cards
Would you like to hit or stand? (h/s)
s
Banker has busted!
Players ranked by hand value:
A has a hand value of 21
B has a hand value of 9
Final balances:
A: 101
B: 103
Continue another round? (y/n)

```