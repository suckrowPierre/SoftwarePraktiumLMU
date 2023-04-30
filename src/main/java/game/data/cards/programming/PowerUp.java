package game.data.cards.programming;

import communication.protocol_v2v1.sendobjects.actions_events_effects.Energy;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the PowerUp card.
 *
 * @author Simon Hümmer
 */
public class PowerUp extends Card {

    public PowerUp() {
        cardName = CardName.PowerUp;
        cardDescription = "Take one energy cube and place it on your player mat.";
    }

    /**
     * This will give the robot an Energy cube.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'PowerUp' for player: " + player.getName());

        int currentEnergy = player.getEnergy();
        player.setEnergy(currentEnergy + 1);

        Logger.tag(LoggingTags.game.toString()).info("Player Energy: " + player.getEnergy());

        GameHandler.getInstance().sendToAllPlayers(new Energy(player.getClientID(), player.getEnergy(), "PowerUp Card"));
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
