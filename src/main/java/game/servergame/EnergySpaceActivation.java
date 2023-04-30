package game.servergame;

import communication.protocol_v2v1.sendobjects.actions_events_effects.Energy;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.boardelements.BoardElement;
import game.data.boardelements.EnergySpace;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the activation of the BoardElement EnergySpace
 *
 * @author Simon Hümmer
 */
public class EnergySpaceActivation {

    public EnergySpaceActivation() {
    }

    /**
     * Gives the player an Energy Cube if the EnergySpace is full or if it is the end of a round
     */
    public void activateEnergySpace(BoardElement boardElement, ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating EnergySpace for player: " + player.getName());

        EnergySpace energySpace = (EnergySpace) boardElement;

        if (energySpace.getCount() == 1 || player.getPlayerCards().getRegisterDeck().getRegisterDeck()[4].getCardName().equals(CardName.Null)) {
            player.setEnergy(player.getEnergy() + 1);
            if (energySpace.getCount() != 0) {
                energySpace.setCount(energySpace.getCount() - 1);
            }
            Logger.tag(LoggingTags.game.toString()).info(player.getName() + " Energy: " + player.getEnergy());
            GameHandler.getInstance().sendToAllPlayers(new Energy(player.getClientID(), player.getEnergy(), "EnergySpace"));
        }
    }
}