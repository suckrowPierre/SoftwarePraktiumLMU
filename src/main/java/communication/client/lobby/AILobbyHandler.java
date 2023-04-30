package communication.client.lobby;

import communication.client.Client;

import java.util.Objects;

/**
 * Lobby Logic for AI
 * @author Pierre-Louis Suckrow
 */
public class AILobbyHandler extends LobbyHandler {

  public AILobbyHandler(Client client) {
    super(client);
  }

  public void removeConnectedClient(int clientID) {
    int indexToRemove = getIndexByClientID(clientID);
    boolean isPlayer = Objects.requireNonNull(getByID(clientID)).isPlayer();
    if (indexToRemove != -1) {
      connectedClients.remove(indexToRemove);
    }
  }


  public void addConnectedClient(int clientID, String name, int figure) {
    updateConnectedClient(clientID, name, figure);
  }


  public void updateStatus(int clientID, boolean status) {
    if (clientID == client.getClientID()) {
      // maybe redudant if thisClientPlayer has the same referenz as ServerConnectedClient with same ID in
      // connectedClients
      this.thisClientPlayer.setReady(status);
    }
    connectedClients.get(getIndexByClientID(clientID)).setReady(status);
  }

}
