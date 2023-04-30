package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class RegisterChosenMessageBody extends MessageBody {
    int clientID;
    int register;

    public RegisterChosenMessageBody(int clientID, int register) {
        this.clientID = clientID;
        this.register = register;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(register);
        return content;
    }
}

