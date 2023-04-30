package game.data.cards.special;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the SpamFolder Card
 *
 * @author Simon Hümmer
 */
public class SpamFolder extends Card {

    public SpamFolder() {
        cardName = CardName.SpamFolder;
        cardDescription = "";
    }

    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'SpamFolder' for player: " + player.getName());

        //TODO: Permanently discard one
        // SPAM damage card from
        // your discard pile to the
        // SPAM damage card
        // draw pile.
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
