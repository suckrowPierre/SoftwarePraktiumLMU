package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.Rotations;

public class PlayerTurning extends ProtocolSendObject {
    public PlayerTurning(int clientID, Rotations rotation) {
        this.messageType = MessageType.PlayerTurning;
        this.messageBody = new PlayerTurningMessageBody(clientID, rotation);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public Rotations getRotation() {return (Rotations) messageBody.getBodyContent().get(1);}

}

