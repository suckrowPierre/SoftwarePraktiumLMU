package communication.client.data_handlers.client_recieverhandler;

import communication.client.UserClient;
import communication.client.WritingClientSingelton;
import communication.client.chatgui.*;
//TODO: Why is this not working communication.protocol_v1v0.* ???????
import communication.client.chatgui.boardelements.GUICheckPoint;
import communication.client.chatgui.boardelements.GUIEnergySpace;
import communication.client.chatgui.boardelements.GUIRobotLaser;
import communication.client.data_handlers.gamedata_recieverhandler.UserGameDateRecieverHandler;
import communication.client.lobby.UserLobbyHandler;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.actions_events_effects.*;
import communication.protocol_v2v1.sendobjects.cards.CardPlayed;
import communication.protocol_v2v1.sendobjects.chat.ReceivedChat;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.*;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.StartingPointTaken;
import communication.protocol_v2v1.sendobjects.lobby.*;
import communication.protocol_v2v1.sendobjects.special.ConnectionUpdate;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import communication.protocol_v2v1.sendobjects.actions_events_effects.CheckpointMoved;
import game.data.Maps;
import game.data.cards.CardName;
import javafx.application.Platform;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.Arrays;

/**
 * handles received SendObjects that are same for User
 * @author Simon Naab, Sarah Koch, Pierre-Louis Suckrow
 */
public class UserClientRecieverHandler extends WritingClientRecieverHandler {



    public UserClientRecieverHandler(UserClient client) {
    super(client);
    }

    @Override
    protected void handlePlayerAddedSubMethod() {
        Platform.runLater(() -> LobbyModel.getInstance().setChooserobotsuccessful());
    }

    protected void handleSelectMap(ProtocolSendObject recvSendObject) {
        SelectMap s = (SelectMap) recvSendObject;
        Maps[] availableMaps = s.getAvailableMaps();
        for (Maps map : availableMaps) {
            Platform.runLater(() -> Model.getInstance().addMap(map.toString()));
        }
        Platform.runLater(() -> Model.getInstance().setChooseMap(true));
    }

    protected void handleMapSelected(ProtocolSendObject recvSendObject) {
        MapSelected m = (MapSelected) recvSendObject;
        outputMessage("Map selected: " + m.getMap());
        Platform.runLater(()->Model.getInstance().setMapname(m.getMap().getName()));
    }


    protected void handleGameStarted(ProtocolSendObject recvSendObject) {
        GameStarted gameStarted = (GameStarted) recvSendObject;
        gameDateRecieverHandler = new UserGameDateRecieverHandler(gameStarted.getGameMap());
        gameDateRecieverHandler.setLobby((UserLobbyHandler) client.getLobbyHandler());
    }

    protected void handleReceivedChat(ProtocolSendObject recvSendObject) {
        ReceivedChat receivedChat = (ReceivedChat) recvSendObject;
        String sendername =
                client.getLobbyHandler().getNameByID(receivedChat.getFrom())
                        + " ("
                        + receivedChat.getFrom()
                        + ")";
        if (receivedChat.isPrivate() == false) {
            outputMessage(sendername + ": " + receivedChat.getMessage());
        } else {
            outputMessage("(PM)" + sendername + ": " + receivedChat.getMessage());
        }
    }

    protected void handleErrorCases(ErrorMessage error) {
        String errorMessage = error.getMessage();
        Platform.runLater(() -> LobbyModel.getInstance().setErrormessage("Error: " + errorMessage));

        switch (errorMessage) {
            case "Figure is already taken":
                // New GUI Figure Already Taken
                break;
        }
    }

    protected void handleCardPlayed(ProtocolSendObject recvSendObject){
        CardPlayed cardPlayed = (CardPlayed) recvSendObject;
        String username = client.getLobbyHandler().getNameByID(cardPlayed.getClientID())
                + " ("
                + cardPlayed.getClientID()
                + ")";
        outputMessage(username +  " played card: " + cardPlayed.getCard());
        Platform.runLater(() -> {
            int robotid = client.getLobbyHandler().getByID(cardPlayed.getClientID()).getFigure();
            Robot robot = Model.getInstance().getBoard().getRobot(robotid);
            robot.showTextInTooltip("Last played card: "+cardPlayed.getCard());
        });
        Platform.runLater(()->{
            if(cardPlayed.getClientID()==WritingClientSingelton.getInstance().getClientID() && (
                    cardPlayed.getCard().equals(CardName.MemorySwap)||cardPlayed.getCard().equals(CardName.SpamBlocker))){
                UpgradeModel.getInstance().removeCardfromUpgrades(cardPlayed.getCard().toString());
            }
        });
        
    }

    protected void handleNotYourCards(ProtocolSendObject recvSendObject) {
        NotYourCards notYourCards = (NotYourCards) recvSendObject;
        String username = client.getLobbyHandler().getNameByID(notYourCards.getClientID())
                + " ("
                + notYourCards.getClientID()
                + ")";
        outputMessage(username + " got " + notYourCards.getCardsInHand() + " cards in their hand");
    }

