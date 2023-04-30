package game.data.decks.programming;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.programming.*;
import game.data.decks.Deck;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements a ProgrammingDeck for each player
 *
 * @author Simon H.
 */
public class ProgrammingDeck extends Deck {

    private ArrayList<Card> programmingDeck;

    @Override
    public void initialize() {
        this.programmingDeck = new ArrayList<>();

        for (int i = 0; i < Parameter.MOVEI_CARDS_AMOUNT; i++) {
            programmingDeck.add(new MoveI());
        }

        for (int i = 0; i < Parameter.MOVEII_CARDS_AMOUNT; i++) {
            programmingDeck.add(new MoveII());
        }

        for (int i = 0; i < Parameter.MOVEIII_CARDS_AMOUNT; i ++) {
            programmingDeck.add(new MoveIII());
        }

        for (int i = 0; i < Parameter.BACKUP_CARDS_AMOUNT; i ++) {
            programmingDeck.add(new BackUp());
        }

        for (int i = 0; i < Parameter.TURNLEFT_CARDS_AMOUNT; i++) {
            programmingDeck.add(new TurnLeft());
        }

        for (int i = 0; i < Parameter.TURNRIGHT_CARDS_AMOUNT; i++) {
            programmingDeck.add(new TurnRight());
        }

        for (int i = 0; i < Parameter.UTURN_CARDS_AMOUNT; i ++) {
            programmingDeck.add(new UTurn());
        }

        for (int i = 0; i < Parameter.AGAIN_CARDS_AMOUNT; i ++) {
            programmingDeck.add(new Again());
        }

        for (int i = 0; i < Parameter.POWERUP_CARDS_AMOUNT; i ++) {
            programmingDeck.add(new PowerUp());
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.getDeck());
    }

    @Override
    public ArrayList<Card> getDeck() {
        return programmingDeck;
    }
}
