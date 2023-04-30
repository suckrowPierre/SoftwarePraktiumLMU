package game.data.decks.programming;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.data.cards.programming.Null;
import game.data.decks.Deck;

import java.util.ArrayList;

/**
 * This class implements a RegisterDeck for each player
 *
 * @author Simon Hümmer
 */
public class RegisterDeck extends Deck {

    private Card[] registerDeck;

    @Override
    public void initialize() {
        this.registerDeck = new Card[Parameter.REGISTER_CARDS_AMOUNT];
        for (int i = 0; i < registerDeck.length; i++) {
            registerDeck[i] = new Null();
        }
    }

    @Override
    public void shuffle() {}

    @Override
    public ArrayList<Card> getDeck() {
        return null;
    }

    public Card[] getRegisterDeck() {
        return registerDeck;
    }

    /**
     * @return topCard of your registerDeck
     */
    public Card getTopCardFromRegister() {
        for (Card card : registerDeck) {
            if (card.getCardName() != CardName.Null) {
                return card;
            }
        }
        return null;
    }

    /**
     * removes the topCard from your registerDeck
     */
    public void removeTopCardFromRegister() {
        for (int i = 0; i < registerDeck.length; i++) {
            if (registerDeck[i].getCardName() != CardName.Null) {
                registerDeck[i] = new Null();
                break;
            }
        }
    }
}
