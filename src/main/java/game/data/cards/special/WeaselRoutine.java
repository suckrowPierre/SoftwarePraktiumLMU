package game.data.cards.special;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the WeaselRoutine Card
 *
 * @author Simon Hümmer
 */
public class WeaselRoutine extends Card {

    public WeaselRoutine() {
        cardName = CardName.WeaselRoutine;
        cardDescription = "";
    }

    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'WeaselRoutine' for player: " + player.getName());

        //TODO: Choose one of the
        // following actions to
        // perform this register:
        // Turn Left
        // Turn Right
        // U-Turn
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
