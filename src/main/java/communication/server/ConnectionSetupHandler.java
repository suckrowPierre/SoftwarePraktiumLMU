package communication.server;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.communication_setup.HelloClient;
import communication.protocol_v2v1.sendobjects.communication_setup.HelloServer;
import communication.protocol_v2v1.sendobjects.communication_setup.Welcome;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import communication.server.connectedclient.ConnectedClientsHandler;
import communication.server.connectedclient.ServerConnectedClient;
import communication.server.connectedclient.ServerConnectedPlayer;
import org.tinylog.Logger;

import java.io.*;
import java.net.Socket;
/**
 * handles new connection if client has correction version and sends the correction protocol messages hew will be fast forwarded to connectedClientHandler
 * @author Pierre-Louis Suckrow
 */
public class ConnectionSetupHandler extends ServerCommunication implements Runnable {

  public ConnectionSetupHandler(Socket socket, BufferedWriter out, BufferedReader in) {
    super(socket, out, in);

  }

  @Override
  public void run() {
    try{
      sendAndRecieveProtocolVersion();
    }catch (Exception e){
      System.out.println(e);
    }
  }

  /**
   * checks if the connected client has correct Version and if he connects correctly. If all is correct he will be added to connectedClientHandler
   * @throws IOException
   */
  private void sendAndRecieveProtocolVersion() throws IOException{
    ProtocolSendObject tosend = new HelloClient(Server.version);
    writeOutStream(this, tosend);
    ProtocolSendObject recvProtocalSendObject = readInStream();
    if(recvProtocalSendObject.getMessageType() == MessageType.HelloServer){
      HelloServer helloServer = (HelloServer) recvProtocalSendObject;
      if(helloServer.getProtocol() == Server.version){
        Logger.info("new client with new Version");
        int ID = ConnectedClientsHandler.getInstance().getNewClientID();
        ServerConnectedPlayer connectedPlayer = new ServerConnectedPlayer(helloServer.getGroup(),helloServer.isAi(),ID);
        ServerConnectedClient connectedClient = new ServerConnectedClient(connectedPlayer,socket,outStream,inStream);
        ConnectedClientsHandler.getInstance().addNewConnectedClient(connectedClient);
        writeOutStream(this, new Welcome(ID));
      }else{
        writeOutStream(this,new ErrorMessage("wrong protocol version. Please use Version. closing this connection" + Server.version));
        closeEverything(socket,outStream,inStream);
      }
    }else{
      writeOutStream(this,new ErrorMessage("wrong protocol message. Not in order of protocol. closing this connection"));
      closeEverything(socket,outStream,inStream);
    }
  }









}
