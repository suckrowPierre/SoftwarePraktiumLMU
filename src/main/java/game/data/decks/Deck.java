package game.data.decks;

import game.data.cards.Card;

import java.util.ArrayList;

/**
 * Abstract class for every Deck in the game.
 *
 * @author Simon Hümmer
 */
public abstract class Deck {

    public abstract void initialize();

    public abstract void shuffle();

    public abstract ArrayList<Card> getDeck();

    public Card getTopCard() {
        return this.getDeck().get(0);
    }

    public void removeTopCard(ArrayList<Card> deck) {
        deck.remove(0);
    }
}
