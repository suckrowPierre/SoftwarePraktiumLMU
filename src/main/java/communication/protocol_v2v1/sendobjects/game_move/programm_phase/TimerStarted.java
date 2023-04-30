package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class TimerStarted extends ProtocolSendObject {
    public TimerStarted() {
        this.messageType = MessageType.TimerStarted;
        this.messageBody = new TimerStartedMessageBody();
    }

}


