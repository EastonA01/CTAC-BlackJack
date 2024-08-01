package org.example;

import java.util.ArrayList;
import java.util.Collections;

// Class with the properties of a deck
public class Deck implements DeckActions {
    // Array of cards
    private ArrayList<Card> cards = new ArrayList<>();
    private int numCards;
    private int currentCardIndex = 0; // Tracks the next card to be dealt

    // Deck constructor
    public Deck() {
        // Card values
        String[] cardValues = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        // Suits
        String[] suits = new String[]{"Hearts", "Diamonds", "Clubs", "Spades"};
        // Iterate through each suit
        for (String suit : suits) {
            // Iterate through each card in a suit and add to Deck
            for (String card : cardValues) {
                this.cards.add(new Card(suit, card));
            }
        }
        this.numCards = this.cards.size();
    }

    @Override
    public void shuffle() {
        // Collections library has a shuffle method!!!!!!
        Collections.shuffle(this.cards);
        currentCardIndex = 0; // Reset the index after shuffling
    }

    @Override
    public Card dealNextCard() {
        // Check if the current index is within the bounds of the deck
        if (currentCardIndex < this.cards.size()) {
            return this.cards.remove(currentCardIndex);
        } else {
            System.out.println("No more cards to deal");
            return null;
        }
    }

    @Override
    public void printDeck(int numberOfCards) {
        // Print the specified number of cards
        System.out.println("The deck size is: " + numberOfCards + "\n");
        System.out.println("The deck contains " + this.numCards + " cards\n");
        for (int i = 0; i < this.numCards; i++) {
            System.out.println(this.cards.get(i).toString());
        }
    }
}
