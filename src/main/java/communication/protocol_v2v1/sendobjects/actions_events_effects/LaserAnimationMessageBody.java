package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;
import game.data.Orientations;
import game.data.Position;

import java.util.ArrayList;

public class LaserAnimationMessageBody extends MessageBody {
    private Position startLaser;
    private Position endLaser;
    private Orientations orientation;

    public LaserAnimationMessageBody(Position startLaser,Position endLaser, Orientations orientation) {
    this.startLaser = startLaser;
    this.endLaser = endLaser;
    this.orientation = orientation;
    }


    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(startLaser);
        content.add(endLaser);
        content.add(orientation);
        return content;
    }
}
