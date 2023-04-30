package communication.protocol_v2v1.deserializers;

import com.google.gson.*;
import communication.protocol_v2v1.MessageBody;
import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.sendobjects.lobby.GameStartedMessageBody;
import game.data.GameMapJsonDeserializer;
import org.tinylog.Logger;
import util.LoggingTags;

import java.lang.reflect.Type;

/**
 * Json Deserializer for MessageBody of ProtocolSendObject
 * @author Pierre-Louis Suckrow
 */
public class MessageBodyDeserializer extends Deserializer implements JsonDeserializer<MessageBody> {

    private static final String MESSAGE_BODY = "MessageBody";

    @Override
    public MessageBody deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
        String messageType = json.getAsJsonObject().get("messageType").getAsString();
        Class<MessageBody> act = null;
        MessageType type = MessageType.valueOf(messageType);
        String filename = type + MESSAGE_BODY;
        try{
            act = getClassWithSubStructure(filename);
        }catch (Exception e){
            Logger.error(e);
            throw new JsonParseException("MessageBody File of messageType: " + type.toString() + "not found");
        }
        MessageBody body;
        JsonObject bodyjson = json.getAsJsonObject().get("messageBody").getAsJsonObject();
        if (type != MessageType.GameStarted) {
             body = context.deserialize(bodyjson, act);
        }else{
            body = new GameStartedMessageBody(GameMapJsonDeserializer.deserializeArray(bodyjson.getAsJsonArray("gameMap")));
        }

        return body;
    }

}
