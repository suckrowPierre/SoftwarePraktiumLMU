package communication.protocol_v2v1.sendobjects.special;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class ErrorMessage extends ProtocolSendObject {
    public ErrorMessage(String error) {
        this.messageType = MessageType.Error;
        this.messageBody = new ErrorMessageBody(error);
    }

    public String getMessage(){
        return (String) messageBody.getBodyContent().get(0);
    }
}

