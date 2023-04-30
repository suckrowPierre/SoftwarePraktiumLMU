package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class AnimationMessageBody extends MessageBody {
    private String type;

    public AnimationMessageBody(String type) {
        this.type = type;
    }


    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(type);
        return content;
    }
}
