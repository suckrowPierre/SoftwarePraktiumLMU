package communication.client.data_handlers.gamedata_recieverhandler;

import communication.client.WritingClientSingelton;
import communication.client.chatgui.Model;
import communication.client.chatgui.ProgrammingBoardModel;
import communication.client.chatgui.Robot;
import communication.client.chatgui.boardelements.*;
import communication.client.lobby.UserLobbyHandler;
import game.clientgame.clientgamehandler.UserClientGameHandler;
import game.clientgame.clientgamehandler.GameHandlerSingelton;
import game.data.GameMap;
import game.data.Orientations;
import game.data.Position;
import game.data.Rotations;
import game.data.boardelements.*;
import game.data.cards.CardName;
import javafx.application.Platform;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * To handle update to Game from ClientReceiverHandler for User
 * @author Pierre-Louis Suckrow
 */
public class UserGameDateRecieverHandler extends GameDateRecieverHandler {


    public UserGameDateRecieverHandler(GameMap map) {
        super(map);
        GameHandlerSingelton.getInstance().setClientGameHandler(new UserClientGameHandler());
        clientGameHandler = GameHandlerSingelton.getInstance().getClientGameHandler();
        clientGameHandler.setMap(map);
        Platform.runLater(() -> Model.getInstance().setLoadmap(map.getMap().size(), map.getMap().get(0).size()));
        openView();
    }

    public void setLobby(UserLobbyHandler lobby) {
        this.lobby = lobby;
    }

    private void openView() {
        GameMap map = clientGameHandler.getMap();
        loadMap(map);
    }

    private void loadMap(GameMap map) {
        Logger.tag(LoggingTags.game.toString()).info("loadingMap");
        for (int x = 0; x < map.getMap().size(); x++) {
            for (int y = 0; y < map.getMap().get(0).size(); y++) {
                for (BoardElement boardElement : map.getMap().get(x).get(y)) {
                    GUIBoardElement guiBoardElement = getGuiBoardElement(x, y, boardElement);
                    if (!(guiBoardElement == null)) {
                        if (boardElement.getType() == BoardElementTypes.Laser) {
                            settingMissingLasers(boardElement, x, y, map);
                        }
                        Platform.runLater(() -> Model.getInstance().getBoard().setBoardElement(guiBoardElement));
                    }
                }
            }
        }
        Platform.runLater(() -> Model.getInstance().getBoard().finishBoard());

    }

    //TODO: if working refactor this(Typecasting could be done before the switch, try to get rid of the switch complete if possible(maybe not doable because of the different constructors)
    private GUIBoardElement getGuiBoardElement(int x, int y, BoardElement gameBoardElement) {
        switch (gameBoardElement.getType()) {
            case Antenna -> {
                Antenna typecastedGameBoardElement = (Antenna) gameBoardElement;
                return new GUIAntenna(x, y, typecastedGameBoardElement.getOrientations());
            }
            case CheckPoint -> {
                CheckPoint typecastedGameBoardElement = (CheckPoint) gameBoardElement;
                return new GUICheckPoint(x, y, typecastedGameBoardElement.getCount());
            }
            case ConveyorBelt -> {
                ConveyorBelt typecastedGameBoardElement = (ConveyorBelt) gameBoardElement;
                return new GUIConveyorBelt(x, y, typecastedGameBoardElement.getOrientations(), typecastedGameBoardElement.getSpeed());
            }
            case Empty -> {
                //TODO? nichts, wenn du die größe am Anfang beim Laden des Mapfensters korrekt übergeben hast siehe Methode Model.getInstance.setLoadmap(int x, int y)
            }
            case EnergySpace -> {
                EnergySpace typecastedGameBoardElement = (EnergySpace) gameBoardElement;
                return new GUIEnergySpace(x, y, typecastedGameBoardElement.getCount());
            }
            case Laser -> {
                Laser typecastedGameBoardElement = (Laser) gameBoardElement;
                return new GUILaser(x, y, typecastedGameBoardElement.getCount(), typecastedGameBoardElement.getOrientations());
            }
            case RestartPoint -> {
                RestartPoint typecastedGameBoardElement = (RestartPoint) gameBoardElement;
                return new GUIRestartPoint(x, y, typecastedGameBoardElement.getOrientations());
            }
            case StartPoint -> {
                StartPoint typecastedGameBoardElement = (StartPoint) gameBoardElement;
                return new GUIStartPoint(x, y);
            }
            case Wall -> {
                Wall typecastedGameBoardElement = (Wall) gameBoardElement;
                return new GUIWall(x, y, typecastedGameBoardElement.getOrientations());
            }
            case Pit -> {
                Pit typecastedGameBoardElement = (Pit) gameBoardElement;
                return new GUIPit(x, y);
            }
            case Gear -> {
                Gear typecastedGameBoardElement = (Gear) gameBoardElement;
                return new GUIGear(x, y, typecastedGameBoardElement.getOrientations());
            }
            case PushPanel -> {
                PushPanel typecastedGameBoardElement = (PushPanel) gameBoardElement;
                return new GUIPushPanel(x, y, typecastedGameBoardElement.getOrientations(), typecastedGameBoardElement.getRegisters());
            }
        }
        return null;
    }


