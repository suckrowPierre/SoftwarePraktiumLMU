package game.data.boardelements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class BoardElementJsonDeserializer implements JsonDeserializer<BoardElement> {

    @Override
    public BoardElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BoardElementTypes type = BoardElementTypes.valueOf(json.getAsJsonObject().get("type").getAsString());
        Class<?> act;

        try {
            act = Class.forName("game.data.boardelements." + type);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("type not in use");
        }
        return context.deserialize(json, act);
    }

}
