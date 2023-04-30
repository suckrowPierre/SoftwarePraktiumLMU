package communication.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.SendObjectJsonWrapper;
import org.tinylog.Logger;
import util.LoggingTags;
import util.PPLogger;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Handles the Communication of the Server
 * @author Pierre-Louis Suckrow
 */
public abstract class ServerCommunication {
    protected BufferedWriter outStream;
    protected BufferedReader inStream;
    protected Socket socket;

    public ServerCommunication(Socket socket, BufferedWriter out, BufferedReader in) {
        try {
            this.outStream = out;
            this.inStream = in;
            this.socket = socket;
        } catch (Exception e) {
            closeEverything(socket, outStream, inStream);
        }

    }

    /**
     * For writing in OutStream. "sending"
     *
     * @param com
     * @param send ProtocolSendObject you want to send
     * @throws IOException
     */
    protected void writeOutStream(ServerCommunication com, ProtocolSendObject send) throws IOException {
        JsonObject jsontosend = SendObjectJsonWrapper.wrap(send);
        String tosend = new Gson().toJson(jsontosend);
        Logger.tag(LoggingTags.communication.toString()).info(PPLogger.logCommunicationSending, tosend);
        com.outStream.write(tosend);
        com.outStream.newLine();
        com.outStream.flush();

    }

    /**
     * "recieving"
     *
     * @return recieved ProtocolSendObject
     * @throws IOException
     */
    public ProtocolSendObject readInStream() throws IOException {
        String recjson = inStream.readLine();
        String recjson2 = new String(recjson.getBytes(), StandardCharsets.UTF_8);
        Logger.tag(LoggingTags.communication.toString()).info(PPLogger.logCommunicationReceiving, recjson2);
        ProtocolSendObject recvSendObject = SendObjectJsonWrapper.dewrapString(recjson2);
        return recvSendObject;
    }

    /**
     * closes the connection
     *
     * @param socket
     * @param out
     * @param in
     */
    protected void closeEverything(Socket socket, BufferedWriter out, BufferedReader in) {
        try {
            if (outStream != null) {
                outStream.close();
            }
            if (inStream != null) {
                inStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            Logger.info(e);
        }
    }

}

