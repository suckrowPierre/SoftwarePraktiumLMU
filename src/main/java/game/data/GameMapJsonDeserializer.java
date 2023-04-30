package game.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementJsonDeserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameMapJsonDeserializer {

    public static GameMap deserializeString(String mapString) {
        Type type = new TypeToken<ArrayList<ArrayList<ArrayList<BoardElement>>>>() {
        }.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(BoardElement.class, new BoardElementJsonDeserializer()).create();
        List<List<List<BoardElement>>> data = gson.fromJson(mapString, type);
        return new GameMap(data);
    }

    public static GameMap deserialize(JsonObject json) {
        Type type = new TypeToken<ArrayList<ArrayList<ArrayList<BoardElement>>>>() {
        }.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(BoardElement.class, new BoardElementJsonDeserializer()).create();
        List<List<List<BoardElement>>> data = gson.fromJson(json, type);
        return new GameMap(data);
    }

    public static GameMap deserializeArray(JsonArray json) {
        Type type = new TypeToken<ArrayList<ArrayList<ArrayList<BoardElement>>>>() {
        }.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(BoardElement.class, new BoardElementJsonDeserializer()).create();
        List<List<List<BoardElement>>> data = gson.fromJson(json, type);
        return new GameMap(data);
    }


}
