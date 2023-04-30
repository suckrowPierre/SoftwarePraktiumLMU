package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class SetStatus extends ProtocolSendObject {

    public SetStatus(boolean ready) {
        this.messageType = MessageType.SetStatus;
        this.messageBody = new SetStatusMessageBody(ready);
    }


    public boolean isReady(){
        return (boolean) messageBody.getBodyContent().get(0);
    }


}

