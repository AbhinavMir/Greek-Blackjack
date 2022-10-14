import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
// import java.lang;

public class Implementing {

    public static void main(String[] args) {
//        System.out.println("STARTING...");
//        ArrayList<Round.Player> winners = new ArrayList<Round.Player>();
//        ArrayList<Round.Player> busted = new ArrayList<Round.Player>();
//        Scanner scanner = new Scanner(System.in);
//        Logger logger = Logger.getLogger(Implementation.class.getName());
//
//        if (args.length > 0 && args[0].equals("--verbose")) {
//            logger.setLevel(Level.FINE);
//        } else if (args.length > 0 && args[0].equals("--no-warnings")) {
//            logger.setLevel(Level.SEVERE);
//        } else if (args.length > 0 && args[0].equals("--informational")) {
//            Logger.getLogger("").setLevel(Level.INFO);
//        } else if (args.length > 0 && args[0].equals("--off")) {
//            Logger.getLogger("").setLevel(Level.OFF);
//        }
//
//        int numPlayers;
//        int MINIMUM_BET;
//
//        if (args.length > 1) {
//            numPlayers = Integer.parseInt(args[1]);
//        } else {
//            System.out.println("Enter number of players: ");
//            numPlayers = scanner.nextInt();
//        }
//
//        if (args.length > 2) {
//            MINIMUM_BET = Integer.parseInt(args[2]);
//        } else {
//            System.out.println("Enter minimum bet: ");
//            MINIMUM_BET = scanner.nextInt();
//        }
//
//        ArrayList<Round.Player> players = new ArrayList<Round.Player>();
//
//        System.out.println("Enter the balance for all users: ");
//        int balance = scanner.nextInt();
//        while (balance < MINIMUM_BET) {
//            System.out.println("Balance must be greater than minimum bet");
//            balance = scanner.nextInt();
//        }
//
//        int roundNumber = 1;
//        for (int i = 0; i < numPlayers; i++) {
//            System.out.println("Please enter the name of player " + (i + 1) + ": ");
//            String name = scanner.next();
//            System.out.println("Please enter player's buy in");
//            Round.Player player = new Round.Player(i + 1, name, scanner.nextInt());
//            player.balance = balance;
//            players.add(player);
//            System.out.println("Player " + (i + 1) + " is " + player.name + " and has ID: " + player.id + " and balance: " + player.balance);
//        }
//
//        Round round = new Round(numPlayers, roundNumber, MINIMUM_BET, players);
//        ArrayList<Round.Player> tempPlayers = Round.players;
//        for (Round.Player player : players) {
//            player.updateHandValue();
//        }
//        int balancePlayer;
//        while (players.size() > 0) {
//            round.dealCardsToPlayersFirstRound(Round.gameState.DEALING, players);
//            winners.clear();
//            busted.clear();
//
//            logger.info("Round initiated with " + numPlayers + " players.");
//            logger.info("Round number: " + roundNumber);
//            logger.info("Minimum bet per round: " + MINIMUM_BET);
//
//            System.out.println("\033[33m");
//            for (int i = 0; i < numPlayers - 1; i++) {
//                System.out.println(players.get(i).name + " has " + Round.players.get(i).hand.get(0).toString() + " and " + Round.players.get(i).hand.get(1).toString());
//                Round.players.get(i).updateHandValue();
//                System.out.println(players.get(i).name + " has a hand value of " + Round.players.get(i).handValue[0] + " / " + Round.players.get(i).handValue[1]);
//            }
//            System.out.println("\033[0m");
//
//            for (int i = 0; i < numPlayers; i++) {
//                Round.Player currentPlayer = Round.players.get(i);
//                balancePlayer = currentPlayer.balance;
//                if (balancePlayer < MINIMUM_BET) {
//                    System.out.println(currentPlayer.name + "'s balance too low");
//                    busted.add(currentPlayer);
//                    break;
//                } else {
//                    Round.pot += MINIMUM_BET;
//                    currentPlayer.balance -= MINIMUM_BET;
//                    System.out.println(currentPlayer.name);
//                    System.out.println("Your cards: " + currentPlayer.hand.toString());
//                    System.out.println("Hit or Stand? (h/s)");
//                    while (true) {
//                        String input = scanner.next();
//                        if (input.equals("h")) {
//                            Round.Card card = Round.deck.getRandomCardFaceUp();
//                            currentPlayer.hand.add(card);
//                            currentPlayer.updateHandValue();
//                            System.out.println("Your cards: " + currentPlayer.hand.toString());
//                            System.out.println("Value: " + currentPlayer.handValue[0] + " / " + currentPlayer.handValue[1]);
//                            if (currentPlayer.handValue[0] > 21 && currentPlayer.handValue[1] > 21) {
//                                System.out.println("BUSTED");
//                                busted.add(currentPlayer);
//                                players.remove(currentPlayer);
//                                break;
//                            }
//                        } else if (input.equals("s")) {
//                            break;
//                        } else {
//                            System.out.println("Invalid input");
//                        }
//
//
//                        // print everyone's balance
//                        System.out.println("\033[33m");
//                        for (int j = 0; j < numPlayers; j++) {
//                            System.out.println(Round.players.get(j).name + " has " + Round.players.get(j).balance);
//                        }
//                        System.out.println("\nBanker has " + Round.banker.balance);
//                        System.out.println("\033[0m");
//
//                    }
//                }
//            }
//
//        }
//    }
//
//    public boolean isPlayerInTempArray(Round.Player player, ArrayList<Round.Player> tempArray) {
//        for (Round.Player p : tempArray) {
//            if (p.id == player.id) {
//                return true;
//            }
//        }
//        return false;
    }
}

