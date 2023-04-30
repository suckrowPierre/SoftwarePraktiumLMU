package game.servergame;

import communication.protocol_v2v1.sendobjects.actions_events_effects.Movement;
import communication.protocol_v2v1.sendobjects.actions_events_effects.PlayerTurning;
import communication.protocol_v2v1.sendobjects.actions_events_effects.Reboot;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.GameMap;
import game.data.Orientations;
import game.data.Position;
import game.data.Rotations;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.boardelements.RestartPoint;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the Reboot of the Robot
 *
 * @author Simon Hümmer
 */
public record RebootRobot(BoardElementInteraction boardElementInteraction) {

    /**
     * Reboots the robot and sets the position to the robot to its StartingPoint or to the RestartPoint
     * The Player takes 2 damage cards
     * If the restart position if already occupied the other robots get pushed away in the direction of the RestartPoint
     */
    public void rebootRobot(ServerConnectedPlayer player, GameMap map, int xPosition, int yPosition) {
        Logger.tag(LoggingTags.game.toString()).info("Rebooting Robot");

        boardElementInteraction.takeDamage(2, player);

        RestartPoint restartPoint = searchRestartPoint(map);
        assert restartPoint != null;
        Orientations orientation = restartPoint.getOrientations()[0];

        if (xPosition < 0) {
            for (BoardElement boardElement : map.getMap().get(xPosition + 1).get(yPosition)) {
                if (boardElement.getIsOnBoard().equals("Start A") || boardElement.getIsOnBoard().equals("Start B")) {
                    Position startingPoint = player.getStartingPoint();
                    boardElementInteraction.positionOccupied(startingPoint, map, orientation);
                    player.getRobot().setPosition(startingPoint);
                } else {
                    rebootOnRestartPoint(player, map, orientation);
                }
            }
        } else if (xPosition >= map.getMap().size()) {
            for (BoardElement boardElement : map.getMap().get(xPosition - 1).get(yPosition)) {
                if (boardElement.getIsOnBoard().equals("Start A") || boardElement.getIsOnBoard().equals("Start B")) {
                    Position startingPoint = player.getStartingPoint();
                    boardElementInteraction.positionOccupied(startingPoint, map, orientation);
                    player.getRobot().setPosition(startingPoint);
                } else {
                    rebootOnRestartPoint(player, map, orientation);
                }
            }
        } else if (yPosition < 0) {
            for (BoardElement boardElement : map.getMap().get(xPosition).get(yPosition + 1)) {
                if (boardElement.getIsOnBoard().equals("Start A") || boardElement.getIsOnBoard().equals("Start B")) {
                    Position startingPoint = player.getStartingPoint();
                    boardElementInteraction.positionOccupied(startingPoint, map, orientation);
                    player.getRobot().setPosition(startingPoint);
                } else {
                    rebootOnRestartPoint(player, map, orientation);
                }
            }
        } else if (yPosition >= map.getMap().get(0).size()) {
            for (BoardElement boardElement : map.getMap().get(xPosition).get(yPosition - 1)) {
                if (boardElement.getIsOnBoard().equals("Start A") || boardElement.getIsOnBoard().equals("Start B")) {
                    Position startingPoint = player.getStartingPoint();
                    boardElementInteraction.positionOccupied(startingPoint, map, orientation);
                    player.getRobot().setPosition(startingPoint);
                } else {
                    rebootOnRestartPoint(player, map, orientation);
                }
            }
        } else {
            rebootOnRestartPoint(player, map, orientation);
        }

        sendRebootObjects(player);
    }

    /**
     * Sends all data of the reboot to the client
     */
    private void sendRebootObjects(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("New Robot Position: ( " + player.getRobot().getPosition().getX() + " | " + player.getRobot().getPosition().getY() + " )");

        player.setRebooted(true);
        player.getPlayerCards().clearRegister();

        GameHandler.getInstance().sendToAllPlayers(new Reboot(player.getClientID()));
        GameHandler.getInstance().sendToAllPlayers(new Movement(player.getClientID(), player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY()));

        Orientations orientation = player.getRobot().getLineOfSight();
        while (orientation != Orientations.top) {
            GameHandler.getInstance().sendToAllPlayers(new PlayerTurning(player.getClientID(), Rotations.clockwise));
            switch (orientation) {
                case right -> orientation = Orientations.bottom;
                case bottom -> orientation = Orientations.left;
                case left -> orientation = Orientations.top;
            }
        }
        player.getRobot().setLineOfSight(Orientations.top);

        GameHandler.getInstance().nextCurrentPlayer();
    }

    /**
     * Sets the position of the robot to the position of the RestartPoint
     */
    private void rebootOnRestartPoint(ServerConnectedPlayer player, GameMap map, Orientations orientation) {
        Position restartPosition = searchRestartPointPosition(map);
        boardElementInteraction.positionOccupied(restartPosition, map, orientation);
        player.getRobot().setPosition(restartPosition);
    }

    private RestartPoint searchRestartPoint(GameMap map) {
        for (int x = 0; x < map.getMap().size(); x++) {
            for (int y = 0; y < map.getMap().get(0).size(); y++) {
                for (BoardElement boardElement : map.getMap().get(x).get(y)) {
                    if (boardElement.getType().equals(BoardElementTypes.RestartPoint)) {
                        return (RestartPoint) boardElement;
                    }
                }
            }
        }
        return null;
    }

    private Position searchRestartPointPosition(GameMap map) {
        for (int x = 0; x < map.getMap().size(); x++) {
            for (int y = 0; y < map.getMap().get(0).size(); y++) {
                for (BoardElement boardElement : map.getMap().get(x).get(y)) {
                    if (boardElement.getType().equals(BoardElementTypes.RestartPoint)) {
                        return new Position(x, y);
                    }
                }
            }
        }
        return new Position(0, 0);
    }
}