package game.data.cards.programming;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the Null card.
 * This card is just a placeholder and has no own action.
 *
 * @author Simon Hümmer
 */
public class Null extends Card {

    public Null() {
        cardName = CardName.Null;
        cardDescription = "";
    }

    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'Null' for player: " + player.getName());
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
