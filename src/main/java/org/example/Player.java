package org.example;

import java.util.ArrayList;


// Represents the player in the blackjack game.
public class Player {
    // Variables
    private ArrayList<Card> hand;        // The player's current hand of cards
    private int money;                 // The player's current money
    private int bet;                     // The player's current bet amount

    // Initial Constructor
    public Player(int money) {
        this.hand = new ArrayList<>();
        this.money = money;
    }

    // Adds card to player arrayList hand
    public void addCard(Card card) {
        hand.add(card);
    }

    // Returns players hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Hand value logic
    public int handValue() {
        // Initialize values
        int totalValue = 0;   // totalValue of the hand
        int numOfAces = 0;    // Number of aces in the hand

        // Calculate the totalValue of the hand
        for (Card card : hand) {
            switch (card.getValue()) {
                case "Jack":
                case "Queen":
                case "King":
                    totalValue += 10;
                    break;
                case "Ace":
                    numOfAces++;
                    totalValue += 11;
                    break;
                default:
                    totalValue += Integer.parseInt(card.getValue());
                    break;
            }
        }

        // Adjust the totalValue for numOfAces if the hand totalValue exceeds 21
        while (totalValue > 21 && numOfAces > 0) {
            totalValue -= 10;
            numOfAces--;
        }

        return totalValue;
    }

    // Clear hand of player
    public void clearHand() {
        hand.clear();
    }

    // Bet money amount given in argument
    public void betMoney(int amount) {
        if (amount > money) {
            throw new IllegalArgumentException("Bet amount exceeds money");
        }
        bet = amount;
        money -= amount;
    }

    // Add money if we win
    public void winCase() {
        money += 2 * bet; // Win amount is double the bet
        bet = 0;           // Reset the bet
    }

    // Clear bet if we lose
    public void loseCase() {
        bet = 0; // Reset the bet
    }

    // Double the bet if we have enough money
    public void doubleDownBet() {
        if (bet * 2 > money) {
            throw new IllegalArgumentException("Not enough money to bet! Please add more funds at the start of the next game");
        }
        money -= bet;  // Deduct the additional bet amount
        bet *= 2;        // Double the bet
    }

    // Return money
    public int getMoney() {
        return money;
    }

}
