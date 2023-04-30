package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class Movement extends ProtocolSendObject {
    public Movement(int clientID, int x, int y) {
        this.messageType = MessageType.Movement;
        this.messageBody = new MovementMessageBody(clientID, x, y);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public int getX() {return (int) messageBody.getBodyContent().get(1);}
    public int getY() {return (int) messageBody.getBodyContent().get(2);}
}


