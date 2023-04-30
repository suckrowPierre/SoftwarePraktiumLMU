package communication.client.lobby;

import communication.client.Client;
import javafx.application.Platform;

import java.util.Objects;

/**
 * Lobby Logic for User
 * @author Pierre-Louis Suckrow
 */
public class UserLobbyHandler extends LobbyHandler {



  public UserLobbyHandler(Client client) {
    super(client);
  }

  public void removeConnectedClient(int clientID) {
    int indexToRemove = getIndexByClientID(clientID);
    boolean isPlayer = Objects.requireNonNull(getByID(clientID)).isPlayer();
    if (indexToRemove != -1) {
      connectedClients.remove(indexToRemove);
    }
    if (isPlayer) {
      updatePlayerView();
    }
  }

  public void addConnectedClient(int clientID, String name, int figure) {
    updateConnectedClient(clientID, name, figure);
    if (Objects.requireNonNull(getByID(clientID)).isPlayer()) {
      updatePlayerView();
    }
  }

  private void updatePlayerView() {
    Platform.runLater(() -> model.clearlists());
    for (ConnectedClient connectedClient : getPlayers()) {
      Platform.runLater(
          () -> model.setPlayername(connectedClient.getFigure(), connectedClient.getName()));
      Platform.runLater(
          () -> model.setPlayerstatus(connectedClient.getFigure(), connectedClient.isReady()));
    }
  }

  public void updateStatus(int clientID, boolean status) {

    if (clientID == client.getClientID()) {
      // maybe redudant if thisClientPlayer has the same referenz as ServerConnectedClient with same ID in
      // connectedClients
      this.thisClientPlayer.setReady(status);
    }
    connectedClients.get(getIndexByClientID(clientID)).setReady(status);
    updatePlayerView();
  }


}
