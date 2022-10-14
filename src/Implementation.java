import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
// import java.lang;

public class Implementation {
    public static void main(String[] args) {
        System.out.println("STARTING...");
        ArrayList<Round.Player> winners = new ArrayList<Round.Player>();
        ArrayList<Round.Player> busted = new ArrayList<Round.Player>();
        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getLogger(Implementation.class.getName());

        // javac Implemenatation --off 2 1
        if (args.length > 0 && args[0].equals("--verbose")) {
            logger.setLevel(Level.FINE);
        } else if (args.length > 0 && args[0].equals("--no-warnings")) {
            logger.setLevel(Level.SEVERE);
        } else if (args.length > 0 && args[0].equals("--informational")) {
            Logger.getLogger("").setLevel(Level.INFO);
        } else if (args.length > 0 && args[0].equals("--off")) {
            Logger.getLogger("").setLevel(Level.OFF);
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

        if (args.length > 3) {
            balance = Integer.parseInt(args[3]);
        } else {
            System.out.println("Enter the balance for all users: ");
            balance = scanner.nextInt();
        }

        if (args.length > 4) {
            for (int i = 0; i < numPlayers; i++) {
                players.add(new Round.Player(i + 1, args[i + 4], balance));
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
            busted.clear();
            winners.clear();
            logger.info("Round initiated with " + numPlayers + " players.");
            logger.info("Round number: " + roundNumber);
            logger.info("Minimum bet per round: " + MINIMUM_BET);

            Round.banker.hand.add(Round.deck.getRandomCardFaceUp());
            for (int i = 0; i < numPlayers; i++) {
                players.get(i).hand.add(Round.deck.getRandomCardFaceUp());
            }

            String choice, foldOrBet;
            int bet;
            // First round of betting or folding - no H/S
            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = Round.players.get(i);
                System.out.println("\u001b[34mIt is " + player.name + "'s turn. \033[0m");
                System.out.println("Your hand:\033[33m " + player.hand.toString() + "\033[0m");
                if (player.calculateHandValue(player.hand)[0] == player.calculateHandValue(player.hand)[1]) {
                    System.out.println("Your hand value is: \033[33m" + player.calculateHandValue(player.hand)[0] + "\033[0m");
                } else {
                    System.out.println("Your hand value: \033[33m" + player.calculateHandValue(player.hand)[0] + "\033[0m");
                }
                Round.Card.state = Round.CardState.FACE_UP;
                System.out.println("Banker's hand: \033[33m" + Round.banker.hand.toString() + "\033[0m");
                Round.Card.state = Round.CardState.FACE_DOWN;
                if (Round.banker.calculateHandValue(player.hand)[0] == Round.banker.calculateHandValue(Round.banker.hand)[1]) {
                    System.out.println("Your hand value is: \033[33m" + Round.banker.calculateHandValue(Round.banker.hand)[0] + "\033[0m");
                } else {
                    System.out.println("Your hand value: \033[33m" + Round.banker.calculateHandValue(Round.banker.hand)[0] + "\033[0m");
                }
                System.out.println("Fold or bet? (f/b)");
                while (true) {
                    foldOrBet = scanner.next();
                    if (foldOrBet.equals("f") || foldOrBet.equals("b")) {
                        break;
                    } else {
                        System.out.println("Please enter either f or b.");
                    }
                }

                if (foldOrBet.equals("b")) {
                    System.out.println("How much would you like to bet?");
                    bet = scanner.nextInt();
                    players.get(i).balance -= bet;
                    Round.pot += players.get(i).balance;
                    System.out.println("You have bet\033]33m " + bet + "\033]0m and your balance is now " + players.get(i).balance);
                } else {
                    System.out.println("You folded.");
                    player.isFolded = true;
                    break;
                }
            }

            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = Round.players.get(i);
                if (player.isFolded) {
                    continue;
                } else {
                    if (!player.isBusted && !player.isWinner) {
                        System.out.println("\u001b[34mIt is " + player.name + "'s turn. \033[0m");
                        player.hand.add(Round.deck.getRandomCardFaceUp());
                        player.hand.add(Round.deck.getRandomCardFaceUp());
                        System.out.println("Your hand:\033[33m " + player.hand.toString() + "\033[0m");
                        if (player.calculateHandValue(player.hand)[0] == player.calculateHandValue(player.hand)[1]) {
                            System.out.println("Your hand value is: " + player.calculateHandValue(player.hand)[0]);
                        } else {
                            System.out.println("Your hand value is: " + player.calculateHandValue(player.hand)[0] + " / " + player.calculateHandValue(player.hand)[1]);
                        }

                        System.out.println("Every player is dealt a card, and the banker is dealt a card face up.");
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

                roundNumber++;

                if (Round.banker.calculateHandValue(Round.banker.hand)[0] < 27 || Round.banker.calculateHandValue(Round.banker.hand)[1] < 27) {
                    Round.Card newCard = Round.deck.getRandomCardFaceUp();
                    System.out.println("Banker hand value is " + Round.banker.calculateHandValue(Round.banker.hand)[0] + " or " + Round.banker.calculateHandValue(Round.banker.hand)[1]);
                    Round.banker.hand.add(newCard);
                    System.out.println("Banker drew a " + newCard.toString());
                    System.out.println("Total value of hand: " + Round.banker.calculateHandValue(Round.banker.hand)[0] + " or " + Round.banker.calculateHandValue(Round.banker.hand)[1]);

                } else {
                    System.out.println("Banker hand value is " + Round.banker.calculateHandValue(Round.banker.hand)[0]);
                }

                System.out.println("Checking for players below Banker's hand value...");

            }

            for (int i = 0; i < numPlayers; i++) {

            }
        }
    }
}

