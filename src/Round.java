/*
This code is a direct fork of the code provided in A1, with tweaks for readability.
Cell has been replaced with Card - and a few member states have been modified.
Board has been replaced with Deck - and a few member states have been modified.
Player and Banker have been separated into their own classes - for ease of use.

 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class Round {

    private final int betPerRound; // Strictly used to calculate bet on the pot per round
    Logger logger = Logger.getLogger(Round.class.getName());
    int numberOfPlayers;
    int roundNumber;
    ArrayList<Player> players;
    Deck deck;
    Banker banker;
    private int currentPlayer = 0;
    private int pot; // The total amount of money in the pot

    public Round(int numberOfPlayers, int roundNumber, int betPerRound) {
        this.roundNumber = roundNumber;
        this.numberOfPlayers = numberOfPlayers;
        this.betPerRound = betPerRound;
        this.banker = new Banker();
        this.deck = new Deck(289357, 312); // 6 decks of cards

        dealCardsToPlayers(gameState.DEALING);

        logger.info("Round initiated with " + numberOfPlayers + " players.");
        logger.info("Round number: " + roundNumber);
        logger.info("Minimum bet per round: " + betPerRound);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    private void dealCardsToPlayers(gameState state) {
        if (state == gameState.DEALING) {
            logger.info("Dealing cards to players.");
            Card bankerFaceDownCard = deck.getRandomCard();
            logger.info("Banker's face down card is " + bankerFaceDownCard.toString());
            Card bankerFaceUpCard = deck.getRandomCardFaceUp();
            logger.info("Banker's face up card is " + bankerFaceUpCard.toString());
            banker.hand.add(bankerFaceDownCard);
            logger.info("Added Card 1 to banker's hand.");
            banker.hand.add(bankerFaceUpCard);
            logger.info("Added Card 2 to banker's hand.");

            for (int i = 0; i < numberOfPlayers; i++) {
                // use get random card
                Card card = deck.getRandomCard();
                players.get(i).hand.add(card);
            }
        }

        state = gameState.HITTING;
        logger.info("Cards dealt to players and banker.");
    }

    private void nextPlayer() {
        if (currentPlayer < numberOfPlayers) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
    }

    private void addPlayers(ArrayList<Player> players) {
        this.players = players;
        logger.info("Players added to round.");
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
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

    interface ruleSet {
    }

    static class Player {
        Logger logger = Logger.getLogger(Player.class.getName());
        int id;
        String name;
        int score;
        boolean isTurn;
        int balance;
        boolean isWinner = false;
        ArrayList<Card> hand;
        int handValue;

        public Player(int id, String name) {
            this.id = id;
            this.name = name;
            this.score = 0;
            this.isTurn = false;
            this.balance = 0;
            this.hand = new ArrayList<Card>();
            this.handValue = 0;
            logger.info("Player: " + name + " created with id: " + id);
        }

        public void fold() {
            this.isTurn = false;
            logger.info("Player: " + name + " folded.");
        }

        public void calculateHandValue(Boolean A) {
            int handValue = 0;
            for (Card card : this.hand) {
                handValue += card.getValue();
            }
            if (A) {
                handValue += 10;
            }
            this.handValue = handValue;
            logger.info("Player: " + name + " hand value: " + handValue);
        }

        public void calculateHandValue() {
            int handValue = 0;
            for (Card card : this.hand) {
                handValue += card.getValue();
            }
            this.handValue = handValue;
        }

        public void checkBust() {
            if (this.handValue > 31) {
                this.fold();
            }
        }
    }

    static class Banker {
        String name;
        int score;
        ArrayList<Card> hand;
        boolean isTurn;
        int[] balance;
        boolean isWinner = false;

        public Banker(String name) {
            this.name = name;
            this.score = 0;
            this.isTurn = false;
        }

        public Banker() {
            new Banker("Banker");
        }
    }

    // create a card class
    public class Card {
        public static final int MAX_NUMBER_OF_PLAYERS = 8;
        public static final int MIN_NUMBER_OF_PLAYERS = 2;

        private final String suit;
        private final int value;
        public CardState state;
        Player owner;

        public Card(String suit, int value) {
            this.suit = suit;
            this.value = value;
            this.state = CardState.FACE_DOWN;
        }

        public String getSuit() {
            return suit;
        }

        public int getValue() {
            return value;
        }

        public CardState getState() {
            return state;
        }

        public String toString() {
            return String.format("%s of %s", value, suit);
        }

        public Card getCard() {
            return this;
        }
    }

    public class Deck {
        private final ArrayList<Card> cards;
        private int size;

        public Deck(int RANDOM_SEED, int size) {
            cards = createDeck(RANDOM_SEED, size);
            size = 0;
        }

        public Card getRandomCard() {
            int randomIndex = (int) (Math.random() * cards.size());
            Card randomCard = cards.get(randomIndex);
            cards.remove(randomIndex);
            return randomCard;
        }

        public Card getRandomCardFaceUp() {
            int randomIndex = (int) (Math.random() * cards.size());
            Card randomCard = cards.get(randomIndex);
            randomCard.state = CardState.FACE_UP;
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

        private ArrayList<Card> createDeck(int RANDOM_SEED, int size) {
            ArrayList<Card> cards = new ArrayList<Card>();
            String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
            for (int i = 0; i < suits.length; i++) {
                for (int j = 1; j <= 13; j++) {
                    cards.add(new Card(suits[i], j));
                }
            }
            Collections.shuffle(cards);
            return cards;
        }

        /*
        @NOTE: This is a safer, casino-friendly method of creating a blackjack deck.
         */
        private ArrayList<Card> createDeck() {
            // generate a random number
            int RANDOM_SEED = (int) (Math.random() * 1000);
            return createDeck(RANDOM_SEED, 312);
        }

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
    }

    class Team {
        int numPlayers;
        Player[] players;
    }

}