package communication.server.connectedclient;

import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.lobby.PlayerAdded;
import communication.protocol_v2v1.sendobjects.lobby.PlayerStatus;
import communication.protocol_v2v1.sendobjects.lobby.SelectMap;
import communication.server.GameHandler;
import game.data.Maps;
import org.tinylog.Logger;


/**
 * Singelton for handeling all connected Clients
 * @author Pierre-Louis Suckrow
 */
public class ConnectedClientsHandler {

    private int minPlayer = 2;
    private static ConnectedClientsHandler instance;
   private ConnectedClientList connectedClientList;
   private boolean mapSelected;
   private int firstReadyClient;
   private Maps selectedMap;

    public ConnectedClientsHandler() {
        setup();
    }


    private void setup(){
        connectedClientList = new ConnectedClientList();
        mapSelected = false;
        firstReadyClient = -1;
        selectedMap = null;
    }

    public void reset(){
        for(ServerConnectedClient connectedClient: connectedClientList.getConnectedClients()){
            connectedClient.closeConnection();
        }
        setup();
    }

    public void setMinPlayers(int minPlayers){
        this.minPlayer = minPlayers;
    }

    public static synchronized ConnectedClientsHandler getInstance() {
        if (instance == null) {
            instance = new ConnectedClientsHandler();
        }
        return instance;
    }

    public synchronized void sendToAll(ProtocolSendObject protocolSendObject){
        for(ServerConnectedClient serverConnectedClient:connectedClientList.getConnectedClients()){
                serverConnectedClient.safeWriteInOwnOutStream(protocolSendObject);
        }
    }

    public synchronized int getNewClientID(){
        return connectedClientList.getConnectedClients().size();
    }

    public synchronized ConnectedClientList getConnectedClientList() {
        return connectedClientList;
    }

    public synchronized void tryToLaunchGame(){
        if((connectedClientList.getConnectedClients().size() >= minPlayer) && areAllPlayersReady() && mapSelected){
            Logger.info("launching game");
            GameHandler.getInstance().startGame(selectedMap);
        }
    }

    private boolean areAllPlayersReady(){
        boolean allPlayersReady = true ;
        for (ServerConnectedClient connectedClient: connectedClientList.getConnectedClients()){
                allPlayersReady &= connectedClient.getConnectedPlayer().isReady();
        }
        return allPlayersReady;
    }

    public void checkIfFirstReady(int clientID){
        if(firstReadyClient == -1 && connectedClientList.getConnectedClientByClientID(clientID).getConnectedPlayer().isReady()){
            firstReadyClient = clientID;
            Maps[] maps = Maps.DizzyHighway.getDeclaringClass().getEnumConstants();
            connectedClientList.getConnectedClientByClientID(clientID).safeWriteInOwnOutStream(new SelectMap(maps));

        }
    }

    public int getFirstReadyClient() {
        return firstReadyClient;
    }

    public boolean isMapSelected() {
        return mapSelected;
    }

    public void setMapSelected(boolean mapSelected) {
        this.mapSelected = mapSelected;
    }

    public Maps getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(Maps selectedMap) {
        this.selectedMap = selectedMap;
    }

    public synchronized void addNewConnectedClient(ServerConnectedClient serverConnectedClient){
        connectedClientList.addClient(serverConnectedClient);
    }

    public void notifyNewClientOfConnectedClients(ServerConnectedClient newServerConnectedClient){
        ServerConnectedPlayer newPlayer = newServerConnectedClient.getConnectedPlayer();
        for(ServerConnectedClient serverConnectedClient:connectedClientList.getConnectedClients()){
            ServerConnectedPlayer currentPlayer = serverConnectedClient.getConnectedPlayer();
            if(currentPlayer.getClientID() != newPlayer.getClientID()) {
                newServerConnectedClient.safeWriteInOwnOutStream(new PlayerAdded(currentPlayer.getClientID(),currentPlayer.getName(),currentPlayer.getFigure()));
                newServerConnectedClient.safeWriteInOwnOutStream(new PlayerStatus(currentPlayer.getClientID(),currentPlayer.isReady()));

            }
        }
    }

    public synchronized void broadcast(int excludedClient, ProtocolSendObject protocolSendObject){
        for(ServerConnectedClient serverConnectedClient:connectedClientList.getConnectedClients()){
            if(serverConnectedClient.getConnectedPlayer().getClientID() != excludedClient) {
                serverConnectedClient.safeWriteInOwnOutStream(protocolSendObject);
            }
        }
    }

    public synchronized void sendTo(int clientID, ProtocolSendObject protocolSendObject){
        connectedClientList.getConnectedClientByClientID(clientID).safeWriteInOwnOutStream(protocolSendObject);
    }
}
