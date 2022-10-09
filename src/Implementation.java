import java.util.*;

public class Implementation {

    // create a card class
    public class Card {
        private String suit;
        private int value;

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
        private ArrayList<Card> cards;
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

    class Banker extends Backend.Player {
        int balance;

        public Banker(int id, String name) {
            super();
        }
    }
}
