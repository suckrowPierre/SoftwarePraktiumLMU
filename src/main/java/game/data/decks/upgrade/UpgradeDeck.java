package game.data.decks.upgrade;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.upgrade.AdminPrivilege;
import game.data.cards.upgrade.MemorySwap;
import game.data.cards.upgrade.RearLaser;
import game.data.cards.upgrade.SpamBlocker;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a UpgradeDeck with all upgrade cards
 *
 * @author Simon Hümmer
 */
public class UpgradeDeck extends Deck {

    private ArrayList<Card> upgradeDeck;

    @Override
    public void initialize() {
        this.upgradeDeck = new ArrayList<>();

        for (int i = 0; i < Parameter.ADMINPRIVILEDGE_CARDS_AMOUNT; i++) {
            upgradeDeck.add(new AdminPrivilege());

        }

        for (int i = 0; i < Parameter.MEMORYSWAP_CARDS_AMOUNT; i++) {
            upgradeDeck.add(new MemorySwap());
        }

        for (int i = 0; i < Parameter.REARLASER_CARDS_AMOUNT; i++) {
            upgradeDeck.add(new RearLaser());
        }

        for (int i = 0; i < Parameter.SPAMBLOCKER_CARDS_AMOUNT; i++) {
            upgradeDeck.add(new SpamBlocker());
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return upgradeDeck;
    }
}
