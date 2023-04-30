package game.data.decks.programming;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.data.cards.programming.Null;
import game.data.decks.Deck;

import java.util.ArrayList;

/**
 * This method implements a HandDeck for each player
 */
public class HandDeck extends Deck {

    private Card[] handDeck;

    @Override
    public void initialize() {
        this.handDeck = new Card[Parameter.HAND_CARDS_AMOUNT];
        for (int i = 0; i < handDeck.length; i++) {
            handDeck[i] = new Null();
        }
    }

    @Override
    public void shuffle() {
    }

    @Override
    public ArrayList<Card> getDeck() {
        return null;
    }

    public Card[] getHandDeck() {
        return handDeck;
    }

    /**
     * @return topCard of your handDeck
     */
    public Card getTopCardFromHandDeck() {
        for (Card card : handDeck) {
            if (card.getCardName() != CardName.Null) {
                return card;
            }
        }
        return null;
    }

    /**
     * removes the topCard from your handDeck
     */
    public void removeTopCardFromHandDeck() {
        for (int i = 0; i < handDeck.length; i++) {
            if (handDeck[i].getCardName() != CardName.Null) {
                handDeck[i] = new Null();
                break;
            }
        }
    }
}
