package org.example;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    // Class variables
    private Deck deck; // The deck of cards used in the game
    private final Player player; // The player participating in the game
    private final Player dealer; // Deals the cards

    // Make a game with a shuffled deck and a player and a dealer
    public GameRunner() {
        this.deck = new Deck();  // Initialize the deck
        this.deck.shuffle();     // Shuffle the deck
        this.player = new Player(100); // Create a player with initial balance
        this.dealer = new Player(0);  // Create a dealer with no balance (N/A, dealer can't bust)
    }

    // Game loop method
    public void playRound() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuePlaying;

            do {
                // Display player balance and prompt for bet amount
                System.out.println("Money: $" + player.getMoney());
                int betAmount = getValidBet(scanner);
                player.betMoney(betAmount);

                // Deal initial cards to player and dealer
                dealInitialCards();

                // Display hands
                printHands();

                // Track game status
                boolean playerActive = true;
                boolean doubledDown = false;

                // Player's turn
                playerActive = handlePlayerTurn(scanner, playerActive);

                // Handle Double Down scenario
                if (doubledDown) {
                    playerActive = handleDoubleDown(scanner, playerActive);
                }

                // Dealer's turn
                playDealerTurn();

                // Determine the winner
                winCheck();

                // Clear hands for the next round
                resetGame();

                // Ask if the player wants to play again
                System.out.print("Play again? (yes/no): ");
                continuePlaying = scanner.next().equalsIgnoreCase("yes");

            } while (continuePlaying);
        }
    }

    private int getValidBet(Scanner scanner) {
        System.out.print("How much do you want to bet? (bets MUST be in sets of 5): ");
        int betAmount;
        while ((betAmount = scanner.nextInt()) % 5 != 0) {
            System.out.print("Your bet is invalid, please place a VALID bet: ");
        }
        return betAmount;
    }

    private void dealInitialCards() {
        player.addCard(deck.dealNextCard());
        player.addCard(deck.dealNextCard());
        dealer.addCard(deck.dealNextCard());
        dealer.addCard(deck.dealNextCard());
    }

    private void printHands() {
        PrintStream output = System.out;
        ArrayList<Card> playerHand = player.getHand();
        output.println("Your hand: " + playerHand + " (value: " + player.handValue() + ")");
        output.println("Dealer's hand: [" + dealer.getHand().get(0) + ", ?]");
    }

    private boolean handlePlayerTurn(Scanner scanner, boolean playerActive) {
        while (playerActive && player.handValue() < 21) {
            System.out.print("Do you want to: \n [1] Hit\n [2] Stand\n [3] Double-down\n");
            int action = scanner.nextInt();
            switch (action) {
                case 1: // Hit
                    player.addCard(deck.dealNextCard());
                    printPlayerHand();
                    break;
                case 3: // Double Down
                    player.doubleDownBet();
                    boolean doubledDown = true;
                    printPlayerHand("after doubling down");
                    playerActive = false;
                    break;
                default: // Stand
                    playerActive = false;
                    break;
            }
        }
        return playerActive;
    }

    private void printPlayerHand() {
        PrintStream output = System.out;
        output.println("Your hand: " + player.getHand() + " (value: " + player.handValue() + ")");
    }

    private void printPlayerHand(String message) {
        PrintStream output = System.out;
        output.println("Your hand " + message + ": " + player.getHand() + " (value: " + player.handValue() + ")");
    }

    private boolean handleDoubleDown(Scanner scanner, boolean playerActive) {
        while (playerActive && player.handValue() < 21) {
            System.out.print("Do you want to (1) hit or (2) stand? ");
            int action = scanner.nextInt();
            if (action == 1) {
                player.addCard(deck.dealNextCard());
                printPlayerHand();
            } else {
                playerActive = false;
            }
        }
        return playerActive;
    }
    

    private void playDealerTurn() {
        while (dealer.handValue() < 17) {
            dealer.addCard(deck.dealNextCard());
        }
        PrintStream output = System.out;
        output.println("Dealer's hand: " + dealer.getHand() + " (value: " + dealer.handValue() + ")");
    }

    private void winCheck() {
        // Values
        int playerHandValue = player.handValue();
        int dealerHandValue = dealer.handValue();

        String resultMessage;
        boolean playerBusted = playerHandValue > 21;
        boolean dealerBusted = dealerHandValue > 21;

        if (playerBusted) {
            resultMessage = "You lose!";
            player.loseCase();
        } else if (dealerBusted || playerHandValue > dealerHandValue) {
            resultMessage = "You win!";
            player.winCase();
        } else if (playerHandValue == dealerHandValue) {
            resultMessage = "Dealer and Player Tie!";
            player.winCase(); // Assuming a tie results in a win case for the player
        } else {
            resultMessage = "House rules, you lose!"; // If something goes awry
            player.loseCase();
        }

        System.out.println(resultMessage);
    }



    private void resetGame() {
        player.clearHand();
        dealer.clearHand();
    }
}
