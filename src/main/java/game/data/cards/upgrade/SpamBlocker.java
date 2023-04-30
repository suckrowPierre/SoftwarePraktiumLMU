package game.data.cards.upgrade;

import communication.protocol_v2v1.sendobjects.game_move.programm_phase.YourCards;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedClient;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.servergame.BoardElementInteraction;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.ArrayList;

/**
 * This class implements the SpamBlocker Card
 *
 * @author Simon Hümmer
 */
public class SpamBlocker extends Card {

    public SpamBlocker() {
        cardName = CardName.SpamBlocker;
        cardDescription = "Replace each SPAM damage card in your hand with a card from the top of your deck. " +
                "Immediately discard the SPAM damage cards by placing them in the SPAM damage card draw pile. " +
                "If you draw new SPAM damage cards from your deck, keep them in your hand for this round";
        cost = 3;
    }

    /**
     * All Spam Cards are replaced by cards from your programming deck
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'SpamBlocker' for player: " + player.getName());

        BoardElementInteraction boardElementInteraction = new BoardElementInteraction();

        for (int i = 0; i < player.getPlayerCards().getHandDeck().getHandDeck().length; i++) {
            if (player.getPlayerCards().getHandDeck().getHandDeck()[i].getCardName().equals(CardName.Spam)) {
                boardElementInteraction.getSpamDeck().getDeck().add(this);
                if (player.getPlayerCards().getProgrammingDeck().getDeck().isEmpty()) {
                    player.getPlayerCards().getDiscardDeck().addDiscardToProgramming(player.getPlayerCards().getProgrammingDeck());
                    player.getPlayerCards().getProgrammingDeck().shuffle();
                }
                Card topCard = player.getPlayerCards().getProgrammingDeck().getTopCard();
                player.getPlayerCards().getProgrammingDeck().removeTopCard(player.getPlayerCards().getProgrammingDeck().getDeck());
                player.getPlayerCards().getHandDeck().getHandDeck()[i] = topCard;
            }
        }

        ArrayList<CardName> cardNames = new ArrayList<>();
        for (Card card : player.getPlayerCards().getHandDeck().getHandDeck()) {
            cardNames.add(card.getCardName());
        }

        CardName[] handCards = new CardName[cardNames.size()];
        handCards = cardNames.toArray(handCards);

        for (ServerConnectedClient serverConnectedClient : GameHandler.getInstance().getPlayers()) {
            if (serverConnectedClient.getConnectedPlayer().getClientID() == player.getClientID()) {
                serverConnectedClient.safeWriteInOwnOutStream(new YourCards(handCards));
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
