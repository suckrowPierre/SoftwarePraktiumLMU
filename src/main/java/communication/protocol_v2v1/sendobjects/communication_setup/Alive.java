package communication.protocol_v2v1.sendobjects.communication_setup;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class Alive extends ProtocolSendObject {

  //
  public Alive() {
    this.messageType = MessageType.Alive;
    this.messageBody = new AliveMessageBody();
  }
}