    public void addNewStartingPoint(int clientID, int x, int y) {
        super.addNewStartingPoint(clientID, x, y);
        int roboid = getRoboterbyID(clientID);
        Platform.runLater(() -> {
            Robot robot = Model.getInstance().getBoard().getRobot(roboid);
            robot.move(x, y);
            robot.setVisibility(true);
        });

    }

    public void startTimer() {
        Platform.runLater(() -> ProgrammingBoardModel.getInstance().setTimerRunning(true));
    }


    public void movePlayer(int clientID, int x, int y) {
        int roboid = getRoboterbyID(clientID);
        Platform.runLater(() -> {
            Robot robot = Model.getInstance().getBoard().getRobot(roboid);
            robot.move(x, y);
        });
    }

    public void turnPlayer(int clientID, Rotations rotation) {
        int roboid = getRoboterbyID(clientID);
        Platform.runLater(() -> {
            Robot robot = Model.getInstance().getBoard().getRobot(roboid);
            robot.turn(rotation);
        });
    }

    public void replaceCard(int register, CardName cardName, int clientID) {
        if (clientID == WritingClientSingelton.getInstance().getClientID()) {
            clientGameHandler.replaceCard(register, cardName);
            //TODO: replaceView
        } else {
            //Bekommen ander das überhaupt mit ???? Sonst kann extends DataHandler in GameDataRecieverHandler gelöscht werden
            outputMessage("Player: " + clientID + " had to replace his Card in Register " + register + " with card " + cardName.toString());
        }

    }

    public void finishGame(int clientID) {
        Platform.runLater(() -> {
            Model.getInstance().setGameFinished(clientID);
        });
    }

    private void settingMissingLasers(BoardElement boardElement, int x, int y, GameMap map) {
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

            for (BoardElement boardElementToCheck : map.getMap().get(positionToCheck.getX()).get(positionToCheck.getY())) {
                switch (boardElementToCheck.getType()) {
                    case Wall -> {
                        Wall walltoCheck = (Wall) boardElementToCheck;
                        if (walltoCheck.getOrientations()[0] == laser.getOrientations()[0]) {
                            setFillingLaser(positionToCheck, laser.getCount(), laser.getOrientations());
                            laserStopped = true;
                        } else if (walltoCheck.getOrientations()[0] == Orientations.getOppositeOrientation(laser.getOrientations()[0])) {
                            laserStopped = true;
                        } else {
                            setFillingLaser(positionToCheck, laser.getCount(), laser.getOrientations());
                        }
                    }
                    case Antenna -> {
                        laserStopped = true;
                    }
                    case Laser -> {
                        laserStopped = true;
                    }
                    default -> {
                        setFillingLaser(positionToCheck, laser.getCount(), laser.getOrientations());
                    }
                }

            }
        }
    }


    private void setFillingLaser(Position position, int count, Orientations[] orientation) {
        GUILaserRay laser = new GUILaserRay(position.getX(), position.getY(), count, orientation);
        Platform.runLater(() -> Model.getInstance().getBoard().setBoardElement(laser));
    }

    public void chooseRebootDirection(){
        super.chooseRebootDirection();
        Platform.runLater(() -> {
            Model.getInstance().setRebooting(true);
            for(int i = 0; i<5; i++){
                ProgrammingBoardModel.getInstance().showCardInView(i, "Empty");
            }
        });
    }

    public void pickDamage(int count, CardName[] cardNames){}
}
