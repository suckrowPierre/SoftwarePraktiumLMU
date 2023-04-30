package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class EnergyMessageBody extends MessageBody {
    int clientID;
    int count;
    String source;

    public EnergyMessageBody(int clientID, int count, String source) {
        this.clientID = clientID;
        this.count = count;
        this.source = source;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(count);
        content.add(source);
        return content;
    }
}
