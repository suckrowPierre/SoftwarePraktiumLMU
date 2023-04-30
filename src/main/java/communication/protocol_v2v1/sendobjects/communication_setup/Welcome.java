package communication.protocol_v2v1.sendobjects.communication_setup;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class Welcome extends ProtocolSendObject {

  public Welcome(int ID) {
    this.messageType = MessageType.Welcome;
    this.messageBody = new WelcomeMessageBody(ID);
  }

  public int getID(){
      return (int) messageBody.getBodyContent().get(0);
  }

}
