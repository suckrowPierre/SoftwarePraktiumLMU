package game.data.cards;

import communication.protocol_v2v1.sendobjects.actions_events_effects.Movement;
import communication.protocol_v2v1.sendobjects.actions_events_effects.PlayerTurning;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.*;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.boardelements.PushPanel;
import game.data.boardelements.Wall;
import game.servergame.BoardElementInteraction;

/**
 * This class defines what a card shall be in the game.
 * There are more specific cards defined in the classes that inherit from Card.
 *
 * @author Simon Hümmer
 */
public abstract class Card {

    public CardName cardName;
    public String cardDescription;
    public int cost;

    public Card() {
    }

    /**
     * This is the method that is called, when the Card's effect is activated.
     * It will be overwritten in each subclass.
     */
    public abstract void activateCard(ServerConnectedPlayer player);

    public boolean isDamageCard() {
        return false;
    }

    public boolean isUpgradeCard() {
        return false;
    }

    /**
     * This method checks if there is an Antenna, a Wall or a PushPanel in the robots way.
     *
     * @param oldPosition         player position before moving
     * @param newPosition         player position after moving
     * @param ownOrientation      players lineOfSight
     * @param oppositeOrientation opposite players lineOfSight
     * @return true if the robot is allowed to move
     */
    public boolean isValidMove(Position oldPosition,
                               Position newPosition,
                               Orientations ownOrientation,
                               Orientations oppositeOrientation,
                               ServerConnectedPlayer player) {

        int oldPositionX = oldPosition.getX();
        int oldPositionY = oldPosition.getY();
        int newPositionX = newPosition.getX();
        int newPositionY = newPosition.getY();

        GameMap map = GameHandler.getInstance().getMap();
        BoardElementInteraction boardElementInteraction = new BoardElementInteraction();

        if (boardElementInteraction.fellOffMap(player, map, newPositionX, newPositionY)) {
            return false;
        }

        for (BoardElement boardElement : map.getMap().get(oldPositionX).get(oldPositionY)) {
            if (boardElement.getType().equals(BoardElementTypes.Wall)) {
                Wall wall = (Wall) boardElement;
                Orientations[] currentFieldWallOrientations = wall.getOrientations();
                for (Orientations wallOrientations : currentFieldWallOrientations) {
                    if (wallOrientations.equals(ownOrientation)) {
                        return false;
                    }
                }
            } else if (boardElement.getType().equals(BoardElementTypes.PushPanel)) {
                PushPanel pushPanel = (PushPanel) boardElement;
                Orientations[] currentFieldPushPanelOrientations = pushPanel.getOrientations();
                for (Orientations pushPanelOrientations : currentFieldPushPanelOrientations) {
                    if (pushPanelOrientations.equals(oppositeOrientation)) {
                        return false;
                    }
                }
            }
        }

        for (BoardElement boardElement : map.getMap().get(newPositionX).get(newPositionY)) {
            if (boardElement.getType().equals(BoardElementTypes.Wall)) {
                Wall wall = (Wall) boardElement;
                Orientations[] nextFieldWallOrientations = wall.getOrientations();
                for (Orientations wallOrientations : nextFieldWallOrientations) {
                    if (wallOrientations.equals(oppositeOrientation)) {
                        return false;
                    }
                }
            } else if (boardElement.getType().equals(BoardElementTypes.PushPanel)) {
                PushPanel pushPanel = (PushPanel) boardElement;
                Orientations[] nextFieldPushPanelOrientations = pushPanel.getOrientations();
                for (Orientations pushPanelOrientations : nextFieldPushPanelOrientations) {
                    if (pushPanelOrientations.equals(ownOrientation)) {
                        return false;
                    }
                }
            } else if (boardElement.getType().equals(BoardElementTypes.Antenna)) {
                return false;
            }
        }

        return pushRobot(boardElementInteraction, newPosition, ownOrientation, oppositeOrientation);
    }

    /**
     * This method checks if a robot is in a way of another robot and moves the other robot if so
     *
     * @param oldPosition         player new position
     * @param ownOrientation      player line of sight
     * @param oppositeOrientation opposite player line of sight
     * @return true, if the robot whose turn it is can move without collision
     */
    private boolean pushRobot(BoardElementInteraction boardElementInteraction,
                              Position oldPosition,
                              Orientations ownOrientation,
                              Orientations oppositeOrientation) {

        int oldPositionX = oldPosition.getX();
        int oldPositionY = oldPosition.getY();

        for (ServerConnectedPlayer player : boardElementInteraction.getPlayer()) {
            Robot robot = player.getRobot();

            if (robot.getPosition().equals(oldPosition)) {
                Position robotNewPosition;
                switch (ownOrientation) {
                    case top -> {
                        robotNewPosition = new Position(oldPositionX, oldPositionY - 1);
                        if (isValidMove(oldPosition, robotNewPosition, ownOrientation, oppositeOrientation, player)) {
                            robot.setPosition(robotNewPosition);
                            sendNewRobotMovement(player.getClientID(), robotNewPosition);
                        } else {
                            return false;
                        }
                    }
                    case bottom -> {
                        robotNewPosition = new Position(oldPositionX, oldPositionY + 1);
                        if (isValidMove(oldPosition, robotNewPosition, ownOrientation, oppositeOrientation, player)) {
                            robot.setPosition(robotNewPosition);
                            sendNewRobotMovement(player.getClientID(), robotNewPosition);
                        } else {
                            return false;
                        }
                    }
                    case right -> {
                        robotNewPosition = new Position(oldPositionX + 1, oldPositionY);
                        if (isValidMove(oldPosition, robotNewPosition, ownOrientation, oppositeOrientation, player)) {
                            robot.setPosition(robotNewPosition);
                            sendNewRobotMovement(player.getClientID(), robotNewPosition);
                        } else {
                            return false;
                        }
                    }
                    case left -> {
                        robotNewPosition = new Position(oldPositionX - 1, oldPositionY);
                        if (isValidMove(oldPosition, robotNewPosition, ownOrientation, oppositeOrientation, player)) {
                            robot.setPosition(robotNewPosition);
                            sendNewRobotMovement(player.getClientID(), robotNewPosition);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public CardName getCardName() {
        return cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public int getCost() {
        return cost;
    }

    protected void sendNewRobotMovement(int clientID, Position position) {
        GameHandler.getInstance().sendToAllPlayers(new Movement(clientID, position.getX(), position.getY()));
    }


    protected void sendNewRobotTurning(int clientID, Rotations rotation) {
        GameHandler.getInstance().sendToAllPlayers(new PlayerTurning(clientID, rotation));
    }
}
