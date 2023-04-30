package game.servergame;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.boardelements.BoardElement;
import game.data.boardelements.Laser;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the activation of BoardElement Laser
 *
 * @author Simon Hümmer
 */
public record LaserActivation(BoardElementInteraction boardElementInteraction) {

    /**
     * The player takes damage equal to the amount of Lasers
     */
    public void activateLaser(BoardElement boardElement, ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Laser for player: " + player.getName());

        Laser laser = (Laser) boardElement;

        int count = laser.getCount();

        boardElementInteraction.takeDamage(count, player);
    }
}