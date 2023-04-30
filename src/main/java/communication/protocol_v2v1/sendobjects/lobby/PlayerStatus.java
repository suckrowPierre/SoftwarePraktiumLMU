package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class PlayerStatus extends ProtocolSendObject {

    public PlayerStatus(int clientID, boolean ready) {
        this.messageType = MessageType.PlayerStatus;
        this.messageBody = new PlayerStatusMessageBody(clientID, ready);
    }

    public int getClientID(){
        return (int) messageBody.getBodyContent().get(0);
    }

    public boolean isReady(){
        return (boolean) messageBody.getBodyContent().get(1);
    }

}

