package org.example;

public class Card {
    // Variables
    private final String suit;
    private final String value;
    // Constructor
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }
    // Get the value of this card
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value + " of " + this.suit;
    }
}