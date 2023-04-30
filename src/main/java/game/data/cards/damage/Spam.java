package game.data.cards.damage;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.servergame.BoardElementInteraction;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the Spam card.
 *
 * @author Simon Hümmer
 */
public class Spam extends Card {

    public Spam() {
        cardName = CardName.Spam;
        cardDescription = "";
    }

    /**
     * This makes the player play the top card of his programming deck.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'Spam' for player: " + player.getName());

        BoardElementInteraction boardElementInteraction = new BoardElementInteraction();

        boardElementInteraction.getSpamDeck().getDeck().add(this);

        Card topCard = player.getPlayerCards().getProgrammingDeck().getTopCard();
        player.getPlayerCards().getProgrammingDeck().removeTopCard(player.getPlayerCards().getProgrammingDeck().getDeck());

        topCard.activateCard(player);
    }

    @Override
    public boolean isDamageCard() {
        return true;
    }
}
