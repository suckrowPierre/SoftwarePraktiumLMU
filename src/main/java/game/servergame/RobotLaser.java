package game.servergame;

import communication.protocol_v2v1.sendobjects.actions_events_effects.LaserAnimation;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.GameMap;
import game.data.Orientations;
import game.data.Position;
import game.data.Robot;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.boardelements.PushPanel;
import game.data.boardelements.Wall;

import java.util.ArrayList;

/**
 * This class implements the activation of the RobotLaser
 *
 * @author Pierre, Simon Hümmer
 */
public record RobotLaser(BoardElementInteraction boardElementInteraction) {

    /**
     * The player who gets shot takes 1 damage card
     */
    public void activateRobotLaser(GameMap map) {
        for (ShootingRobots shootingRobots : getShootingRobots(map)) {
            GameHandler.getInstance().sendToAllPlayers(new LaserAnimation(shootingRobots.shooter().getRobot().getPosition(), shootingRobots.target().getRobot().getPosition(), shootingRobots.shooter().getRobot().getLineOfSight()));
            boardElementInteraction.takeDamage(1, shootingRobots.getTarget());
        }
    }

    /**
     * Makes a list of all robots who get shot
     */
    private ArrayList<ShootingRobots> getShootingRobots(GameMap map) {
        ArrayList<ShootingRobots> shootingRobots = new ArrayList<>();
        for (ServerConnectedPlayer shooter : boardElementInteraction.getPlayer()) {
            for (ServerConnectedPlayer target : boardElementInteraction.getPlayer()) {
                if (shooter.getClientID() != target.getClientID() && checkInShootingRange(shooter.getRobot(), target.getRobot(), map)) {
                    shootingRobots.add(new ShootingRobots(shooter, target));
                }
            }
        }
        return shootingRobots;
    }

    /**
     * Checks if a target stands in range and checks if the player has the Card RearLaser in its hand
     *
     * @return true, if a robots can be shot
     */
    private boolean checkInShootingRange(Robot shooter, Robot target, GameMap map) {
        int xPositionShooter = shooter.getPosition().getX();
        int yPositionShooter = shooter.getPosition().getY();
        int xPositionTarget = target.getPosition().getX();
        int yPositionTarget = target.getPosition().getY();
        switch (shooter.getLineOfSight()) {
            case top -> {
                if (xPositionShooter == xPositionTarget) {
                    if ((checkTop(shooter.getPosition(), target.getPosition()) && checkBoardElementsY(map, yPositionTarget, yPositionShooter, xPositionShooter))
                            || (shooter.isRearLaser() && checkBottom(shooter.getPosition(), target.getPosition()) && checkBoardElementsY(map, yPositionShooter, yPositionTarget, xPositionShooter))) {
                        return true;
                    }
                }

            }
            case bottom -> {
                if (xPositionShooter == xPositionTarget) {
                    if ((checkBottom(shooter.getPosition(), target.getPosition()) && checkBoardElementsY(map, yPositionShooter, yPositionTarget, xPositionShooter))
                            || (shooter.isRearLaser() && checkTop(shooter.getPosition(), target.getPosition()) && checkBoardElementsY(map, yPositionTarget, yPositionShooter, xPositionShooter))) {
                        return true;
                    }
                }
            }
            case right -> {
                if (yPositionShooter == yPositionTarget) {
                    if ((checkRight(shooter.getPosition(), target.getPosition()) && checkBoardElementsX(map, xPositionTarget, xPositionShooter, yPositionShooter))
                            || (shooter.isRearLaser() && checkLeft(shooter.getPosition(), target.getPosition()) && checkBoardElementsX(map, xPositionShooter, xPositionTarget, yPositionShooter))) {
                        return true;
                    }
                }
            }
            case left -> {
                if (yPositionShooter == yPositionTarget) {
                    if ((checkLeft(shooter.getPosition(), target.getPosition()) && checkBoardElementsX(map, xPositionShooter, xPositionTarget, yPositionShooter))
                            || (shooter.isRearLaser() && checkRight(shooter.getPosition(), target.getPosition()) && checkBoardElementsX(map, xPositionTarget, xPositionShooter, yPositionShooter))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkRight(Position shooter, Position target) {
        return shooter.getX() > target.getX();
    }

    private boolean checkLeft(Position shooter, Position target) {
        return shooter.getX() < target.getX();
    }

    private boolean checkTop(Position shooter, Position target) {
        return shooter.getY() > target.getY();
    }

    private boolean checkBottom(Position shooter, Position target) {
        return shooter.getY() < target.getY();
    }

    /**
     * Checks if there is a BoardElement in the way of Laser on the y-axis
     */
    private boolean checkBoardElementsY(GameMap map, int start, int end, int position) {
        for (int y = start; y < end + 1; y++) {
            for (BoardElement boardElement : map.getMap().get(position).get(y)) {
                if (boardElement.getType().equals(BoardElementTypes.Wall)) {
                    Wall wall = (Wall) boardElement;
                    Orientations[] orientations = wall.getOrientations();
                    for (Orientations orientation : orientations) {
                        if (orientation.equals(Orientations.top) || orientation.equals(Orientations.bottom)) {
                            return false;
                        }
                    }
                } else if (boardElement.getType().equals(BoardElementTypes.PushPanel)) {
                    PushPanel pushPanel = (PushPanel) boardElement;
                    Orientations[] orientations = pushPanel.getOrientations();
                    for (Orientations orientation : orientations) {
                        if (orientation.equals(Orientations.top) || orientation.equals(Orientations.bottom)) {
                            return false;
                        }
                    }
                } else if (boardElement.getType().equals(BoardElementTypes.Antenna)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there is a BoardElement in the way of Laser on the x-axis
     */
    private boolean checkBoardElementsX(GameMap map, int start, int end, int position) {
        for (int y = start; y < end + 1; y++) {
            for (BoardElement boardElement : map.getMap().get(y).get(position)) {
                if (boardElement.getType().equals(BoardElementTypes.Wall)) {
                    Wall wall = (Wall) boardElement;
                    Orientations[] orientations = wall.getOrientations();
                    for (Orientations orientation : orientations) {
                        if (orientation.equals(Orientations.right) || orientation.equals(Orientations.left)) {
                            return false;
                        }
                    }
                } else if (boardElement.getType().equals(BoardElementTypes.PushPanel)) {
                    PushPanel pushPanel = (PushPanel) boardElement;
                    Orientations[] orientations = pushPanel.getOrientations();
                    for (Orientations orientation : orientations) {
                        if (orientation.equals(Orientations.right) || orientation.equals(Orientations.left)) {
                            return false;
                        }
                    }
                } else if (boardElement.getType().equals(BoardElementTypes.Antenna)) {
                    return false;
                }
            }
        }
        return true;
    }
}