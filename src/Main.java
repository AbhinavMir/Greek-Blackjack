import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
// import java.lang;

public class Main {
    public static void main(String[] args) {
        System.out.println("STARTING...");
        ArrayList<Round.Player> winners = new ArrayList<Round.Player>();
        ArrayList<Round.Player> busted = new ArrayList<Round.Player>();
        ArrayList<Round.Player> folded = new ArrayList<Round.Player>();
        ArrayList<Round.Player> playersRankedByHandValue = new ArrayList<Round.Player>();
        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getLogger(Main.class.getName());

        // check if DEBUGGER is enabled in environment
        if (System.getenv("DEBUGGER") != null) {
            if (System.getenv("DEBUGGER").equals("TRUE")) {
                logger.setLevel(Level.ALL);
            } else if (System.getenv("DEBUGGER").equals(("INFORMATIONAL"))) {
                logger.setLevel(Level.INFO);
            } else {
                logger.setLevel(Level.OFF);
            }
        } else {
            // all loggers off
            logger.setLevel(Level.OFF);
        }


        int numPlayers;
        int MINIMUM_BET;

        if (args.length > 1) {
            numPlayers = Integer.parseInt(args[1]);
        } else {
            System.out.println("Enter number of players: ");
            numPlayers = scanner.nextInt();
        }

        if (args.length > 2) {
            MINIMUM_BET = Integer.parseInt(args[2]);
        } else {
            System.out.println("Enter minimum bet: ");
            MINIMUM_BET = scanner.nextInt();
        }

        ArrayList<Round.Player> players = new ArrayList<Round.Player>();

        int roundNumber = 1;
        int balance;

        if (args.length > 2) {
            balance = Integer.parseInt(args[2]);
        } else {
            System.out.println("Enter the balance for all users: ");
            balance = scanner.nextInt();
        }

        if (args.length > 3) {
            for (int i = 0; i < numPlayers; i++) {
                players.add(new Round.Player(i + 1, args[i + 3], balance));
            }
        } else {
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Please enter the name of player " + (i + 1) + ": ");
                String name = scanner.next();
                Round.Player player = new Round.Player(i + 1, name, balance);
                players.add(player);
                System.out.println("Player " + (i + 1) + " is " + player.name + " and has ID: " + player.id + " and balance: " + player.balance);
            }
        }

        Round round = new Round(numPlayers, roundNumber, MINIMUM_BET, players);
        while (true) {
            for (Round.Player player : players) {
                player.hand.clear();
            }
            busted.clear(); // clear busted
            winners.clear(); // clear winners
            logger.info("Round initiated with " + numPlayers + " players.");
            logger.info("Round number: " + roundNumber);
            logger.info("Minimum bet per round: " + MINIMUM_BET);

            // deal cards to players
            Round.banker.hand.add(Round.deck.getRandomCardFaceUp()); // give the banker a face up card
            for (int i = 0; i < numPlayers; i++) {
                players.get(i).hand.add(Round.deck.getRandomCardFaceDown()); // give the players face down cards
            }

            // initialize variables
            String choice, foldOrBet = null;
            int bet, finalBankerHandValue = 0;

            // First round of betting or folding - no H/S
            for (int i = 0; i < players.size(); i++) {
                Round.Player player = Round.players.get(i); // current player
                System.out.println("\u001b[34mIt is " + player.name + "'s turn. \033[0m"); // print player's turn
                System.out.println("Your hand:\033[33m " + player.hand.toString() + "\033[0m"); // print player's hand

                // calculate player's hand value
                if (player.calculateHandValue(player.hand)[0] == player.calculateHandValue(player.hand)[1]) {
                    System.out.println("Your hand value is: \033[33m" + player.calculateHandValue(player.hand)[0] + "\033[0m");
                } else {
                    System.out.println("Your hand value: \033[33m" + player.calculateHandValue(player.hand)[0] + "\033[0m");
                }

                // switch the face up for printing and then switch it back
                Round.Card.state = Round.CardState.FACE_UP;
                System.out.println("Banker's hand: \033[33m" + Round.banker.hand.toString() + "\033[0m");
                Round.Card.state = Round.CardState.FACE_DOWN;

                // print player hand value
                if (Round.banker.calculateHandValue(player.hand)[0] == Round.banker.calculateHandValue(Round.banker.hand)[1]) {
                    System.out.println("Banker's hand value is: \033[33m" + Round.banker.calculateHandValue(Round.banker.hand)[0] + "\033[0m");
                    logger.info("108");
                } else {
                    System.out.println("Banker's hand value is: \033[33m" + Round.banker.calculateHandValue(Round.banker.hand)[0] + "\033[0m");
                    logger.info("111");
                }

                // A loop for correct fold or bet input
                // System.out.println("\033[42mEvery player is one face down card, the Banker is dealt a face up card\033[0m");
                System.out.println("Fold or bet? (f/b)");
                foldOrBet = scanner.next();

                // if player folds, switch bool, else ask bet and subtract
                if (foldOrBet.equals("b")) {
                    System.out.println("How much would you like to bet?");
                    bet = scanner.nextInt();
                    player.bet = bet;
                    players.get(i).balance -= bet;
                    Round.pot += players.get(i).balance;
                    System.out.println("You have bet\033[33m " + bet + "\033[0m and your balance is now " + players.get(i).balance);
                } else {
                    System.out.println("You folded.");
                    folded.add(player);
                    player.isFolded = true;
                }
            }

            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = Round.players.get(i);
                player.hand.add(Round.deck.getRandomCardFaceUp());
                player.hand.add(Round.deck.getRandomCardFaceUp());
            }

