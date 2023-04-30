package game.data.decks.damage;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.damage.Worm;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a WormDeck
 *
 * @author Simon Hümmer
 */
public class WormDeck extends Deck {

    private ArrayList<Card> wormDeck;

    @Override
    public void initialize() {
        this.wormDeck = new ArrayList<>();

        for (int i = 0; i < Parameter.WORM_CARDS_AMOUNT; i++) {
            wormDeck.add(new Worm());
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return wormDeck;
    }
}
