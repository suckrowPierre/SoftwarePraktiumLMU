package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;
import game.data.Direction;
import game.data.Orientations;

import java.util.ArrayList;

public class RebootDirectionMessageBody extends MessageBody {
    Orientations direction;

    public RebootDirectionMessageBody(Orientations direction) {
        this.direction = direction;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(direction);
        return content;
    }
}
