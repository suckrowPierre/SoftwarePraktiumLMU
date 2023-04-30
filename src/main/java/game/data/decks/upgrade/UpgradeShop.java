package game.data.decks.upgrade;

import communication.server.GameHandler;
import game.data.cards.Card;
import game.data.cards.programming.Null;
import game.data.decks.Deck;

import java.util.ArrayList;

/**
 * This class implements a UpgradeShop
 *
 * @author Simon Hümmer
 */
public class UpgradeShop extends Deck {

    private Card[] upgradeShop;

    @Override
    public void initialize() {
        this.upgradeShop = new Card[GameHandler.getInstance().getPlayers().size()];
        for (int i = 0; i < upgradeShop.length; i++) {
            upgradeShop[i] = new Null();
        }
    }

    @Override
    public void shuffle() {}

    @Override
    public ArrayList<Card> getDeck() {
        return null;
    }

    public Card[] getUpgradeShop() {
        return upgradeShop;
    }
}
