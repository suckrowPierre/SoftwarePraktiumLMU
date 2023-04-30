package game.data.decks.upgrade;

import game.data.cards.Card;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a UpgradeDiscardDeck
 *
 * @author Simon Hümmer
 */
public class UpgradeDiscardDeck extends Deck {

    private ArrayList<Card> upgradeDiscardDeck;

    @Override
    public void initialize() {
        this.upgradeDiscardDeck = new ArrayList<>();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return upgradeDiscardDeck;
    }
}
