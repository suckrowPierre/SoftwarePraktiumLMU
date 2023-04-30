package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class CheckPointReached extends ProtocolSendObject {
    public CheckPointReached(int clientID, int number) {
        this.messageType = MessageType.CheckPointReached;
        this.messageBody = new CheckPointReachedMessageBody(clientID, number);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public int getNumber() {return (int) messageBody.getBodyContent().get(1);}
}


