package org.example;

public interface DeckActions {
    void shuffle() ;

    Card dealNextCard();

    void printDeck(int numToPrint) ;
}