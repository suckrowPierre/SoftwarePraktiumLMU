package communication.protocol_v2v1.sendobjects.chat;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class ReceivedChatMessageBody extends MessageBody {
    String message;
    int from;
    boolean isPrivate;

    public ReceivedChatMessageBody(String message, int from, boolean isPrivate) {
        this.message = message;
        this.from = from;
        this.isPrivate = isPrivate;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(message);
        content.add(from);
        content.add(isPrivate);
        return content;
    }
}
