package game.data.decks.damage;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.damage.Virus;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a VirusDeck
 *
 * @author Simon Hümmer
 */
public class VirusDeck extends Deck {

    private ArrayList<Card> virusDeck;

    @Override
    public void initialize() {
        this.virusDeck = new ArrayList<>();

        for (int i = 0; i < Parameter.VIRUS_CARDS_AMOUNT; i ++) {
            virusDeck.add(new Virus());
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return virusDeck;
    }
}
