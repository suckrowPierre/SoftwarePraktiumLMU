package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class CheckpointMoved extends ProtocolSendObject {
    public CheckpointMoved(int checkpointID, int x, int y) {
        this.messageType = MessageType.CheckpointMoved;
        this.messageBody = new CheckpointMovedMessageBody(checkpointID, x, y);
    }

    public int getCheckpointID() {return (int) messageBody.getBodyContent().get(0);}
    public int getX() {return (int) messageBody.getBodyContent().get(1);}
    public  int getY() {return (int) messageBody.getBodyContent().get(2);}
}
