import java.util.logging.Logger;
import java.util.Scanner;

public class Implementation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getLogger(Implementation.class.getName());
        System.out.println("Welcome to the game of 31!");
        System.out.println("Please enter the number of players: ");
        int numPlayers = scanner.nextInt();
        System.out.println("Please enter the minimum bet: ");
        int MINIMUM_BET = scanner.nextInt();
        System.out.println("Please enter the maximum bet: ");
        int MAXIMUM_BET = scanner.nextInt();
        int roundNumber = 1;
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Please enter the name of player " + (i + 1) + ": ");
            String name = scanner.next();
            Round.Player player = new Round.Player(i, name);
            System.out.println("Player " + (i + 1) + " is " + player.name + " and has ID" + player.id);
        }

        /*
        Example input to test:
3
10
100
Abhinav
Puja
         */



        Round round = new Round(numPlayers, roundNumber, MINIMUM_BET);

        while (true) {
            logger.info("Round initiated with " + numPlayers + " players.");
            logger.info("Round number: " + roundNumber);
            logger.info("Minimum bet per round: " + MINIMUM_BET);
            // print everyone's cards
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Player " + (i + 1) + " has " + round.players.get(i).hand.get(0).toString() + " and " + round.players.get(i).hand.get(1).toString());
            }

            // print banker cards
            System.out.println("Banker has " + round.banker.hand.get(0).toString() + " and " + round.banker.hand.get(1).toString());

            for(int i = 0; i < numPlayers; i++) {
                Round.Player player = round.getCurrentPlayer();
                System.out.println("It is " + player.name + "'s turn.");

            }

            roundNumber++;

            System.out.println("Continue? (y/n)");
        }
    }
}
