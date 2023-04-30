package game.data.decks.programming;

import game.data.cards.Card;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a DiscardDeck for each player
 *
 * @author Simon Hümmer
 */
public class DiscardDeck extends Deck {

    private ArrayList<Card> discardDeck;

    @Override
    public void initialize() {
        this.discardDeck = new ArrayList<>();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return discardDeck;
    }

    /**
     * This method adds all cards of the DiscardDeck to a ProgrammingDeck
     */
    public void addDiscardToProgramming(ProgrammingDeck programmingDeck) {
        programmingDeck.getDeck().addAll(discardDeck);
        discardDeck.clear();
    }
}
