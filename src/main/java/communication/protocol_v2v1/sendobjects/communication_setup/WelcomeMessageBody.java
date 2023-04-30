package communication.protocol_v2v1.sendobjects.communication_setup;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class WelcomeMessageBody extends MessageBody {
    int clientID;

    public WelcomeMessageBody(int ID) {
        this.clientID = ID;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        return content;
    }
}
