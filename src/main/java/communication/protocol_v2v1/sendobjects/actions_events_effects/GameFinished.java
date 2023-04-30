package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class GameFinished extends ProtocolSendObject {
    public GameFinished(int clientID) {
        this.messageType = MessageType.GameFinished;
        this.messageBody = new GameFinishedMessageBody(clientID);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
}


