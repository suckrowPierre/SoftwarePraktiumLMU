package communication.protocol_v2v1.sendobjects.communication_setup;

import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.sendobjects.bodycontents.Protocols;

public class HelloClient extends ProtocolSendObject {

  public HelloClient(Protocols protocol) {
    this.messageType = MessageType.HelloClient;
    this.messageBody = new HelloClientMessageBody(protocol);
  }

  public Protocols getProtocol(){
    return (Protocols) messageBody.getBodyContent().get(0);
  }


}

