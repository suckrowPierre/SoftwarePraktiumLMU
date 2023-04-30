package communication.client;

import communication.client.data_handlers.client_recieverhandler.AIClientRecieverHandler;
import communication.client.lobby.AILobbyHandler;

import java.io.IOException;

public class AIClient extends WritingClient{

    /**
     * Subclass of WritingClient for AI
     * @author Pierre-Louis Suckrow
     */
    protected AIClientRecieverHandler recvhandler;

    public AIClient(String host, int port, String version) throws IOException {
        super(host, port, version);
        lobbyHandler = new AILobbyHandler(this);
        recvhandler = new AIClientRecieverHandler(this);
        startChat();
    }

    public void setupAI(){
        writeOutStream(null);
    }


    protected void instreamThread() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (socket.isConnected()) {
                            try {
                                String recjson = inStream.readLine();
                                if (recjson != null) {
                                    recvhandler.handle(recjson);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                                closeEverything(socket, outStream, inStream);
                            }
                        }
                    }
                })
                .start();
    }


    protected void startChat() {
        this.instreamThread();
    }
}
