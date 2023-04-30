package communication.client.lobby;

import communication.client.Client;
import communication.client.chatgui.LobbyModel;

import java.util.ArrayList;

/**
 * Lobby Logic
 * @author Pierre-Louis Suckrow
 */
public abstract class LobbyHandler {

  LobbyModel model = LobbyModel.getInstance();
  Client client;
  ArrayList<ConnectedClient> connectedClients;
  ConnectedClient thisClientPlayer;

  public LobbyHandler(Client client) {
    this.client = client;
    connectedClients = new ArrayList<>();
  }

  public abstract void removeConnectedClient(int clientID);

  public ArrayList<ConnectedClient> getConnectedClients() {
    return connectedClients;
  }

  public abstract void addConnectedClient(int clientID, String name, int figure);


  public abstract void updateStatus(int clientID, boolean status);

  protected void updateConnectedClient(int clientID, String name, int figure) {
    int indexToChange = getIndexByClientID(clientID);
    if (indexToChange == -1) {
      connectedClients.add(new ConnectedClient(clientID, name, figure));
    } else {
      connectedClients.set(indexToChange, new ConnectedClient(clientID, name, figure));
    }
  }

  protected int getIndexByClientID(int ID) {
    int index = 0;
    for (ConnectedClient connectedClient : connectedClients) {
      if (connectedClient.getID() == ID) {
        return index;
      }
      index++;
    }
    return -1;
  }

  public ConnectedClient getByID(int ID) {
    for (ConnectedClient connectedClient : connectedClients) {
      if (connectedClient.getID() == ID) {
        return connectedClient;
      }
    }
    return null;
  }

  public void setThisClientPlayer(int ID, String name, int figure) {
    this.thisClientPlayer = new ConnectedClient(ID, name, figure);
    connectedClients.add(thisClientPlayer);
  }

  public ArrayList<ConnectedClient> getPlayers() {
    ArrayList<ConnectedClient> players = new ArrayList<>();
    for (ConnectedClient connectedClient : connectedClients) {
      if (connectedClient.isPlayer()) {
        players.add(connectedClient);
      }
    }
    return players;
  }

  public String getNameByID(int ID) {
    for (ConnectedClient connectedClient : connectedClients) {
      if (connectedClient.getID() == ID) {
        return connectedClient.getName();
      }
    }
    return null;
  }
}
