package game.servergame;

import communication.protocol_v2v1.sendobjects.actions_events_effects.Movement;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.GameMap;
import game.data.Orientations;
import game.data.Position;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.boardelements.ConveyorBelt;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the activation of the BoardElement ConveyorBelt
 *
 * @author Simon Hümmer
 */
public record ConveyorBeltActivation(BoardElementInteraction boardElementInteraction) {

    /**
     * Checks if the ConveyorBelt is a green one or a blue one
     * Checks if the player would get pushed out of the map or into a hole
     * Checks if on the position the robot would end, another robot is standing
     * Moves the Robot in the Direction of the ConveyorBelt
     */
    public void activateConveyorBelt(BoardElement boardElement, ServerConnectedPlayer player, GameMap map) {
        Logger.tag(LoggingTags.game.toString()).info("Activating ConveyorBelt for player: " + player.getName());

        ConveyorBelt oldPositionConveyorBelt = (ConveyorBelt) boardElement;

        int xPosition = player.getRobot().getPosition().getX();
        int yPosition = player.getRobot().getPosition().getY();
        int speed = oldPositionConveyorBelt.getSpeed();
        Orientations moveDirection = oldPositionConveyorBelt.getOrientations()[0];
        Position newPosition;

        if (speed == 1) {
            switch (moveDirection) {
                case top -> {
                    newPosition = new Position(xPosition, yPosition - 1);
                    if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                        if (checkNewPositionPlayer(newPosition, map)) {
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                }
                case bottom -> {
                    newPosition = new Position(xPosition, yPosition + 1);
                    if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                        if (checkNewPositionPlayer(newPosition, map)) {
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                }
                case left -> {
                    newPosition = new Position(xPosition - 1, yPosition);
                    if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                        if (checkNewPositionPlayer(newPosition, map)) {
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                }
                case right -> {
                    newPosition = new Position(xPosition + 1, yPosition);
                    if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                        if (checkNewPositionPlayer(newPosition, map)) {
                            player.getRobot().setPosition(newPosition);
                        }
                    }
                }
            }
        }
        if (speed == 2) {
            switch (moveDirection) {
                case top -> {
                    for (int i = 0; i < speed; i++) {
                        newPosition = new Position(xPosition, yPosition - 1);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            if (checkNewPositionPlayer(newPosition, map) || checkNewPositionBoardElement(newPosition, map)) {
                                player.getRobot().setPosition(newPosition);
                                yPosition--;
                            }
                        }
                    }
                }
                case bottom -> {
                    for (int i = 0; i < speed; i++) {
                        newPosition = new Position(xPosition, yPosition + 1);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            if (checkNewPositionPlayer(newPosition, map) || checkNewPositionBoardElement(newPosition, map)) {
                                player.getRobot().setPosition(newPosition);
                                yPosition++;
                            }
                        }
                    }
                }
                case left -> {
                    for (int i = 0; i < speed; i++) {
                        newPosition = new Position(xPosition - 1, yPosition);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            if (checkNewPositionPlayer(newPosition, map) || checkNewPositionBoardElement(newPosition, map)) {
                                player.getRobot().setPosition(newPosition);
                                xPosition--;
                            }
                        }
                    }
                }
                case right -> {
                    for (int i = 0; i < speed; i++) {
                        newPosition = new Position(xPosition + 1, yPosition);
                        if (!boardElementInteraction.fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && boardElementInteraction.checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            if (checkNewPositionPlayer(newPosition, map) || checkNewPositionBoardElement(newPosition, map)) {
                                player.getRobot().setPosition(newPosition);
                                xPosition++;
                            }
                        }
                    }
                }
            }
        }

        Logger.tag(LoggingTags.game.toString()).info("New Robot Position: ( " + player.getRobot().getPosition().getX() + " | " + player.getRobot().getPosition().getY() + " )");
        GameHandler.getInstance().sendToAllPlayers(new Movement(player.getClientID(), player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY()));

        activateRotatingBelt(player, map, oldPositionConveyorBelt);
    }

    /**
     * If the player gets pushed onto a ConveyorBelt with at least 3 Orientations,
     * he gets turned in the direction of the curved arrow on the ConveyorBelt
     */
    private void activateRotatingBelt(ServerConnectedPlayer player, GameMap map, ConveyorBelt oldPositionConveyorBelt) {
        for (BoardElement boardElement1 : map.getMap().get(player.getRobot().getPosition().getX()).get(player.getRobot().getPosition().getY())) {
            if (boardElement1.getType().equals(BoardElementTypes.ConveyorBelt)) {
                ConveyorBelt newPositionConveyorBelt = (ConveyorBelt) boardElement1;
                if (newPositionConveyorBelt.getOrientations().length > 2) {
                    Orientations flow = newPositionConveyorBelt.getOrientations()[0];
                    if (!flow.equals(oldPositionConveyorBelt.getOrientations()[0])) {
                        Orientations oppositeOrientation = null;
                        switch (oldPositionConveyorBelt.getOrientations()[0]) {
                            case top -> oppositeOrientation = Orientations.bottom;
                            case bottom -> oppositeOrientation = Orientations.top;
                            case right -> oppositeOrientation = Orientations.left;
                            case left -> oppositeOrientation = Orientations.right;
                        }
                        if (newPositionConveyorBelt.getOrientations()[1].equals(oppositeOrientation)) {
                            switch (flow) {
                                case top -> {
                                    switch (newPositionConveyorBelt.getOrientations()[1]) {
                                        case right -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                        case left -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                                case bottom -> {
                                    switch (newPositionConveyorBelt.getOrientations()[1]) {
                                        case right -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                        case left -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                                case right -> {
                                    switch (newPositionConveyorBelt.getOrientations()[1]) {
                                        case top -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                        case bottom -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                                case left -> {
                                    switch (newPositionConveyorBelt.getOrientations()[1]) {
                                        case top -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                        case bottom -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                            }
                        } else if (newPositionConveyorBelt.getOrientations()[2].equals(oppositeOrientation)) {
                            switch (flow) {
                                case top -> {
                                    switch (newPositionConveyorBelt.getOrientations()[2]) {
                                        case right -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                        case left -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                                case bottom -> {
                                    switch (newPositionConveyorBelt.getOrientations()[2]) {
                                        case right -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                        case left -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                                case right -> {
                                    switch (newPositionConveyorBelt.getOrientations()[2]) {
                                        case top -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                        case bottom -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                                case left -> {
                                    switch (newPositionConveyorBelt.getOrientations()[2]) {
                                        case top -> boardElementInteraction.rotateCounterclockwise(player.getRobot().getLineOfSight(), player);
                                        case bottom -> boardElementInteraction.rotateClockwise(player.getRobot().getLineOfSight(), player);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkNewPositionBoardElement(Position position, GameMap map) {
        for (BoardElement boardElement : map.getMap().get(position.getY()).get(position.getY())) {
            if (boardElement.getType() != BoardElementTypes.ConveyorBelt) {
                return false;
            }
        }
        return true;
    }

    private boolean checkNewPositionPlayer(Position position, GameMap map) {
        if (checkPlayer(position)) {
            for (BoardElement boardElement : map.getMap().get(position.getY()).get(position.getY())) {
                if (boardElement.getType() != BoardElementTypes.ConveyorBelt) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkPlayer(Position position) {
        for (ServerConnectedPlayer player : boardElementInteraction.getPlayer()) {
            if (player.getRobot().getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
}