package communication.server;

import communication.protocol_v2v1.sendobjects.bodycontents.Protocols;
import communication.server.connectedclient.ConnectedClientsHandler;
import org.tinylog.Logger;
import util.OutputMessages;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.Inet4Address;
import java.nio.charset.StandardCharsets;

/**
 * Server class
 * @author Pierre-Louis Suckrow
 */
public class Server {

    public static Protocols version = Protocols.V2V1;
    private final ServerSocket serverSocket;
    private final int minPlayers;
    private int port;
    private String ipaddress;


    /**
     * Create Server
     *
     * @param port       TCP port
     * @param minPlayers to start a game
     * @throws IOException
     */
    public Server(int port, int minPlayers) throws IOException {
        this.port = port;
        this.ipaddress = Inet4Address.getLocalHost().getHostAddress();
        this.serverSocket = new ServerSocket(port);
        this.minPlayers = minPlayers;
        try {
            this.startServer();
        } catch (Exception e) {

        }
    }


    /**
     * starting Server
     *
     * @throws Exception
     */
    public void startServer() throws Exception {
        Logger.info(OutputMessages.server_started + port);
        Logger.info(OutputMessages.ip_address + ipaddress);
        Logger.info("minPlayers = " + minPlayers);
        ConnectedClientsHandler.getInstance().setMinPlayers(minPlayers);

        try {
            while (!serverSocket.isClosed()) {
                setupConnectionSetupClientHandler();
            }
        } catch (Exception e) {
            System.out.println(e);
            closeServerSocket();
        }
    }

    /**
     * Setup Connection with Output and Input Stream Writer and Reader
     *
     * @throws IOException
     */
    private void setupConnectionSetupClientHandler() throws IOException {
        Socket socket = serverSocket.accept();
        Logger.info("new connection");
        BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        ;
        BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        ConnectionSetupHandler connectionSetup = new ConnectionSetupHandler(socket, outStream, inStream);
        Thread thread = new Thread(connectionSetup);
        thread.start();

    }


    /**
     * For closing server
     */
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
