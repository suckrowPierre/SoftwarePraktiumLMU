package test;

import game.data.cards.Card;
import game.data.decks.programming.HandDeck;
import game.data.decks.programming.ProgrammingDeck;

public class testtest {

    public static void main(String[] args) {
        ProgrammingDeck deck = new ProgrammingDeck();
        deck.initialize();

        HandDeck hand = new HandDeck();
        hand.initialize();


        Card topCard = deck.getTopCard();

        hand.getDeck().add(topCard);
        System.out.println(hand.getTopCard().cardName);

        deck.removeTopCard(deck.getDeck());
        System.out.println(deck.getDeck().size());
    }

}
