package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.Orientations;
import game.data.Position;
import javafx.geometry.Pos;

public class LaserAnimation extends ProtocolSendObject {


    public LaserAnimation(Position startLaser, Position endLaser, Orientations orientation) {
        this.messageType = MessageType.LaserAnimation;
        this.messageBody = new LaserAnimationMessageBody(startLaser,endLaser,orientation);
    }

    public Position getStartLaser() {return (Position) messageBody.getBodyContent().get(0);}
    public Position getEndLaser() {return (Position) messageBody.getBodyContent().get(1);}
    public Orientations getOrientation() {return (Orientations) messageBody.getBodyContent().get(2);}
}
