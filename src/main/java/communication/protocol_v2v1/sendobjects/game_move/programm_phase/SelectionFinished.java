package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class SelectionFinished extends ProtocolSendObject {
    public SelectionFinished(int clientID) {
        this.messageType = MessageType.SelectionFinished;
        this.messageBody = new SelectionFinishedMessageBody(clientID);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
}


