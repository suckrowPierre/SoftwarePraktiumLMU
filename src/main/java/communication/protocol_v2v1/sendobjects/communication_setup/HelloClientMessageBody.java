package communication.protocol_v2v1.sendobjects.communication_setup;

import communication.protocol_v2v1.MessageBody;
import communication.protocol_v2v1.sendobjects.bodycontents.Protocols;

import java.util.ArrayList;

public class HelloClientMessageBody extends MessageBody {
    Protocols protocol;


    public HelloClientMessageBody(Protocols protocol) {
        this.protocol = protocol;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(protocol);
        return content;
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "protocol=" + protocol +
                '}';
    }
}
