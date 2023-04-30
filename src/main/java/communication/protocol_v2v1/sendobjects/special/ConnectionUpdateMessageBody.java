package communication.protocol_v2v1.sendobjects.special;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public  class ConnectionUpdateMessageBody extends MessageBody {
    int clientID;
    boolean connected;
    Actions action;

    public ConnectionUpdateMessageBody(int clientID, boolean connected, Actions action) {
        this.clientID = clientID;
        this.connected = connected;
        this.action = action;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(connected);
        content.add(action);
        return content;
    }
}
