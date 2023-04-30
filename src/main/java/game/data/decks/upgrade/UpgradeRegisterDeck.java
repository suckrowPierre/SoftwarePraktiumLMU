package game.data.decks.upgrade;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.programming.Null;
import game.data.decks.Deck;

import java.util.ArrayList;

/**
 * This class implements a UpgradeRegisterDeck for each player
 *
 * @author Simon Hümmer
 */
public class UpgradeRegisterDeck extends Deck {

    private Card[] upgradeRegisterDeck;

    @Override
    public void initialize() {
        this.upgradeRegisterDeck = new Card[Parameter.UPGRADEREGISTER_CARDS_AMOUNT];
        for (int i = 0; i < upgradeRegisterDeck.length; i++) {
            upgradeRegisterDeck[i] = new Null();
        }
    }

    @Override
    public void shuffle() {}

    @Override
    public ArrayList<Card> getDeck() {
        return null;
    }

    public Card[] getUpgradeRegisterDeck() {
        return upgradeRegisterDeck;
    }
}
