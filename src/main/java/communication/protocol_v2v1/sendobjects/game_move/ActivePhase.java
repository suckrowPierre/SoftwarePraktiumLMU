package communication.protocol_v2v1.sendobjects.game_move;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class ActivePhase extends ProtocolSendObject {

    public ActivePhase(int phase) {
        this.messageType = MessageType.ActivePhase;
        this.messageBody = new ActivePhaseMessageBody(phase);
    }

    public int getPhase() {return (int) messageBody.getBodyContent().get(0);}

}



