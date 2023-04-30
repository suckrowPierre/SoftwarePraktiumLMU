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
 * This class implements the UTurn card.
 *
 * @author Simon Hümmer
 */
public class UTurn extends Card {

    public UTurn() {
        cardName = CardName.UTurn;
        cardDescription = "Turn your Robot 180 degrees so it faces the opposite direction." +
                " The robot remains in its current space";
    }

    /**
     * This will turn the robot 180 degrees.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'UTurn' for player: " + player.getName());

        Robot robot = player.getRobot();
        Orientations lineOfSight = player.getRobot().getLineOfSight();

        switch (lineOfSight) {
            case top -> robot.setLineOfSight(Orientations.bottom);
            case bottom -> robot.setLineOfSight(Orientations.top);
            case right -> robot.setLineOfSight(Orientations.left);
            case left -> robot.setLineOfSight(Orientations.right);
        }

        Logger.tag(LoggingTags.game.toString()).info("New Line of Sight: " + robot.getLineOfSight());
        sendNewRobotTurning(player.getClientID(), Rotations.clockwise);
        sendNewRobotTurning(player.getClientID(), Rotations.clockwise);
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
