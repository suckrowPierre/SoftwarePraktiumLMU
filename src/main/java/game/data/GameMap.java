package game.data;


import communication.protocol_v2v1.sendobjects.actions_events_effects.CheckpointMoved;
import communication.server.GameHandler;
import game.data.boardelements.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    List<List<List<BoardElement>>> map;

    public GameMap(List<List<List<BoardElement>>> map) {
        System.out.println("Game map data created");
        this.map = map;
    }

    public List<List<List<BoardElement>>> getMap() {
        return map;
    }


    public ArrayList<BoardElementPlusPosition> getAllBoardElementPlusPositionByType(BoardElementTypes boardElementType) {
        ArrayList<BoardElementPlusPosition> list = new ArrayList<>();
        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.get(x).size(); y++) {
                for (BoardElement boardElement : map.get(x).get(y)) {
                    if (boardElement.getType() == boardElementType) {
                        list.add(new BoardElementPlusPosition(boardElement, new Position(x, y)));
                    }
                }
            }
        }
        return list;
    }

    public void setMissingLasers() {
        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.get(0).size(); y++) {
                for (BoardElement boardElement : map.get(x).get(y)) {
                    if (!(boardElement == null)) {
                        if (boardElement.getType() == BoardElementTypes.Laser) {
                            settingMissingLasers(boardElement, x, y);
                        }
                    }
                }
            }
        }
    }


    private void settingMissingLasers(BoardElement boardElement, int x, int y) {
        Laser laser = (Laser) boardElement;
        Position positionToCheck = new Position(x, y);
        boolean laserStopped = false;
        while (!laserStopped) {
            switch (laser.getOrientations()[0]) {
                case top -> {
                    positionToCheck.setY(positionToCheck.getY() - 1);
                }
                case bottom -> {
                    positionToCheck.setY(positionToCheck.getY() + 1);
                }
                case right -> {
                    positionToCheck.setX(positionToCheck.getX() + 1);
                }
                case left -> {
                    positionToCheck.setX(positionToCheck.getX() - 1);
                }
            }

            for (BoardElement boardElementToCheck : map.get(positionToCheck.getX()).get(positionToCheck.getY())) {
                switch (boardElementToCheck.getType()) {
                    case Wall -> {
                        Wall walltoCheck = (Wall) boardElementToCheck;
                        if (walltoCheck.getOrientations()[0] == laser.getOrientations()[0]) {
                            setFillingLaser(positionToCheck, laser.getCount(), laser.getOrientations(), laser);
                            laserStopped = true;
                        } else if (walltoCheck.getOrientations()[0] == Orientations.getOppositeOrientation(laser.getOrientations()[0])) {
                            laserStopped = true;
                        } else {
                            setFillingLaser(positionToCheck, laser.getCount(), laser.getOrientations(), laser);
                        }
                    }
                    case Antenna -> {
                        laserStopped = true;
                    }
                    case Laser -> {
                        laserStopped = true;
                    }
                    default -> {
                        setFillingLaser(positionToCheck, laser.getCount(), laser.getOrientations(), laser);
                    }
                }

            }
        }
    }


    private void setFillingLaser(Position position, int count, Orientations[] orientation, Laser laser) {
        map.get(position.getX()).get(position.getY()).add(new LaserRay(laser.getIsOnBoard(), laser.getCount(), laser.getOrientations()));
    }


    public void rotateCheckPoints() {
        ArrayList<BoardElementPlusPosition> checkpoints = getAllBoardElementPlusPositionByType(BoardElementTypes.CheckPoint);
        for (BoardElementPlusPosition boardElementPlusPosition : checkpoints) {
            Position position = boardElementPlusPosition.getPosition();
            CheckPoint checkPoint = (CheckPoint) boardElementPlusPosition.getBoardElement();
            rotateCheckpoint(checkPoint, position, 0);
        }
    }


    private void rotateCheckpoint(CheckPoint checkPoint, Position position, int i) {
            for (BoardElement boardElement : map.get(position.getX()).get(position.getY())) {
                if (boardElement.getType() == BoardElementTypes.ConveyorBelt) {
                    ConveyorBelt conveyorBelt = (ConveyorBelt) boardElement;
                    if (conveyorBelt.getSpeed() > i) {
                        Orientations orientation = conveyorBelt.getOrientations()[0];
                        map.get(position.getX()).get(position.getY()).remove(checkPoint);
                        Position newPosition = null;
                        switch (orientation) {
                            case top -> {
                                newPosition = new Position(position.getX(), position.getY() - 1);
                            }
                            case bottom -> {
                                newPosition = new Position(position.getX(), position.getY() + 1);
                            }
                            case right -> {
                                newPosition = new Position(position.getX() + 1, position.getY());
                            }
                            case left -> {
                                newPosition = new Position(position.getX() - 1, position.getY());
                            }
                        }
                        map.get(newPosition.getX()).get(newPosition.getY()).add(checkPoint);
                        GameHandler.getInstance().sendToAllPlayers(new CheckpointMoved(checkPoint.getCount(), newPosition.getX(), newPosition.getY()));
                        rotateCheckpoint(checkPoint, newPosition, i + 1);

                    }
                }
            }

    }
}

