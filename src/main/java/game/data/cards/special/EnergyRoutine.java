package game.data.cards.special;

import communication.protocol_v2v1.sendobjects.actions_events_effects.Energy;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the EnergyRoutine Card.
 *
 * @author Simon Hümmer
 */
public class EnergyRoutine extends Card {

    public EnergyRoutine() {
        cardName = CardName.EnergyRoutine;
        cardDescription = "Take one energy cube, and place it on your player mat.";
    }

    /**
     * This will give the player one energy cube.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'EnergyRoutine' for player: " + player.getName());

        int currentEnergy = player.getEnergy();
        player.setEnergy(currentEnergy + 1);

        Logger.tag(LoggingTags.game.toString()).info("Player Energy: " + player.getEnergy());

        GameHandler.getInstance().sendToAllPlayers(new Energy(player.getClientID(), player.getEnergy(), "EnergyRoutine Card"));
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
