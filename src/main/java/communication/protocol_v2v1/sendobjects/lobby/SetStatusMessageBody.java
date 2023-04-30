package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class SetStatusMessageBody extends MessageBody {
    boolean ready;

    public SetStatusMessageBody(boolean ready) {
        this.ready = ready;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(ready);
        return content;
    }
}
