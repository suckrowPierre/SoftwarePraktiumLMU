package test.game.data.cards.upgrade;

import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import communication.protocol_v2v1.sendobjects.cards.PlayCard;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import communication.server.connectedclient.ServerRecieverHandler;
import game.data.cards.CardName;
import game.data.cards.programming.MoveI;
import game.data.cards.programming.MoveIII;
import game.data.cards.upgrade.MemorySwap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MemorySwapTest {

    @Test
    public void constructorInitialization() {
        MemorySwap memorySwap = new MemorySwap();

        assertEquals(CardName.MemorySwap, memorySwap.getCardName());
        assertEquals(1, memorySwap.getCost());
    }

    @Test
    @Disabled
    public void activateCard() {
        GameHandler gameHandler = GameHandler.getInstance();
        //gameHandler.checkForNewPhase();

        ServerRecieverHandler serverRecieverHandler = new ServerRecieverHandler(0);
        ServerConnectedPlayer player = new ServerConnectedPlayer(Groups.PragmatischePinguine, false, 0);

        //set 3 MoveI cards into hands deck
        player.getPlayerCards().getHandDeck().getHandDeck()[0] = new MoveI();
        player.getPlayerCards().getHandDeck().getHandDeck()[1] = new MoveI();
        player.getPlayerCards().getHandDeck().getHandDeck()[2] = new MoveI();

        //put 3 MoveIII cards to the top of programming deck
        player.getPlayerCards().getProgrammingDeck().getDeck().clear();
        player.getPlayerCards().getProgrammingDeck().getDeck().add(new MoveIII());
        player.getPlayerCards().getProgrammingDeck().getDeck().add(new MoveIII());
        player.getPlayerCards().getProgrammingDeck().getDeck().add(new MoveIII());

        //Play MemorySwap Card
        MemorySwap memorySwapCard = new MemorySwap();
        serverRecieverHandler.handle(new PlayCard(memorySwapCard.getCardName()));

        //verify that selected handDeck cards were replaced by top 3 programming deck cards
        assertEquals(CardName.MoveIII, player.getPlayerCards().getHandDeck().getHandDeck()[0].getCardName(), "Expected handDeck card 1 replaced with MoveIII card");
        assertEquals(CardName.MoveIII, player.getPlayerCards().getHandDeck().getHandDeck()[1].getCardName(), "Expected handDeck card 2 replaced with MoveIII card");
        assertEquals(CardName.MoveIII, player.getPlayerCards().getHandDeck().getHandDeck()[2].getCardName(), "Expected handDeck card 3 replaced with MoveIII card");
    }

    @Test
    public void activateCard_cannotBePlayedInPhase0() {
        GameHandler gameHandler = GameHandler.getInstance();

        assertEquals(0, gameHandler.getPhase(), "Expected current phase is 0");

        ServerRecieverHandler serverRecieverHandler = new ServerRecieverHandler(0);

        //Play MemorySwap Card
        MemorySwap memorySwapCard = new MemorySwap();
        ProtocolSendObject handleResult = serverRecieverHandler.handle(new PlayCard(memorySwapCard.getCardName()));

        assertInstanceOf(ErrorMessage.class, handleResult, "Expected ErrorMessage");

        ErrorMessage errorMessage = (ErrorMessage) handleResult;
        assertEquals("wrong phase", errorMessage.getMessage(), "Expected wrong phase message");
    }

    @Test
    void memorySwapIsNoDamageCard() {
        MemorySwap memorySwap = new MemorySwap();
        assertFalse(memorySwap.isDamageCard());
    }

    @Test
    void memorySwapIsUpgradeCard() {
        MemorySwap memorySwap = new MemorySwap();
        assertTrue(memorySwap.isUpgradeCard());
    }
}