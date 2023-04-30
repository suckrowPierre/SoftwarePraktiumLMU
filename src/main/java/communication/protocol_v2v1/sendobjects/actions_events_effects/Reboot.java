package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class Reboot extends ProtocolSendObject {

    public Reboot(int clientID) {
        this.messageType = MessageType.Reboot;
        this.messageBody = new RebootMessageBody(clientID);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
}

