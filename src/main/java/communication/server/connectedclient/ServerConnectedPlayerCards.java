package communication.server.connectedclient;

import game.data.Parameter;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.data.cards.programming.Null;
import game.data.decks.programming.DiscardDeck;
import game.data.decks.programming.HandDeck;
import game.data.decks.programming.ProgrammingDeck;
import game.data.decks.programming.RegisterDeck;
import game.data.decks.upgrade.UpgradeRegisterDeck;

import java.util.ArrayList;

/**
 * All the Cards of ServerConnectedPlayer
 * @author Simon Hümmer, Pierre-Louis Suckrow
 */
public class ServerConnectedPlayerCards {

    private ProgrammingDeck programmingDeck;
    private DiscardDeck discardDeck;
    private HandDeck handDeck;
    private RegisterDeck registerDeck;
    private UpgradeRegisterDeck upgradeRegisterDeck;
    private Card lastPlayedCard;

    public ServerConnectedPlayerCards() {
        this.programmingDeck = new ProgrammingDeck();
        programmingDeck.initialize();
        programmingDeck.shuffle();

        this.discardDeck = new DiscardDeck();
        discardDeck.initialize();

        this.handDeck = new HandDeck();
        handDeck.initialize();

        this.registerDeck = new RegisterDeck();
        registerDeck.initialize();

        this.upgradeRegisterDeck = new UpgradeRegisterDeck();
        upgradeRegisterDeck.initialize();
    }

    public void drawHandCards() {
        for (int i = 0; i < Parameter.HAND_CARDS_AMOUNT; i++) {
            if (programmingDeck.getDeck().isEmpty()) {
                discardDeck.addDiscardToProgramming(programmingDeck);
                programmingDeck.shuffle();
            }
            if (programmingDeck.getTopCard().getCardName().equals(CardName.Null)) {
                programmingDeck.removeTopCard(programmingDeck.getDeck());
            } else {
                handDeck.getHandDeck()[i] = programmingDeck.getTopCard();
                programmingDeck.removeTopCard(programmingDeck.getDeck());
            }
        }
    }


    private Card getCardFromHand(CardName name) {
        for (int i = 0; i < handDeck.getHandDeck().length; i++) {
            if (handDeck.getHandDeck()[i].getCardName().equals(name)) {
                Card card = handDeck.getHandDeck()[i];
                handDeck.getHandDeck()[i] = new Null();
                return card;
            }
        }
        return null;
    }

    public boolean putSelectedCardInRegister(int registerID, CardName cardName) {
        Card card = getCardFromHand(cardName);
        if (card != null) {
            registerDeck.getRegisterDeck()[registerID - 1] = card;
            return true;
        } else {
            return false;
        }

    }

    public void resetCard(int registerID) {
        for (int i = 0; i < handDeck.getHandDeck().length; i++) {
            if (handDeck.getHandDeck()[i].getCardName().equals(CardName.Null)) {
                handDeck.getHandDeck()[i] = registerDeck.getRegisterDeck()[registerID - 1];
            }
        }
        registerDeck.getRegisterDeck()[registerID-1] = new Null();
    }

    public void clearHandCards() {
        for (int i = 0; i < handDeck.getHandDeck().length; i++) {
            if (handDeck.getHandDeck()[i].getCardName() != CardName.Null) {
                discardDeck.getDeck().add(handDeck.getHandDeck()[i]);
                handDeck.getHandDeck()[i] = new Null();
            }
        }
    }

    public void clearRegister() {
        for (int i = 0; i < registerDeck.getRegisterDeck().length; i++) {
            if (registerDeck.getRegisterDeck()[i].getCardName() != CardName.Null) {
                discardDeck.getDeck().add(registerDeck.getRegisterDeck()[i]);
                registerDeck.getRegisterDeck()[i] = new Null();
            }
        }
    }

    public CardName[] autoFillRegisters() {
        ArrayList<CardName> returnCards = new ArrayList<>();
        for (int i = 0; i < registerDeck.getRegisterDeck().length; i++) {
            Card card = registerDeck.getRegisterDeck()[i];
            System.out.println("i: "+ i + card.getCardName());
            if (card.getCardName().equals(CardName.Null)) {
                if (programmingDeck.getDeck().isEmpty()) {
                    discardDeck.addDiscardToProgramming(programmingDeck);
                    programmingDeck.shuffle();
                }
                    Card topCard = programmingDeck.getTopCard();
                    registerDeck.getRegisterDeck()[i] = topCard;
                    returnCards.add(topCard.getCardName());
                    programmingDeck.removeTopCard(programmingDeck.getDeck());

            }
        }

        CardName[] cardNames = new CardName[returnCards.size()];
        cardNames = returnCards.toArray(cardNames);
        return cardNames;
    }

    public Card getCurrentCard() {
        return registerDeck.getTopCardFromRegister();
    }

    public Card playCard() {
        Card card = getCurrentCard();
        if(card.getCardName() != CardName.Again){
            lastPlayedCard = card;
            discardDeck.getDeck().add(card);
            registerDeck.removeTopCardFromRegister();
        }else {
            discardDeck.getDeck().add(card);
            registerDeck.removeTopCardFromRegister();
            card = lastPlayedCard;
        }

        if (registerDeck.getRegisterDeck()[4].getCardName().equals(CardName.Null)) {
            clearHandCards();
        }

        return card;
    }

    public ProgrammingDeck getProgrammingDeck() {
        return programmingDeck;
    }

    public DiscardDeck getDiscardDeck() {
        return discardDeck;
    }

    public HandDeck getHandDeck() {
        return handDeck;
    }

    public RegisterDeck getRegisterDeck() {
        return registerDeck;
    }

    public UpgradeRegisterDeck getUpgradeRegisterDeck() {
        return upgradeRegisterDeck;
    }

    public CardName[] getHandDeckNames() {
        CardName[] cardNames = new CardName[handDeck.getHandDeck().length];
        int i = 0;
        for (Card card : handDeck.getHandDeck()) {
            cardNames[i] = card.getCardName();
            i++;
        }
        return cardNames;
    }

    public boolean allCardsInRegister() {
        for (Card card : registerDeck.getRegisterDeck()) {
            if (card.getCardName() == CardName.Null) {
                return false;
            }
        }
        return true;
    }

    public Card getRegisterDeckByPosition(int register) {
        return registerDeck.getRegisterDeck()[register];
    }

    public int getCurrentRegister() {
        int i = 0;
        for (Card card : registerDeck.getRegisterDeck()) {
            if (card.getCardName().equals(CardName.Null)) {
                i++;
            }
        }
        return i;
    }
}
