/*
This code is a direct fork of the code provided in A1, with tweaks for readability.
Cell has been replaced with Card - and a few member states have been modified.
Board has been replaced with Deck - and a few member states have been modified.
Player and Banker have been separated into their own classes - for ease of use.

 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Round {

    static ArrayList<Player> players;
    static Banker banker;
    static Deck deck;
    static int pot;
    private final int betPerRound; // Strictly used to calculate bet on the pot per round
    Logger logger = Logger.getLogger(Round.class.getName());
    int numberOfPlayers;
    int roundNumber;
    private int currentPlayer = 0;

    public Round(int numberOfPlayers, int roundNumber, int betPerRound, ArrayList<Player> players) {
        this.roundNumber = roundNumber;
        this.numberOfPlayers = numberOfPlayers;
        this.betPerRound = betPerRound;
        banker = new Banker("Banker", betPerRound * 3);
        deck = new Deck(52 * 2);
        Round.players = players;
        logger.info("Round initiated with " + numberOfPlayers + " players.");
        logger.info("Round number: " + roundNumber);
        logger.info("Minimum bet per round: " + betPerRound);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    private void nextPlayer() {
        if (currentPlayer < numberOfPlayers) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
    }

    private void addPlayers(ArrayList<Player> players) {
        Round.players = players;
        logger.info("Players added to round.");
    }

    public void removePlayer(Player player) {
        players.remove(player);
        logger.info("Player: " + player.name + " was removed from round.");
    }

    private void nextRound() {
        this.roundNumber++;
    }

    enum gameState {
        BETTING, DEALING, HITTING, STANDING, RAISING, ENDING
    }

    enum CardState {
        FACE_DOWN, FACE_UP, INACTIVE
    }

    static class Player {
        Logger logger = Logger.getLogger(Player.class.getName());
        int id;
        String name;
        int score;
        boolean isFolder;
        boolean isBusted;
        int balance;
        boolean isWinner;
        boolean isFolded;
        int bet;
        ArrayList<Card> hand;
        int[] handValue = new int[2];

        public Player(int id, String name, int balance) {
            this.id = id;
            this.name = name;
            this.score = 0;
            this.balance = balance;
            this.hand = new ArrayList<Card>();
            this.handValue = new int[]{0, 0};
            this.isBusted = false;
            this.isWinner = false;
            this.isFolded = false;
            logger.info("Player: " + name + " created with id: " + id);
        }

        public void reset() {
            this.isBusted = false;
            this.isWinner = false;
            this.isFolded = false;
            this.bet = 0;
            this.hand = new ArrayList<>();
            this.handValue = new int[2];
        }

        public void transferBalance(int amount, Player player) {
            this.balance -= amount;
            player.balance += amount;
            logger.info("Transferred " + amount + " from " + this.name + " to " + player.name);
        }

        public void transferToBanker(int amount) {
            this.balance -= amount;
            banker.balance += amount;
            logger.info("Transferred " + amount + " from " + this.name + " to " + banker.name);
        }

        public void flipHand() {
            for (Card card : hand) {
                card.flipCard();
            }
        }

        public void allCardsFaceUp(ArrayList<Card> handThis) {
            for (Card card : handThis) {
                Card.state = CardState.FACE_UP;
            }
        }

        public void allCardsFaceUp() {
            for (Card card : hand) {
                card.flipCard();
            }
        }

        public int[] calculateHandValue(ArrayList<Card> hand) {
            int[] handValue = new int[2];
            for (Card card : hand) {
                Card.state = CardState.FACE_UP;
                // card.value is a String, so convert it if it is a number, if ace then 1 and 11, if K, Q, J then 10
                if (card.getValue().equals("A")) {
                    handValue[0] += 1;
                    handValue[1] += 11;
                } else if (card.getValue().equals("K") || card.getValue().equals("Q") || card.getValue().equals("J")) {
                    handValue[0] += 10;
                    handValue[1] += 10;
                } else if (card.getValue().equals("[HIDDEN]") || card.getValue().equals("[HIDDEN]")) {
                    handValue[0] += 0;
                    handValue[1] += 0;
                } else {
                    handValue[0] += Integer.parseInt(card.getValue());
                    handValue[1] += Integer.parseInt(card.getValue());
                }
                Card.state = CardState.FACE_DOWN;
            }

            return handValue;
        }

        public void updateHandValue() {
            this.handValue = calculateHandValue(this.hand);
        }
    }

    static class Banker {
        int[] handValue = new int[2];
        String name;
        int score;
        ArrayList<Card> hand;
        int balance;
        boolean isWinner = false;

        public Banker(String name, int balance) {
            this.name = name;
            this.score = 0;
            this.balance = balance;
            this.hand = new ArrayList<Card>();
        }

        public void updateHandValue() {
            this.handValue = calculateHandValue(this.hand);
        }

        public void updateBalance(int amount) {
            this.balance += amount;
        }

        public int[] calculateHandValue(ArrayList<Card> hand) {
            // calculate value if face up
            // flip the card
            for (Card card : hand) {
                if (Card.state == CardState.FACE_UP) {
                    Card.state = CardState.FACE_DOWN;
                } else {
                    Card.state = CardState.FACE_UP;
                }
            }

            int[] handValue = new int[2];
            for (Card card : hand) {
                // card.value is a String, so convert it if it is a number, if ace then 1 and 11, if K, Q, J then 10
                if (card.getValue().equals("A")) {
                    handValue[0] += 1;
                    handValue[1] += 11;
                } else if (card.getValue().equals("K") || card.getValue().equals("Q") || card.getValue().equals("J")) {
                    handValue[0] += 10;
                    handValue[1] += 10;
                } else if (card.getValue().equals("[HIDDEN]")) {
                    handValue[0] += 0;
                    handValue[1] += 0;
                } else {
                    handValue[0] += Integer.parseInt(card.getValue());
                    handValue[1] += Integer.parseInt(card.getValue());
                }
            }

            for (Card card : hand) {
                if (Card.state == CardState.FACE_UP) {
                    Card.state = CardState.FACE_DOWN;
                } else {
                    Card.state = CardState.FACE_UP;
                }
            }
            this.handValue = handValue;
            return handValue;
        }

        public void printHand() {
            for (Card card : hand) {
                if (card.getState() == CardState.FACE_UP) {
                    System.out.println(card);
                } else {
                    System.out.println("[HIDDEN]");
                }
            }
        }
    }

    // create a card class
    public static class Card {
        public static final int MAX_NUMBER_OF_PLAYERS = 8;
        public static final int MIN_NUMBER_OF_PLAYERS = 2;
        public static CardState state;
        private final String suit;
        private final String value;
        Player owner;

        public Card(String suit, String value) {
            this.suit = suit;
            this.value = value;
            state = CardState.FACE_DOWN;
        }

        public void flipCard() {
            if (state == CardState.FACE_UP) {
                state = CardState.FACE_DOWN;
            } else {
                state = CardState.FACE_UP;
            }
        }

        public String printCard(Card card) {
            // check if card is face down
            if (state == CardState.FACE_DOWN) {
                return "[HIDDEN]";
            } else {
                return card.suit + " " + card.value;
            }
        }

        public String getSuit() {
            if (state == CardState.FACE_DOWN) {
                return "[HIDDEN]";
            } else {
                return suit;
            }
        }

        public String getSuitNormal() {
            return suit;
        }

        public String getValue() {
            if (state == CardState.FACE_DOWN) {
                return "[HIDDEN]";
            } else {
                return value;
            }
        }

        public String getValueNormal() {
            return value;
        }

        public CardState getState() {
            return state;
        }

        public String toString() {
            return String.format("%s of %s", getValueNormal(), getSuitNormal());
        }
    }

    public class Deck {
        private final ArrayList<Card> cards;
        private int size;

        public Deck(int size) {
            cards = createDeck(size);
            size = 0;
        }

        public Card getRandomCardFaceUp() {
            int randomIndex = (int) (Math.random() * cards.size());
            Card randomCard = cards.get(randomIndex);
            Card.state = CardState.FACE_UP;
            cards.remove(randomIndex);
            return randomCard;
        }

        public Card getRandomCardFaceDown() {
            int randomIndex = (int) (Math.random() * cards.size());
            Card randomCard = cards.get(randomIndex);
            Card.state = CardState.FACE_DOWN;
            return randomCard;
        }

        private void addCard(Card card) {
            cards.add(card);
            size++;
        }

        public void removeCard(Card card) {
            cards.remove(card);
            size--;
        }

        private ArrayList<Card> createDeck(int size) {
            ArrayList<Card> cards = new ArrayList<Card>();
            String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
            String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
            for (int i = 0; i < size; i++) {
                for (String suit : suits) {
                    for (String value : values) {
                        cards.add(new Card(suit, value));
                    }
                }
            }

            Collections.shuffle(cards);
            return cards;
        }

        /*
        @NOTE: This is a safer, casino-friendly method of creating a blackjack deck.
         */

        public int getSize() {
            return size;
        }

        public void shuffle() {
            Collections.shuffle(cards);
        }

        public void printDeck() {
            for (int i = 0; i < size; i++) {
                System.out.println(cards.get(i));
            }
        }

        public int size() {
            return size;
        }
    }

    class Team {
        int numPlayers;
        Player[] players;
    }

}