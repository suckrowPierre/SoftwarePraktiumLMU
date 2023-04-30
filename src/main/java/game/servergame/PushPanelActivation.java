package game.servergame;

import communication.protocol_v2v1.sendobjects.actions_events_effects.Movement;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.GameMap;
import game.data.Orientations;
import game.data.Position;
import game.data.boardelements.BoardElement;
import game.data.boardelements.PushPanel;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the activation of the BoardElement PushPanel
 *
 * @author Simon Hümmer
 */
public record PushPanelActivation(BoardElementInteraction boardElementInteraction) {

    /**
     * If the current round equals a number of a register from the PushPanel,
     * the Player gets pushed in the direction of the PushPanel
     * If the Robot gets pushed out of the Map or gets pushed into a Pit, it reboots
     * If a robot stands in the way of the pushed robots, it gets pushed too
     */
    public void activatePushPanel(BoardElement boardElement, ServerConnectedPlayer player, GameMap map, int currentRound) {
        Logger.tag(LoggingTags.game.toString()).info("Activating PushPanel for player: " + player.getName());

        PushPanel pushPanel = (PushPanel) boardElement;

        int xPosition = player.getRobot().getPosition().getX();
        int yPosition = player.getRobot().getPosition().getY();
        Position newPosition;

        for (int register : pushPanel.getRegisters()) {
            if (register == currentRound + 1) {
                Orientations orientation = pushPanel.getOrientations()[0];
                switch (orientation) {
                    case top -> {
                        newPosition = new Position(xPosition, yPosition - 1);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            boardElementInteraction.positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                    case bottom -> {
                        newPosition = new Position(xPosition, yPosition + 1);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            boardElementInteraction.positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                    case right -> {
                        newPosition = new Position(xPosition + 1, yPosition);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            boardElementInteraction.positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                    case left -> {
                        newPosition = new Position(xPosition - 1, yPosition);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            boardElementInteraction.positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                }

                Logger.tag(LoggingTags.game.toString()).info("New Robot Position: ( " + player.getRobot().getPosition().getX() + " | " + player.getRobot().getPosition().getY() + " ) ");
                GameHandler.getInstance().sendToAllPlayers(new Movement(player.getClientID(), player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY()));
            }
        }
    }
}