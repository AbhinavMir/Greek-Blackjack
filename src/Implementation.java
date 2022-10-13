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
                System.out.println(players.get(i).name);
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
            System.out.println("\033[33m");
            logger.info("Round initiated with " + numPlayers + " players.");
            logger.info("Round number: " + roundNumber);
            logger.info("Minimum bet per round: " + MINIMUM_BET);

            for (int i = 0; i < numPlayers; i++) {
                System.out.println(players.get(i).name);
            }
            System.out.println("\033[0m");

            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = Round.players.get(i);
                if (!player.isBusted && !player.isWinner) {
                    System.out.println("It is " + player.name + "'s turn.");
                    System.out.println("\033[30m" + "_______________" + "\033[0m\n");
                    for (int j = 0; j < numPlayers; j++) {
                        if (j != i) {
                            Round.Player otherPlayer = Round.players.get(j);
                            System.out.println(otherPlayer.hand.toString());
                            System.out.println(otherPlayer.name + "'s Value: " + otherPlayer.calculateHandValue(otherPlayer.hand)[0] + " / " + otherPlayer.calculateHandValue(otherPlayer.hand)[1]);
                        }
                    }

                    System.out.println("Your hand: " + player.calculateHandValue(player.hand)[0] + " / " + player.calculateHandValue(player.hand)[1]);
                    System.out.println("_________________\n");
                    String choice;
                    while (true) {
                        if (winners.size() + busted.size() == numPlayers) {
                            System.out.println("All players have busted or won. Game over.");
                            // print all the winners from the winners hashMap
                            for (i = 0; i < winners.size(); i++) {
                                System.out.println(winners.get(i).name + " won");
                            }
                            for (i = 0; i < busted.size(); i++) {
                                System.out.println(busted.get(i).name + " busted");
                            }
                            break;
                        } else {
                            // print all winner and bust status of all players
                            for (Round.Player playerVar : players) {
                                System.out.println(playerVar.isBusted + " and " + playerVar.isWinner);
                            }
                            System.out.println("Would you like to hit or stand? (h/s)");
                            choice = scanner.next();
                            if (choice.equals("h")) {
                                Round.Card card = Round.deck.getRandomCardFaceUp();
                                System.out.println("You drew a " + card.toString());
                                player.hand.add(card);
                                System.out.println("Your hand: " + player.calculateHandValue(player.hand)[0] + " / " + player.calculateHandValue(player.hand)[1]);
                                if (player.calculateHandValue(player.hand)[0] > 21 && player.calculateHandValue(player.hand)[1] > 21) {
                                    System.out.println("\033[32m");
                                    System.out.println("You have busted!");
                                    System.out.println("\033[0m");
                                    player.isBusted = true;
                                    break;
                                } else if (player.calculateHandValue(player.hand)[0] == 21 || player.calculateHandValue(player.hand)[1] == 21) {
                                    System.out.println("You have won!");
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

                if (Round.banker.calculateHandValue(Round.banker.hand)[0] < 17 || Round.banker.calculateHandValue(Round.banker.hand)[1] < 17) {
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
        }
    }
}

