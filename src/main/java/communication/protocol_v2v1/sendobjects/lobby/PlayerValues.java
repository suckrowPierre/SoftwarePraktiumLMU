package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;


public class PlayerValues extends ProtocolSendObject {

  public PlayerValues(String name, int figure) {
    this.messageType = MessageType.PlayerValues;
    this.messageBody = new PlayerValuesMessageBody(name, figure);
  }


  public String getName(){
    return (String) messageBody.getBodyContent().get(0);
  }

  public int getFigure(){
    return (int) messageBody.getBodyContent().get(1);
  }

}

