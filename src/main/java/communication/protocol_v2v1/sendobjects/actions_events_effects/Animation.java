package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class Animation extends ProtocolSendObject {

    public Animation(String type) {
        this.messageType = MessageType.Animation;
        this.messageBody = new AnimationMessageBody(type);
    }

    public String getType() {return (String) messageBody.getBodyContent().get(0);}
}

