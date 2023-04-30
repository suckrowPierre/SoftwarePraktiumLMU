package game.servergame;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.boardelements.BoardElement;
import game.data.boardelements.LaserRay;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the activation of the BoardElement Laser
 *
 * @author Simon Hümmer
 */
public record LaserRayActivation(BoardElementInteraction boardElementInteraction) {

    /**
     * The player takes damage equal to the amount of Lasers
     */
    public void activateLaserRay(BoardElement boardElement, ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Laser for player: " + player.getName());

        LaserRay laserRay = (LaserRay) boardElement;

        int count = laserRay.getCount();

        boardElementInteraction.takeDamage(count, player);
    }
}
