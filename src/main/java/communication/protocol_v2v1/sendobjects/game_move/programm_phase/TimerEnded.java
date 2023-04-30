package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class TimerEnded extends ProtocolSendObject {
    public TimerEnded(int[] clientIDs) {
        this.messageType = MessageType.TimerEnded;
        this.messageBody = new TimerEndedMessageBody(clientIDs);
    }

    public int[] getClientIDs() {return (int[]) messageBody.getBodyContent().get(0);}
}


