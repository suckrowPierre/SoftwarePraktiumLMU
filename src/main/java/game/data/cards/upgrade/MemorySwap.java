package game.data.cards.upgrade;

import communication.protocol_v2v1.sendobjects.game_move.programm_phase.YourCards;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedClient;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.ArrayList;

/**
 * This class implements the MemorySwap Card
 *
 * @author Simon Hümmer
 */
public class MemorySwap extends Card {

    public MemorySwap() {
        cardName = CardName.MemorySwap;
        cardDescription = "Draw three cards. Then choose three from your hand to put on top of your deck.";
        cost = 1;
    }

    /**
     * This method tells the player the names of the top three cards of his programming deck
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'MemorySwap' for player: " + player.getName());
        ArrayList<CardName> cardNames = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if (player.getPlayerCards().getProgrammingDeck().getDeck().size() < 3) {
                player.getPlayerCards().getDiscardDeck().addDiscardToProgramming(player.getPlayerCards().getProgrammingDeck());
            }
            Card topCard = player.getPlayerCards().getProgrammingDeck().getDeck().get(i);
            cardNames.add(topCard.getCardName());
        }

        CardName[] swapCards = new CardName[cardNames.size()];
        swapCards = cardNames.toArray(swapCards);

        for (ServerConnectedClient serverConnectedClient : GameHandler.getInstance().getPlayers()) {
            if (serverConnectedClient.getConnectedPlayer().getClientID() == player.getClientID()) {
                serverConnectedClient.safeWriteInOwnOutStream(new YourCards(swapCards));
            }
        }
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }

    @Override
    public boolean isUpgradeCard() {
        return true;
    }

    @Override
    public int getCost() {
        return cost;
    }
}
