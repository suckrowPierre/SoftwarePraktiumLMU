package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class CheckPointReachedMessageBody extends MessageBody {
    int clientID;
    int number;

    public CheckPointReachedMessageBody(int clientID, int number) {
        this.clientID = clientID;
        this.number = number;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(number);
        return content;
    }
}
