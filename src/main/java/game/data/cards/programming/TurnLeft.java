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
 * This class implements the TurnLeft card.
 */
public class TurnLeft extends Card {

    public TurnLeft() {
        cardName = CardName.TurnLeft;
        cardDescription = "Turn your Robot 90 degrees to the left. The robot remains in its current space";
    }

    /**
     * This will turn the robot 90 degrees to the left.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'TurnLeft' for player: " + player.getName());

        Robot robot = player.getRobot();
        Orientations lineOfSight = player.getRobot().getLineOfSight();

        switch (lineOfSight) {
            case top -> robot.setLineOfSight(Orientations.left);
            case bottom -> robot.setLineOfSight(Orientations.right);
            case right -> robot.setLineOfSight(Orientations.top);
            case left -> robot.setLineOfSight(Orientations.bottom);
        }

        Logger.tag(LoggingTags.game.toString()).info("New Line of Sight: " + robot.getLineOfSight());
        sendNewRobotTurning(player.getClientID(), Rotations.counterclockwise);
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
