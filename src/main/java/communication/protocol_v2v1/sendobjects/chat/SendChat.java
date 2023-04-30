package communication.protocol_v2v1.sendobjects.chat;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;


//Maybe Refactoring because of same name
public class SendChat extends ProtocolSendObject {

    public SendChat(String message, int to) {
        this.messageType = MessageType.SendChat;
        this.messageBody = new SendChatMessageBody(message,to);
    }

    public String getMessage(){
        return (String) messageBody.getBodyContent().get(0);
    }

    public int getTo(){
        return (int) messageBody.getBodyContent().get(1);
    }



}

