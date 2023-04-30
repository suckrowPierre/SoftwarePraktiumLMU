package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class ChooseRegister extends ProtocolSendObject {
    public ChooseRegister(int register) {
        this.messageType = MessageType.ChooseRegister;
        this.messageBody = new ChooseRegisterMessageBody(register);
    }

    public int getRegister() {return (int) messageBody.getBodyContent().get(0);}
}
