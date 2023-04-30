package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class ChooseRegisterMessageBody extends MessageBody {
    int register;

    public ChooseRegisterMessageBody(int register) {
        this.register = register;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(register);
        return content;
    }
}
