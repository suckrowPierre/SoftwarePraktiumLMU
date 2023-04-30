package game.data.decks.damage;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.damage.Spam;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a SpamDeck
 *
 * @author Simon Hümmer
 */
public class SpamDeck extends Deck {

    private ArrayList<Card> spamDeck;

    @Override
    public void initialize() {
        this.spamDeck = new ArrayList<>();

        for (int i = 0; i < Parameter.SPAM_CARDS_AMOUNT; i++) {
            spamDeck.add(new Spam());
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return spamDeck;
    }
}
