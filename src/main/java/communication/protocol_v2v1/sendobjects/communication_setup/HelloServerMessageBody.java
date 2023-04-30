package communication.protocol_v2v1.sendobjects.communication_setup;

import communication.protocol_v2v1.MessageBody;
import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import communication.protocol_v2v1.sendobjects.bodycontents.Protocols;

import java.util.ArrayList;

public class HelloServerMessageBody extends MessageBody {
    Groups group;
    boolean isAI;
    Protocols protocol;

    public HelloServerMessageBody(Protocols protocol, Groups group, boolean isAI) {
        this.protocol = protocol;
        this.group = group;
        this.isAI = isAI;
    }

    public Groups getGroup() {
        return group;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList(3);
        content.add(protocol);
        content.add(group);
        content.add(isAI);
        return content;
    }
}
