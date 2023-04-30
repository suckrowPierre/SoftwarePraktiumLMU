package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class ShuffleCoding extends ProtocolSendObject {

    public ShuffleCoding(int clientID) {
        this.messageType = MessageType.ShuffleCoding;
        this.messageBody = new ShuffleCodingMessageBody(clientID);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
}


