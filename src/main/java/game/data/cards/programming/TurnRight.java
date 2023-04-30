package game.data.cards.programming;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.Orientations;
import game.data.Robot;
import game.data.Rotations;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the TurnRight card.
 *
 * @author Simon Hümmer
 */
public class TurnRight extends Card {

    public TurnRight() {
        cardName = CardName.TurnRight;
        cardDescription = "Turn your Robot 90 degrees to the right. The robot remains in its current space";
    }

    /**
     * This will turn the robot 90 degrees to the right.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'TurnRight' for player: " + player.getName());

        Robot robot = player.getRobot();
        Orientations lineOfSight = player.getRobot().getLineOfSight();

        switch (lineOfSight) {
            case top -> robot.setLineOfSight(Orientations.right);
            case bottom -> robot.setLineOfSight(Orientations.left);
            case right -> robot.setLineOfSight(Orientations.bottom);
            case left -> robot.setLineOfSight(Orientations.top);
        }

        Logger.tag(LoggingTags.game.toString()).info("New Line of Sight: " + robot.getLineOfSight());
        sendNewRobotTurning(player.getClientID(), Rotations.clockwise);
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
