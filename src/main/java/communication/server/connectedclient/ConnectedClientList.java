package communication.server.connectedclient;

import org.tinylog.Logger;

import java.util.LinkedList;

/**
 * LinkedList of ConnectedClients
 * @author Pierre-Louis Suckrow
 */
public class ConnectedClientList {
    private LinkedList <ServerConnectedClient> connectedClients;

    public ConnectedClientList() {
        this.connectedClients = new LinkedList<> ();
    }


    public void addClient (ServerConnectedClient serverConnectedClient) {
        serverConnectedClient.startProtocol();
        connectedClients.add(serverConnectedClient);
    }

    public void removeConnectedClient (int clientID) {
        Logger.info("removing " + clientID + " from connectedClients");
        ServerConnectedClient serverConnectedClient = getConnectedClientByClientID(clientID);
        connectedClients.remove(serverConnectedClient);
    }

    public ServerConnectedClient getConnectedClientByClientID (int clientID) {
        for (ServerConnectedClient serverConnectedClient:connectedClients){
            if (serverConnectedClient.getConnectedPlayer().getClientID() == clientID) {
                return serverConnectedClient;

            }
        } return null;

    }

    public LinkedList<ServerConnectedClient> getConnectedClients() {
        return connectedClients;
    }


}
