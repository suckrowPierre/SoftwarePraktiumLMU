package communication.protocol_v2v1.deserializers;

import com.google.gson.*;
import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import org.tinylog.Logger;
import util.LoggingTags;

import java.lang.reflect.Type;


/**
 * Json Deserializer for ProtocolSendObject
 * @author Pierre-Louis Suckrow
 */
public class ProtocolSendObjectDeserializer extends Deserializer implements JsonDeserializer<ProtocolSendObject> {


    //TODO: Refactor duplicate code in here and MessageBodyDeserializer
    @Override
    public ProtocolSendObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String messageType = json.getAsJsonObject().get("messageType").getAsString();
        Class<ProtocolSendObject> act = null;
        if(messageType.equals("Error")){
            messageType = "ErrorMessage";
            try{
                act = getClassWithSubStructure(messageType);
            }catch (Exception e){
                Logger.error(e);
                throw new JsonParseException("MessageBody File of messageType: " + messageType + "not found");
            }

        }else {
            MessageType type = MessageType.valueOf(messageType);
            try{
                act = getClassWithSubStructure(type.toString());
            }catch (Exception e){
                Logger.error(e);
                throw new JsonParseException("MessageBody File of messageType: " + messageType + "not found");
            }
        }
        ProtocolSendObject toreturn = context.deserialize(json, act);
        return toreturn;
    }


}
