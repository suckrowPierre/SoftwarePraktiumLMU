package communication.protocol_v2v1.sendobjects.communication_setup;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import communication.protocol_v2v1.sendobjects.bodycontents.Protocols;

public class HelloServer extends ProtocolSendObject {

  public HelloServer(Protocols protocol, Groups group, boolean isAI) {
    this.messageType = MessageType.HelloServer;
    this.messageBody = new HelloServerMessageBody(protocol, group, isAI);
  }

  public Protocols getProtocol(){
    return (Protocols) messageBody.getBodyContent().get(0);
  }

  public Groups getGroup(){
    return (Groups) messageBody.getBodyContent().get(1);
  }

  public boolean isAi(){
    return (boolean) messageBody.getBodyContent().get(2);
  }


}

