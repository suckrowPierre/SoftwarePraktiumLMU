package communication.protocol_v2v1.sendobjects.chat;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class SendChatMessageBody extends MessageBody {
    String message;
    int to;

    public SendChatMessageBody(String message, int to) {
        this.message = message;
        this.to = to;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(message);
        content.add(to);
        return content;
    }
}
