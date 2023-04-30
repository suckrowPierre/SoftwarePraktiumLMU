package communication.client.data_handlers;

import communication.client.UserClient;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.chat.SendChat;
import javafx.application.Platform;

import java.io.IOException;

/**
 * Handles the input from Chat
 * @author Pierre-Louis Suckrow
 */
public class ChatUserInputHandler extends DataHandler {


  UserClient client;

  public ChatUserInputHandler(UserClient client) {
    this.client = client;
    view_connected = false;
    game_connected = false;
  }

  public ProtocolSendObject handle(String input) throws IOException {
    ProtocolSendObject send = null;
    if ("bye".equals(input)) {
      Platform.exit();
      throw new IOException();
    }
    char firstchar = input.charAt(0);
    switch (firstchar) {
      case '/' -> {
        // TODO:
      }
      case '$' -> {
        send = privateMessageHandler(input);
      }
      default -> {
        send = new SendChat(input,-1);
      }
    }
    return send;
  }

  private ProtocolSendObject privateMessageHandler(String input) {
    ProtocolSendObject send;
    String inputlist[] = input.split("\\$");
    String targetetUsername = inputlist[1];
    int targetID = Integer.parseInt(targetetUsername);
    String message = inputlist[2];

    send = new SendChat(message, targetID);
    return send;
  }
}
