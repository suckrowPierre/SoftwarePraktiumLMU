package game.data.cards.programming;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.Orientations;
import game.data.Parameter;
import game.data.Position;
import game.data.Robot;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the BackUp card.
 *
 * @author Simon Hümmer
 */
public class BackUp extends Card {

    public BackUp() {
        cardName = CardName.BackUp;
        cardDescription = "Move your robot one space back. The robot does not change the direction it is facing. ";
    }

    /**
     * This will move the robot one step back.
     * The Robot still faces the same direction.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'BackUp' for player: " + player.getName());

        Robot robot = player.getRobot();
        Orientations lineOfSight = player.getRobot().getLineOfSight();
        Position oldPosition = player.getRobot().getPosition();
        Position newPosition;

        int xPosition = player.getRobot().getPosition().getX();
        int yPosition = player.getRobot().getPosition().getY();

        switch (lineOfSight) {
            case top -> {
                for (int i = 0; i < Parameter.BACKUP_STEPS_AMOUNT; i++) {
                    newPosition = new Position(xPosition, yPosition + 1);
                    if (isValidMove(oldPosition, newPosition, Orientations.bottom, Orientations.top, player)) {
                        robot.setPosition(newPosition);
                        yPosition++;
                    }
                }
            }
            case bottom -> {
                for (int i = 0; i < Parameter.BACKUP_STEPS_AMOUNT; i++) {
                    newPosition = new Position(xPosition, yPosition - 1);
                    if (isValidMove(oldPosition, newPosition, Orientations.top, Orientations.bottom, player)) {
                        robot.setPosition(newPosition);
                        yPosition--;
                    }
                }
            }
            case right -> {
                for (int i = 0; i < Parameter.BACKUP_STEPS_AMOUNT; i++) {
                    newPosition = new Position(xPosition - 1, yPosition);
                    if (isValidMove(oldPosition, newPosition, Orientations.left, Orientations.right, player)) {
                        robot.setPosition(newPosition);
                        xPosition++;
                    }
                }
            }
            case left -> {
                for (int i = 0; i < Parameter.BACKUP_STEPS_AMOUNT; i++) {
                    newPosition = new Position(xPosition + 1, yPosition);
                    if (isValidMove(oldPosition, newPosition, Orientations.right, Orientations.left, player)) {
                        robot.setPosition(newPosition);
                        xPosition--;
                    }
                }
            }
        }

        Logger.tag(LoggingTags.game.toString()).info("New Robot Position: ( " + robot.getPosition().getX() + " | " + robot.getPosition().getY() + " )");
        sendNewRobotMovement(player.getClientID(), robot.getPosition());
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
