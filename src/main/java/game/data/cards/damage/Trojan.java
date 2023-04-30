package game.data.cards.damage;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.servergame.BoardElementInteraction;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the Trojan card.
 *
 * @author Simon Hümmer
 */
public class Trojan extends Card {

    public Trojan() {
        cardName = CardName.Trojan;
        cardDescription = "";
    }

    /**
     * This makes the player take two Spam cards and play the top card of his programming deck.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'Trojan' for player: " + player.getName());

        BoardElementInteraction boardElementInteraction = new BoardElementInteraction();

        boardElementInteraction.getTrojanDeck().getDeck().add(this);

        Card topCard = player.getPlayerCards().getProgrammingDeck().getTopCard();
        player.getPlayerCards().getProgrammingDeck().removeTopCard(player.getPlayerCards().getProgrammingDeck().getDeck());

        topCard.activateCard(player);

        for (int i = 0; i < 2; i++) {
            Card spamCard = boardElementInteraction.getSpamDeck().getTopCard();
            player.getPlayerCards().getDiscardDeck().getDeck().add(spamCard);
            boardElementInteraction.getSpamDeck().getDeck().remove(spamCard);
        }
    }

    @Override
    public boolean isDamageCard() {
        return true;
    }
}
