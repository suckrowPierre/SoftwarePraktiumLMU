package game.data.decks.special;

import game.data.cards.Card;
import game.data.cards.special.*;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck with special cards for every player.
 *
 * @author Simon Hümmer
 */
public class SpecialDeck extends Deck {

    private ArrayList<Card> specialDeck;

    @Override
    public void initialize() {
        this.specialDeck = new ArrayList<>();

        specialDeck.add(new EnergyRoutine());
        specialDeck.add(new RepeatRoutine());
        specialDeck.add(new SandboxRoutine());
        specialDeck.add(new SpeedRoutine());
        specialDeck.add(new WeaselRoutine());
        specialDeck.add(new SpamFolder());
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return specialDeck;
    }
}
