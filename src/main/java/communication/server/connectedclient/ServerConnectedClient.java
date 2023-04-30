package communication.server.connectedclient;

import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.communication_setup.Alive;
import communication.protocol_v2v1.sendobjects.special.Actions;
import communication.protocol_v2v1.sendobjects.special.ConnectionUpdate;
import communication.server.GameHandler;
import communication.server.ServerCommunication;
import org.tinylog.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Connected Client, handles communication for a single Client
 * @author Pierre-Louis Suckrow
 */
public class ServerConnectedClient extends ServerCommunication {
    private ServerConnectedPlayer connectedPlayer;
    private ServerRecieverHandler serverRecieverHandler;
    private boolean running = true;


    public ServerConnectedClient(ServerConnectedPlayer connectedPlayer, Socket socket, BufferedWriter out, BufferedReader in) {
        super(socket, out, in);
        this.connectedPlayer = connectedPlayer;
        serverRecieverHandler = new ServerRecieverHandler(connectedPlayer.getClientID());
    }

    public ServerConnectedPlayer getConnectedPlayer() {
        return connectedPlayer;
    }

    public void listenincoming() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (socket.isConnected()) {
                        try {
                            handleIncoming();
                        } catch (Exception e) {
                            closeEverything(socket, outStream, inStream);
                            break;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void startProtocol() {
        sendalive();
        listenincoming();
    }

    public void sendalive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO: this shouldnt be needed
                while (socket.isConnected() && running) {
                    try {
                        if (AliveHandler.getInstance().isStillAlive()) {
                            writeInOwnOutStream(new Alive());
                            TimeUnit.SECONDS.sleep(5);
                            AliveHandler.getInstance().incrementAliveCounter();
                        } else {
                            closeConnectionAndRemoveClient();
                        }
                    } catch (Exception e) {
                        if (e.getMessage().equals("Stream closed")) {
                            closeConnectionAndRemoveClient();
                        }

                    }
                }
            }
        }).start();
    }

    public void closeConnectionAndRemoveClient() {
        Logger.info("Client: " + connectedPlayer.getClientID() + " not alive anymore");
        ConnectedClientsHandler.getInstance().getConnectedClientList().removeConnectedClient(connectedPlayer.getClientID());
        ConnectedClientsHandler.getInstance().sendToAll(new ConnectionUpdate(connectedPlayer.getClientID(), false, Actions.Remove));
        GameHandler.getInstance().removePlayer(connectedPlayer.getClientID());
        closeConnection();
        running = false;
    }


    private void handleIncoming() throws IOException, ClassNotFoundException {
        ProtocolSendObject recvsendobject = readInStream();
        try {
            safeWriteInOwnOutStream(serverRecieverHandler.handle(recvsendobject));
        } catch (Exception e) {
            Logger.info(e);
        }

    }

    public synchronized void safeWriteInOwnOutStream(ProtocolSendObject protocolSendObject) {
        try {
            writeInOwnOutStream(protocolSendObject);
        } catch (Exception e) {
            Logger.info(e);
        }
    }

    private void writeInOwnOutStream(ProtocolSendObject protocolSendObject) throws IOException {
        if (protocolSendObject != null) {
            writeOutStream(this, protocolSendObject);
        }
    }


    public void closeConnection() {
        closeEverything(this.socket, this.outStream, this.inStream);
    }
}
