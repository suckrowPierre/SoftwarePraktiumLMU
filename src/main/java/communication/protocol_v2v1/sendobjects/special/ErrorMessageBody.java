package communication.protocol_v2v1.sendobjects.special;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class ErrorMessageBody extends MessageBody {
    String error;

    public ErrorMessageBody(String error) {
        this.error = error;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(error);
        return content;
    }

}
