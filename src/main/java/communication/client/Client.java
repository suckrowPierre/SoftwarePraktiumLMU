package communication.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.SendObjectJsonWrapper;
import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import communication.protocol_v2v1.sendobjects.bodycontents.Protocols;
import org.tinylog.Logger;
import util.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public abstract class Client {

  public final String host;
  public final int port;
  BufferedWriter outStream;
  protected Socket socket;
  public static Protocols version;
  public static final Groups group = Groups.PragmatischePinguine;
  public int clientID;


  /**
   * Client with specified host, port and version
   * @author Pierre-Louis Suckrow
   * @param host
   * @param port
   * @param version
   * @throws IOException
   */
  public Client(String host, int port, String version) throws IOException {


    this.host = host;
    this.port = port;
    this.version = Protocols.getbyVersion(version);
    this.socket = new Socket(host, port);
    Logger.tag(LoggingTags.communication.toString()).info(
            OutputMessages.host_connection + host + OutputMessages.port_connection + port);
    outStream =
        new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    WritingClientSingelton.getInstance().setOutStream(outStream);
  }

  protected void closeSocket() throws IOException {
    socket.close();
  }



  protected void closeEverything(Socket socket, BufferedWriter outputStream) {
    try {
      if (outputStream != null) {
        outputStream.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.exit(0);
  }

  public void writeOutStream(ProtocolSendObject send) {

    JsonObject jsontosend = SendObjectJsonWrapper.wrap(send);
    String tosend = new Gson().toJson(jsontosend);
    writeOutStreamString(tosend);
  }

  public void writeOutStreamString(String send) {
    try {
      Logger.tag(LoggingTags.communication.toString()).info(PPLogger.logCommunicationSending,send);
      outStream.write(send);
      outStream.newLine();
      outStream.flush();
    } catch (Exception e) {

    }
  }

  public int getClientID() {
    return clientID;
  }

  public void setClientID(int clientID) {
    this.clientID = clientID;
  }
}
