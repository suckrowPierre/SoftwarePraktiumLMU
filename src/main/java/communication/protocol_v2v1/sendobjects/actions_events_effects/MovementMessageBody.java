package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class MovementMessageBody extends MessageBody {
    int clientID;
    int x;
    int y;

    public MovementMessageBody(int clientID, int x, int y) {
        this.clientID = clientID;
        this.x = x;
        this.y = y;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(x);
        content.add(y);
        return content;
    }
}
