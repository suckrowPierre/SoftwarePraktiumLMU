package communication.client.data_handlers.gamedata_recieverhandler;

import communication.client.data_handlers.DataHandler;
import communication.client.lobby.ConnectedClient;
import communication.client.lobby.LobbyHandler;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.ActiveCard;
import game.clientgame.clientgamehandler.ClientGameHandler;
import game.data.GameMap;
import game.data.Rotations;
import game.data.cards.CardName;

/**
 * To handle update to Game from ClientReceiverHandler
 * @author Pierre-Louis Suckrow
 */
public abstract class GameDateRecieverHandler extends DataHandler {
    ClientGameHandler clientGameHandler;
    LobbyHandler lobby;


    public GameDateRecieverHandler(GameMap map) {
    }

    public void setLobby(LobbyHandler lobby) {
        this.lobby = lobby;
    }

    public void addNewStartingPoint(int clientID, int x, int y){
        clientGameHandler.addStartingPoint(clientID,x,y);
    }

    public void setActivePhase(int phase){
        clientGameHandler.setPhase(phase);
    }

    public void drawCards(CardName[] cardNames){
        clientGameHandler.recieveCards(cardNames);
    }

    public abstract void startTimer();

    public void fillMissingProgrammCards(CardName[] cardNames){
        clientGameHandler.setMissingCards(cardNames);
    }

    public void handleCurrentCards(ActiveCard[] activeCards){
        clientGameHandler.recieveActiveCard(activeCards);
    }

    public abstract void movePlayer(int clientID, int x, int y);

    public abstract void turnPlayer(int clientID, Rotations rotation);

    public int getRoboterbyID(int clientID){
        for(ConnectedClient connectedClient:lobby.getConnectedClients()){
            if(connectedClient.getID() == clientID)
            return connectedClient.getFigure();
        }
        return -1;
    }

    public abstract void replaceCard(int register, CardName cardName, int clientID);

    public void setCurrentPlayer(int clientID){
        clientGameHandler.setCurrentPlayer(clientID);
    }

    public abstract void finishGame(int clientID);

    public void chooseRebootDirection(){
        clientGameHandler.getProgrammingBoard().clearprogrammableCardsInProgrammingDeck();
    };

    public abstract void pickDamage(int count, CardName[] cardNames);

}
