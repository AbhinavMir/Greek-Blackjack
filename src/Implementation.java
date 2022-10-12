import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Implementation {
    public static void main(String[] args) {
        System.out.println("STARTING...");

        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getLogger(Implementation.class.getName());

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
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Please enter the name of player " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.println("Please enter player's buy in");
            int balance = scanner.nextInt();
            Round.Player player = new Round.Player(i + 1, name);
            player.balance = balance;
            players.add(player);
            System.out.println("Player " + (i + 1) + " is " + player.name + " and has ID: " + player.id + " and balance: " + player.balance);
        }

        Round round = new Round(numPlayers, roundNumber, MINIMUM_BET, players);
        round.dealCardsToPlayers(Round.gameState.DEALING, players);

        while (true) {
            logger.info("Round initiated with " + numPlayers + " players.");
            logger.info("Round number: " + roundNumber);
            logger.info("Minimum bet per round: " + MINIMUM_BET);

            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Player " + (i + 1) + " has " + Round.players.get(i).hand.get(0).toString() + " and " + Round.players.get(i).hand.get(1).toString());
            }

            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = Round.players.get(i);
                player.checkBust();
                player.checkWin();
                if (player.bust) {
                    System.out.println("Player " + player.name + " has busted!");
                } else if (player.isWinner) {
                    System.out.println("Player " + player.name + " has won!");
                } else {
                    System.out.println("It is " + player.name + "'s turn.");
                    System.out.println("Current hand: " + player.hand.get(0).toString() + " and " + player.hand.get(1).toString());
                    // print the value of all hands
                    for (int j = 0; j < numPlayers; j++) {
                        System.out.println("Player " + (j + 1) + ": " + Round.players.get(j).calculateHandValue(Round.players.get(j).hand)[0] + " / " + Round.players.get(j).calculateHandValue(Round.players.get(j).hand)[1]);
                    }
                    System.out.println("_________________");
                    String choice;
                    while (true) {
                        System.out.println("Would you like to hit or stand? (h/s)");
                        choice = scanner.next();
                        if (choice.equals("h") && player.calculateHandValue(player.hand)[0] < 21 && player.calculateHandValue(player.hand)[1] < 21) {
                            Round.Card newCard = Round.deck.getRandomCardFaceUp();
                            player.hand.add(newCard);
                            System.out.println("You drew a " + newCard.toString());
                            System.out.println("Your new hand is: " + player.hand.get(0).toString() + " and " + player.hand.get(1).toString() + " and " + player.hand.get(2).toString());
                            System.out.println("Total value of hand: " + player.calculateHandValue(player.hand)[0] + " or " + player.calculateHandValue(player.hand)[1]);
                            if (player.calculateHandValue(player.hand)[0] > 21 && player.calculateHandValue(player.hand)[1] > 21) {
                                System.out.println("You have busted!");
                                player.bust = true;
                                break;
                            } else if (player.calculateHandValue(player.hand)[0] == 21 || player.calculateHandValue(player.hand)[1] == 21) {
                                System.out.println("You have won!");
                                player.isWinner = true;
                                break;
                            }
                        } else if (player.isWinner || player.bust) {
                            break;
                        } else if (choice.equals("s")) {
                            System.out.println("You have chosen to stand.");
                            break;
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }
                }

                roundNumber++;

                if (Round.banker.calculateHandValue(Round.banker.hand)[0] < 17 || Round.banker.calculateHandValue(Round.banker.hand)[1] < 17) {
                    Round.Card newCard = Round.deck.getRandomCard();
                    System.out.println("Banker hand value is " + Round.banker.calculateHandValue(Round.banker.hand)[0] + " or " + Round.banker.calculateHandValue(Round.banker.hand)[1]);
                    Round.banker.hand.add(newCard);
                    System.out.println("Banker drew a " + newCard.toString());
                    Round.banker.printHand();
                    System.out.println("Total value of hand: " + Round.banker.calculateHandValue(Round.banker.hand)[0] + " or " + Round.banker.calculateHandValue(Round.banker.hand)[1]);
                } else {
                    System.out.println("Banker hand value is " + Round.banker.calculateHandValue(Round.banker.hand)[0]);
                }

                System.out.println("Checking for players below Banker's hand value...");
            }
        }
    }
}
