package game.data.cards.special;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the SandboxRoutine Card
 *
 * @author Simon Hümmer
 */
public class SandboxRoutine extends Card {

    public SandboxRoutine() {
        cardName = CardName.SandboxRoutine;
        cardDescription = "";
    }

    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'SandboxRoutine' for player: " + player.getName());

        //TODO: Choose one of the following
        // actions to perform this register:
        // Move 1, 2, or 3
        // Back Up
        // Turn Left
        // Turn Right
        // U-Turn
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
