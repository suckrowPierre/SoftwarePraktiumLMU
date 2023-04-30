package communication.protocol_v2v1.sendobjects.game_move.setup_phase;


import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class SetStartingPoint extends ProtocolSendObject {
    public SetStartingPoint(int x, int y) {
        this.messageType = MessageType.SetStartingPoint;
        this.messageBody = new SetStartingPointMessageBody(x, y);
    }

    public int getX() {return (int) messageBody.getBodyContent().get(0);}
    public int getY() {return (int) messageBody.getBodyContent().get(1);}
    }