    protected void handleCardSelected(ProtocolSendObject recvSendObject) {
        CardSelected cardSelected = (CardSelected) recvSendObject;
        String username = client.getLobbyHandler().getNameByID(cardSelected.getClientID())
                + " ("
                + cardSelected.getClientID()
                + ")";
        if (cardSelected.getFilled()) {
            outputMessage(username + " filled card into slot " + cardSelected.getRegister());
        }
    }

    protected void handleSelectionFinished(ProtocolSendObject recvSendObject) {
        SelectionFinished selectionFinished = (SelectionFinished) recvSendObject;
        String username = client.getLobbyHandler().getNameByID(selectionFinished.getClientID())
                + " ("
                + selectionFinished.getClientID()
                + ")";
        outputMessage(username + " finished their selection");
    }

    protected void handleTimerEnded(ProtocolSendObject recvSendObject) {
        TimerEnded timerEnded = (TimerEnded) recvSendObject;
        Platform.runLater(()-> ProgrammingBoardModel.getInstance().setTimerRunning(false));
        if(timerEnded.getClientIDs().length >=1) {
            outputMessage("Players: " + Arrays.toString(timerEnded.getClientIDs()) + " didn't finish their selection");
        }
    }

    protected void handleCheckPointReached(ProtocolSendObject recvSendObject){
        CheckPointReached cpr = (CheckPointReached) recvSendObject;
        int clientid = cpr.getClientID();
        Platform.runLater(()-> {
            Model.getInstance().reachCheckpoint(clientid, client.getLobbyHandler().getNameByID(clientid), cpr.getNumber());
        });

    }

    protected void handleStartingPointTaken(ProtocolSendObject recvSendObject) {
        super.handleStartingPointTaken(recvSendObject);
        //TODO: this in GameDataRecieverHandler
        StartingPointTaken startingPointTaken = (StartingPointTaken) recvSendObject;
        Platform.runLater(() -> {
            BoardGUI board = Model.getInstance().getBoard();
            if (startingPointTaken.getClientID() == WritingClientSingelton.getInstance().getClientID()){
                board.allowToSetStartingPoint(false);
            }
        });

    }


    protected void handleEnergy(ProtocolSendObject recvSendObject){
        Energy energy = (Energy) recvSendObject;
        if(energy.getClientID() == WritingClientSingelton.getInstance().getClientID()){
            Platform.runLater(() -> UpgradeModel.getInstance().setEnergypoints(energy.getCount()));
        }
        Platform.runLater(() -> {
            int robotid = client.getLobbyHandler().getByID(energy.getClientID()).getFigure();
            Robot robot = Model.getInstance().getBoard().getRobot(robotid);
            robot.showEnergyPointsInTooltip(energy.getCount());
            if(energy.getSource().equals("EnergySpace")) {
                GUIEnergySpace es = Model.getInstance().getBoard().getEnergySpace(robot.getxPos(), robot.getyPos());
                es.takeEnergyCube();
            }
        });

    }

    protected void handleCheckpointMoved (ProtocolSendObject recvSendObject){
        CheckpointMoved checkpointMoved = (CheckpointMoved) recvSendObject;
        Platform.runLater(() ->{
            GUICheckPoint cp = Model.getInstance().getBoard().getCheckpoint(checkpointMoved.getCheckpointID());
            cp.move(checkpointMoved.getX(), checkpointMoved.getY());
        });
    }

    protected  void handlePickDamage(ProtocolSendObject recvSendobject){
        PickDamage pickdamage = (PickDamage) recvSendobject;
        Platform.runLater(()->{
            Model.getInstance().setChoosedamage(true, pickdamage.getCount() , pickdamage.getAvailablePiles());
        });
    }

    protected  void handleDrawDamage(ProtocolSendObject recvSendObject){
        DrawDamage drawDamage = (DrawDamage) recvSendObject;
        String username = client.getLobbyHandler().getNameByID(drawDamage.getClientID())
                + " ("
                + drawDamage.getClientID()
                + ")";
        outputMessage(username + " got damage: " + Arrays.toString(drawDamage.getCards()) );
    }

    protected void handleLaserAnimation(ProtocolSendObject recvSendObject){
        LaserAnimation la = (LaserAnimation) recvSendObject;
        Platform.runLater(()->{
            //GUIRobotLaser laser = Model.getInstance().getBoard().getRobotLaser(la.getOrientation());
            //laser.shoot(la.getStartLaser(), la.getEndLaser());
        });
    }

    protected void handleConnectionUpdate(ProtocolSendObject recvSendObject) {
        ConnectionUpdate c = (ConnectionUpdate) recvSendObject;
        if (!c.isConnected()) {
            Platform.runLater(()->{
                if (gameDateRecieverHandler != null){
                    int clientid = c.getID();
                    int robotid = gameDateRecieverHandler.getRoboterbyID(clientid);
                    if(Model.getInstance().getBoard() != null && robotid != -1)
                    {
                        Model.getInstance().getBoard().getRobot(robotid).setVisibility(false);
                        Logger.tag(LoggingTags.gui.toString()).debug("Robot "+robotid+" removed");

                    }
                }
                client.getLobbyHandler().removeConnectedClient(c.getID());
            });
        }
    }
}