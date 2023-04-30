package communication.client;

import communication.client.lobby.LobbyHandler;
import util.Filenames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Subclass of Client with ability to write in OutStream
 * @author Pierre-Louis Suckrow
 */
public abstract class WritingClient extends Client{
    protected BufferedReader inStream;
    protected LobbyHandler lobbyHandler;



    public WritingClient(String host, int port, String version) throws IOException {
        super(host, port, version);
        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

    }

    public LobbyHandler getLobbyHandler() {
        return lobbyHandler;
    }

    public void closeClient(){
        closeEverything(socket,outStream,inStream);
        System.exit(0);
    }

    protected void closeEverything(
            Socket socket, BufferedWriter outputStream, BufferedReader inStream) {
        try {
            if (outputStream != null) {
                outputStream.close();

            }
            if (inStream != null) {
                inStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
           // e.printStackTrace();
        }

    }

    public void closeEverythingSoft() {
        try {
            outStream.close();
            inStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
