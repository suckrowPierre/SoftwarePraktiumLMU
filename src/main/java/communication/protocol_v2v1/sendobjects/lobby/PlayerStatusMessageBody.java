package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class PlayerStatusMessageBody extends MessageBody {
    int clientID;
    boolean ready;

    public PlayerStatusMessageBody(int clientID, boolean ready) {
        this.clientID = clientID;
        this.ready = ready;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(ready);
        return content;
    }
}
