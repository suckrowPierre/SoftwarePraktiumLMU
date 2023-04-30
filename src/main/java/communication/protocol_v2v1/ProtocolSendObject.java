package communication.protocol_v2v1;

import java.io.Serializable;

/**
 * Object to send over the TCP Sockets, same structure provided protocol
 * @author Simon Naab, Pierre-Louis Suckrow
 */

public abstract class ProtocolSendObject implements Serializable {

  protected MessageBody messageBody;
  protected MessageType messageType;


  //protected ArrayList<BodyContent> messageBody;


  public MessageType getMessageType() {
    return messageType;
  }

  public MessageBody getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(MessageBody messageBody) {
    this.messageBody = messageBody;
  }

  @Override
  public String toString() {
    return "ProtocolSendObject{" +
            "messageType=" + messageType +
            ", messageBody=" + messageBody.toString() +
            '}';
  }


}
