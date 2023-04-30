package game.servergame;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.Orientations;
import game.data.boardelements.BoardElement;
import game.data.boardelements.Gear;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the activation of the BoardElement Gear
 *
 * @author Simon Hümmer
 */
public record GearActivation(BoardElementInteraction boardElementInteraction) {

    /**
     * Rotates the Player in the direction of the BoardElement
     */
    public void activateGear(BoardElement boardElement, ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Gear for player: " + player.getName());

        Gear gear = (Gear) boardElement;

        Orientations lineOfSight = player.getRobot().getLineOfSight();

        if (gear.getOrientations()[0].equals(Orientations.clockwise)) {
            boardElementInteraction.rotateClockwise(lineOfSight, player);
            Logger.tag(LoggingTags.game.toString()).info("New lineOfSight: " + player.getRobot().getLineOfSight());
        }
        if (gear.getOrientations()[0].equals(Orientations.counterclockwise)) {
            boardElementInteraction.rotateCounterclockwise(lineOfSight, player);
            Logger.tag(LoggingTags.game.toString()).info("New lineOfSight: " + player.getRobot().getLineOfSight());
        }
    }
}