package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class RebootMessageBody extends MessageBody {
    int clientID;

    public RebootMessageBody(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        return content;
    }
}
