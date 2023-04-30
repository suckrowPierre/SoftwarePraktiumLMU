package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class CheckpointMovedMessageBody extends MessageBody {
    int checkpointID;
    int x;
    int y;

    public CheckpointMovedMessageBody(int checkpointID, int x, int y) {
        this.checkpointID = checkpointID;
        this.x = x;
        this.y = y;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(checkpointID);
        content.add(x);
        content.add(y);
        return content;
    }
}
