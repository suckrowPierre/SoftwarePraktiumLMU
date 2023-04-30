package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.Direction;
import game.data.Orientations;

public class RebootDirection extends ProtocolSendObject {
    public RebootDirection(Orientations direction){
        this.messageType = MessageType.RebootDirection;
        this.messageBody = new RebootDirectionMessageBody(direction);
    }

    public Orientations getDirection() {return (Orientations) messageBody.getBodyContent().get(0);}
}

