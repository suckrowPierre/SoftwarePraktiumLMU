package game.data.decks.damage;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.damage.Trojan;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a TrojanHorseDeck
 *
 * @author Simon Hümmer
 */
public class TrojanDeck extends Deck {

    private ArrayList<Card> trojanDeck;

    @Override
    public void initialize() {
        this.trojanDeck = new ArrayList<>();

        for (int i = 0; i < Parameter.TROJAN_CARDS_AMOUNT; i++) {
            trojanDeck.add(new Trojan());
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return trojanDeck;
    }
}
