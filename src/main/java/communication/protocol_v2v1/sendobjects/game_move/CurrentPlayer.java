package communication.protocol_v2v1.sendobjects.game_move;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class CurrentPlayer extends ProtocolSendObject {

    public CurrentPlayer(int clientID) {
        this.messageType = MessageType.CurrentPlayer;
        this.messageBody = new CurrentPlayerMessageBody(clientID);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}

    }



