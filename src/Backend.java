/*
This code is a direct fork of the code provided in A1, with tweaks for readability.
Cell has been replaced with Card - and a few member states have been modified.
Board has been replaced with Deck - and a few member states have been modified.
Player and Banker have been separated into their own classes - for ease of use.

 */

import java.util.ArrayList;
import java.util.Collections;

public class Backend {

    int numberOfPlayers;
    int numberOfRounds;
    Player[] players;
    int[][] scoreBoard; // dict
    int[] draws;

    public Backend(int numberOfPlayers, int numberOfRounds) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRounds = numberOfRounds;
        this.players = new Player[numberOfPlayers];
        this.scoreBoard = new int[numberOfPlayers][numberOfRounds];
        this.draws = new int[numberOfRounds];
    }

    enum GameName {
        TICTACTOE, ORDERANDCHAOS, TRIANTAENA
    }

    interface ruleSet {
    }

    static class Player {
        int id;
        String name;
        int score;
        boolean isTurn;
        int[] balance;
        boolean isWinner = false;

        public Player(int id, String name) {
            this.id = id;
            this.name = name;
            this.score = 0;
            this.isTurn = false;
        }
    }

    // create a card class
    public class Card {
        private final String suit;
        private final int value;

        public Card(String suit, int value) {
            this.suit = suit;
            this.value = value;
        }

        public String getSuit() {
            return suit;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            return value + " of " + suit;
        }
    }

    public class Deck {
        private final ArrayList<Card> cards;
        private int size;

        public Deck() {
            cards = new ArrayList<Card>();
            size = 0;
        }

        public void addCard(Card card) {
            cards.add(card);
            size++;
        }

        public void removeCard(Card card) {
            cards.remove(card);
            size--;
        }

        public Card getCard(int index) {
            return cards.get(index);
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