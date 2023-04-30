package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class PlayerAdded extends ProtocolSendObject {

  public PlayerAdded(int clientID, String name, int figure) {
    this.messageType = MessageType.PlayerAdded;
    this.messageBody = new PlayerAddedMessageBody(clientID,name,figure);
  }
  public int getClientID(){
    return (int) messageBody.getBodyContent().get(0);
  }

  public String getName(){
    return (String) messageBody.getBodyContent().get(1);
  }

  public int getFigure(){
    return (int) messageBody.getBodyContent().get(2);
  }

}

