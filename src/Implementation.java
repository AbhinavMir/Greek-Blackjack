import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Implementation {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getLogger(Implementation.class.getName());

        if (args.length > 0 && args[0].equals("--verbose")) {
            logger.setLevel(Level.FINE);
        } else if (args.length > 0 && args[0].equals("--no-warnings")) {
            logger.setLevel(Level.SEVERE);
        } else if (args.length > 0 && args[0].equals("--informational")) {
            logger.setLevel(Level.INFO);
        } else {
            logger.setLevel(Level.OFF);
        }

        // write a function to read args and add them to numplayers etc in order else take input
        // from user
        int numPlayers = 0;
        int MINIMUM_BET = 0;
        int MAXIMUM_BET = 0;

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

        if (args.length > 3) {
            MAXIMUM_BET = Integer.parseInt(args[3]);
        } else {
            System.out.println("Enter maximum bet: ");
            MAXIMUM_BET = scanner.nextInt();
        }

        ArrayList<Round.Player> players = new ArrayList<Round.Player>();

        int roundNumber = 1;
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Please enter the name of player " + (i + 1) + ": ");
            String name = scanner.next();
            Round.Player player = new Round.Player(i + 1, name);
            System.out.println("Player " + (i + 1) + " is " + player.name + " and has ID" + player.id);
            players.add(player);
        }

        Round round = new Round(numPlayers, roundNumber, MINIMUM_BET, players);
        round.dealCardsToPlayers(Round.gameState.DEALING, players);
        // sout all details about the round
        System.out.println("player are: " + Round.players.get(0).name + " and " + Round.players.get(1).name);
        System.out.println("Banker is " + Round.banker.name);

        while (true) {
            logger.info("Round initiated with " + numPlayers + " players.");
            logger.info("Round number: " + roundNumber);
            logger.info("Minimum bet per round: " + MINIMUM_BET);
            // print everyone's cards
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Player " + (i + 1) + " has " + Round.players.get(i).hand.get(0).toString() + " and " + Round.players.get(i).hand.get(1).toString());
            }

            // print banker cards
            System.out.println("Banker has " + Round.banker.hand.get(0).toString() + " and " + Round.banker.hand.get(1).toString());

            for (int i = 0; i < numPlayers; i++) {
                Round.Player player = round.getCurrentPlayer();
                System.out.println("It is " + player.name + "'s turn.");

            }

            roundNumber++;

            System.out.println("Continue? (y/n)");
        }
    }
}
