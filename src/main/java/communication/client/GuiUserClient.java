package communication.client;

import communication.client.chatgui.*;

import java.io.IOException;


/**
 * Subclass of WritingClient with connected GUI
 * @author Pierre-Louis Suckrow
 */
public class GuiUserClient extends UserClient {

  private Model data;

  public GuiUserClient(String host, int port, String version) throws IOException {
    super(host, port, version);
    startChat();
  }

  public void addDataModel(Model m) {
    recvhandler.setmodel(m);
    data = m;
  }

  public void sendMessage(String messagetoSend) {
    new Thread(
            new Runnable() {
              @Override
              public void run() {
                  try {
                    String input = messagetoSend;
                    if (socket.isConnected()) {
                      handleInput(input);
                    }
                  } catch (IOException e) {
                    closeEverything(socket, outStream, inStream);
                  }
                }
            })
        .start();
  }

  protected void startChat() {
    this.instreamThread();
  }
}
