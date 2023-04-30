package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class CardSelected extends ProtocolSendObject {
    public CardSelected(int clientID, int register, boolean filled) {
        this.messageType = MessageType.CardSelected;
        this.messageBody = new CardSelectedMessageBody(clientID, register, filled);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public int getRegister() {return (int) messageBody.getBodyContent().get(1);}
    public boolean getFilled() {return (boolean) messageBody.getBodyContent().get(2);}
}


