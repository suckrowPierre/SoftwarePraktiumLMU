package test.game.data.cards.upgrade;

import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.Parameter;
import game.data.cards.CardName;
import game.data.cards.damage.Spam;
import game.data.cards.programming.MoveIII;
import game.data.cards.programming.Null;
import game.data.cards.upgrade.SpamBlocker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpamBlockerTest {

    @Test
    public void constructorInitialization() {
        SpamBlocker spamBlocker = new SpamBlocker();

        assertEquals(CardName.SpamBlocker, spamBlocker.getCardName());
        assertEquals(3, spamBlocker.getCost());
    }

    @Test
    public void activateCard_spamCardIsReplacedWithTopDeckCard() {
        //initialize player amd his hand deck
        ServerConnectedPlayer player = new ServerConnectedPlayer(Groups.PragmatischePinguine, false, 0);

        //set player hand deck to contain two cards, one of them Spam
        player.getPlayerCards().getHandDeck().getHandDeck()[0] = new Spam();
        player.getPlayerCards().getHandDeck().getHandDeck()[1] = new SpamBlocker();
        for (int i = 2; i < Parameter.HAND_CARDS_AMOUNT; i++) {
            player.getPlayerCards().getHandDeck().getHandDeck()[i] = new Null();
        }

        assertEquals(CardName.Spam, player.getPlayerCards().getHandDeck().getHandDeck()[0].getCardName());
        assertEquals(CardName.SpamBlocker, player.getPlayerCards().getHandDeck().getHandDeck()[1].getCardName());

        //put MoveIII card in programming deck
        player.getPlayerCards().getProgrammingDeck().getDeck().clear();
        player.getPlayerCards().getProgrammingDeck().getDeck().add(new MoveIII());

        assertEquals(CardName.MoveIII, player.getPlayerCards().getProgrammingDeck().getTopCard().getCardName(), "Expected top deck card to be MoveIII");

        SpamBlocker spamBlockerCard = new SpamBlocker();
        spamBlockerCard.activateCard(player);

        assertEquals(CardName.MoveIII, player.getPlayerCards().getHandDeck().getHandDeck()[0].getCardName(), "Expected Spam to be replaced with MoveIII");
        assertEquals(CardName.SpamBlocker, player.getPlayerCards().getHandDeck().getHandDeck()[1].getCardName(), "Expected 2nd card to remain unchanged");
    }

    @Test
    void spamBlockerIsNoDamageCard() {
        SpamBlocker spamBlocker = new SpamBlocker();
        assertFalse(spamBlocker.isDamageCard());
    }

    @Test
    void spamBlockerIsUpgradeCard() {
        SpamBlocker spamBlocker = new SpamBlocker();
        assertTrue(spamBlocker.isUpgradeCard());
    }
}