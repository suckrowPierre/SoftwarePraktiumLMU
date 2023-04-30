package communication.client;

import communication.client.data_handlers.ChatUserInputHandler;
import communication.client.data_handlers.client_recieverhandler.UserClientRecieverHandler;
import communication.client.lobby.UserLobbyHandler;
import communication.protocol_v2v1.ProtocolSendObject;
import org.tinylog.Logger;

import java.io.*;

/**
 * Subclass of WritingClient with ability to handle the incoming SendObjects and handle the ChatInput of a Client
 * @author Pierre-Louis Suckrow
 */
public abstract class UserClient extends WritingClient {


  protected ChatUserInputHandler chat_input_handler;
  protected UserClientRecieverHandler recvhandler;
  private boolean closed = false;


  public UserClient(String host, int port, String version) throws IOException {
    super(host, port, version);
    lobbyHandler = new UserLobbyHandler(this);
    chat_input_handler = new ChatUserInputHandler(this);
    recvhandler = new UserClientRecieverHandler(this);
  }

  protected void handleInput(String input) throws IOException {
    if (!input.equals("")) {
      ProtocolSendObject send = chat_input_handler.handle(input);
      if (send != null) {
        writeOutStream(send);
      }
    }
  }

  protected void instreamThread() {
    new Thread(
            new Runnable() {
              @Override
              public void run() {
                while (socket.isConnected() && (!closed)) {
                  try {
                    String recjson = inStream.readLine();
                    if (recjson != null) {
                      recvhandler.handle(recjson);
                    }
                  } catch (Exception e) {
                    Logger.info(e);
                    closed = true;
                    closeEverything(socket, outStream, inStream);
                  }
                }
              }
            })
            .start();
  }

  protected abstract void startChat();

}