            System.out.println("Everyone's Hand: ");
            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = Round.players.get(i);
                System.out.println("\33[42m" + player.name + "'s hand: \33[0m");
                // print all cards that are face up
                StringBuilder sb = new StringBuilder();
                for (int j = 1; j < player.hand.size(); j++) {
                    sb.append(player.hand.get(j).toString() + ", ");
                }
                System.out.println(sb);
            }

            // Second round of betting or folding - with H/S
            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = Round.players.get(i);
                if (player.isFolded) {
                    continue;
                } else {
                    if (!player.isBusted && !player.isWinner) {
                        System.out.println("\u001b[33mIt is " + player.name + "'s turn. \033[0m");

                        if (player.calculateHandValue(player.hand)[0] == player.calculateHandValue(player.hand)[1]) {
                            System.out.println("Your hand value is: \033[33m" + player.calculateHandValue(player.hand)[0] + "\033[0m");
                        } else {
                            System.out.println("Your hand value: \033[33m" + player.calculateHandValue(player.hand)[0] + " / " + player.calculateHandValue(player.hand)[1] + "\033[0m");
                        }

                        System.out.println("\33[36mEvery player is dealt two more cards\33[0m");

                        // Now let's go into H/S mode
                        while (true) {
                            System.out.println("Would you like to hit or stand? (h/s)");
                            while (true) {
                                choice = scanner.next();
                                if (choice.equals("h") || choice.equals("s")) {
                                    break;
                                } else {
                                    System.out.println("Please enter h or s.");
                                }
                            }


                            if (choice.equals("h")) {
                                Round.Card card = Round.deck.getRandomCardFaceDown();
                                Round.Card.state = Round.CardState.FACE_DOWN;
                                System.out.println("You drew a \033[32m" + card.toString() + "\033[0m");
                                player.hand.add(card);

                                if (player.calculateHandValue(player.hand)[0] == player.calculateHandValue(player.hand)[1]) {
                                    System.out.println("Your hand value is: " + player.calculateHandValue(player.hand)[0]);
                                } else {
                                    System.out.println("Your hand value is: " + player.calculateHandValue(player.hand)[0] + " / " + player.calculateHandValue(player.hand)[1]);
                                }
                                if (player.calculateHandValue(player.hand)[0] > 31 && player.calculateHandValue(player.hand)[1] > 31) {
                                    System.out.println("\033[33m");
                                    System.out.println("You have busted!");
                                    System.out.println("\033[0m");
                                    player.isBusted = true;
                                    busted.add(player);
                                    break;
                                } else if (player.calculateHandValue(player.hand)[0] == 31 || player.calculateHandValue(player.hand)[1] == 31) {
                                    System.out.println("\033[33m" + "You have won!" + "\033[0m");
                                    player.isWinner = true;
                                    winners.add(player);
                                    break;
                                }
                            } else if (choice.equals("s")) {
                                break;
                            } else {
                                System.out.println("Please enter a valid choice.");
                            }

                        }
                    }
                }
            }


            // flip all cards of banker face up
            for (Round.Card card : Round.banker.hand) {
                Round.Card.state = Round.CardState.FACE_UP;
            }


            while (Round.banker.calculateHandValue(Round.banker.hand)[0] >= 27 || Round.banker.calculateHandValue(Round.banker.hand)[1] >= 27) {
                Round.banker.hand.add(Round.deck.getRandomCardFaceUp());
            }

            // check which handValue for banker is between 27 and 31
            if (Round.banker.calculateHandValue(Round.banker.hand)[0] >= 27 && Round.banker.calculateHandValue(Round.banker.hand)[0] <= 31) {
                finalBankerHandValue = Round.banker.calculateHandValue(Round.banker.hand)[0];
            } else if (Round.banker.calculateHandValue(Round.banker.hand)[1] >= 27 && Round.banker.calculateHandValue(Round.banker.hand)[1] <= 31) {
                finalBankerHandValue = Round.banker.calculateHandValue(Round.banker.hand)[1];
            } else {
                System.out.println("Banker has busted!");
            }

            for (int i = 0; i < numPlayers; i++) {
                // If player not busted, add them to playerRankedByHandValues list
                if (!Round.players.get(i).isBusted) {
                    playersRankedByHandValue.add(Round.players.get(i));
                }
            }

            // sort playersRankedByHandValue by handValue
            Collections.sort(playersRankedByHandValue, new Comparator<Round.Player>() {
                @Override
                public int compare(Round.Player o1, Round.Player o2) {
                    return o2.calculateHandValue(o2.hand)[0] - o1.calculateHandValue(o1.hand)[0];
                }
            });

            // check for players above finalBankerHandValue and below 31, remove everyone else
            for (int i = 0; i < playersRankedByHandValue.size(); i++) {
                if (playersRankedByHandValue.get(i).calculateHandValue(playersRankedByHandValue.get(i).hand)[0] < finalBankerHandValue || playersRankedByHandValue.get(i).calculateHandValue(playersRankedByHandValue.get(i).hand)[0] > 31) {
                    playersRankedByHandValue.remove(i);
                }
            }

            // to all the players in playersRankedByHandValue, reward them 2*their bet
            for (Round.Player player : playersRankedByHandValue) {
                player.balance += player.bet * 2;
            }

            // print playersRankedByHandValue
            System.out.println("\33[44mPlayers ranked by hand value:\33[0m");
            for (int i = 0; i < playersRankedByHandValue.size(); i++) {
                System.out.println(playersRankedByHandValue.get(i).name + " has a hand value of " + playersRankedByHandValue.get(i).calculateHandValue(playersRankedByHandValue.get(i).hand)[0]);
            }

            // print everyone's final balances
            System.out.println("\33[43mFinal balances:\33[0m");
            for (Round.Player player : Round.players) {
                System.out.println(player.name + ": " + player.balance);
            }

            for (int i = 0; i < folded.size(); i++) {
                System.out.println(folded.get(i).name + " folded.");
            }

            System.out.println("Continue another round? (y/n)");
            while (true) {
                choice = scanner.next();
                if (choice.equals("y") || choice.equals("n")) {
                    break;
                } else {
                    System.out.println("Please enter y or n.");
                }
            }

            if (choice.equals("n")) {
                break;
            }


            // reset all players
            for (Round.Player player : Round.players) {
                player.reset();
            }

            roundNumber++;
        }
    }
}

