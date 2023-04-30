package communication.protocol_v2v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import communication.protocol_v2v1.deserializers.MessageBodyDeserializer;
import communication.protocol_v2v1.deserializers.ProtocolSendObjectDeserializer;

import java.lang.reflect.Type;

public class SendObjectJsonWrapper {

    static Gson gson = new Gson();

    public static String wraptoString(ProtocolSendObject toWrap) {
        String json = gson.toJson(toWrap);
        return json;
    }

    public static JsonObject wrap(ProtocolSendObject toWrap) {
        String json = gson.toJson(toWrap);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        return jsonObject;
    }

    public static ProtocolSendObject dewrapString(String json){
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        return dewrap(jsonObject);
    }

    public static ProtocolSendObject dewrap(JsonObject jsonObject) {
        ProtocolSendObject dewrapped = deserializeProtocolSendObject(jsonObject);
        dewrapped.setMessageBody(deserializeMessagebody(jsonObject));
        return dewrapped;
    }

    private static ProtocolSendObject deserializeProtocolSendObject(JsonObject jsonObject){
        Type type = new TypeToken<ProtocolSendObject>() {}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(ProtocolSendObject.class, new ProtocolSendObjectDeserializer()).create();
        return gson.fromJson(jsonObject, type);
    }

    private static MessageBody deserializeMessagebody(JsonObject jsonObject){
        Type type = new TypeToken<MessageBody>() {}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(MessageBody.class, new MessageBodyDeserializer()).create();
        return gson.fromJson(jsonObject, type);
    }

}
