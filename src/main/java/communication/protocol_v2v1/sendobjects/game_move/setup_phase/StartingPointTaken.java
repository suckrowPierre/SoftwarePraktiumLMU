package communication.protocol_v2v1.sendobjects.game_move.setup_phase;


import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class StartingPointTaken extends ProtocolSendObject {
    public StartingPointTaken(int clientID,int x, int y) {
        this.messageType = MessageType.StartingPointTaken;
        this.messageBody = new StartingPointTakenMessageBody(clientID, x,y);
    }

    public int getX() {return (int) messageBody.getBodyContent().get(0);}
    public int getY() {return (int) messageBody.getBodyContent().get(1);}
    public int getClientID() {return (int) messageBody.getBodyContent().get(2);}
}


