package communication.client.chatgui;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.util.ArrayList;

/** @author Sarah, Tea
 * Holds the data of the lobby in the user interface */
public class LobbyModel {

    /**
     * Initializes the list of playernames and their status
     */
    public LobbyModel(){
        for(int i = 0; i<6; i++){
            playernamelist.put(i, "No Player");
            playerstatuslist.put(i, "Not Ready");
        }

    }
    private static LobbyModel instance;

    /**
     * Singleton
     *
     * @return instance of the model
     */
    public static LobbyModel getInstance() {
        if (instance == null) instance = new LobbyModel();
        return instance;
    }

    /** Hashmap of playernames we want to show on screen */
    private final ObservableMap<Integer, String> playernamelist = FXCollections.observableHashMap();


    public ObservableMap<Integer, String> getPlayernamelist() {
        return playernamelist;
    }

    public void setPlayername(int robotid, String name){
        playernamelist.put(robotid,name);
    }

    /** Hashmap of the player's state we want to show on screen */
    private final ObservableMap<Integer, String> playerstatuslist = FXCollections.observableHashMap();

    public ObservableMap<Integer, String> getPlayerstatuslist(){
        return playerstatuslist;
    }

    public void setPlayerstatus(int robotid, boolean status){
        if (status){
            playerstatuslist.put(robotid,"Ready");
        }
        else{
            playerstatuslist.put(robotid,"Not Ready");
        }
    }

    /** Sets both Hashmaps to their empty default values */
    public void clearlists(){
        for(int i = 0; i<6; i++){
            playernamelist.put(i, "No Player");
            playerstatuslist.put(i, "Not Ready");
        }
    }

    /** Returns Arraylist of robots that are currently in use */
    public ArrayList<Integer> getUsedRobots(){
        ArrayList<Integer> usedrobots = new ArrayList<Integer>();
        for(int i = 0; i<6; i++){
            if (!playernamelist.get(i).equals("No Player")){
                usedrobots.add(i);
            }
        }
        return usedrobots;
    }

    private BooleanProperty loadlobby = new SimpleBooleanProperty(false);

    public BooleanProperty getLoadlobby(){
        return loadlobby;
    }

    public void setChooserobotsuccessful(){
        loadlobby.set(true);
    }

    private StringProperty errormessage = new SimpleStringProperty("Ok");

    public StringProperty getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String message){
        errormessage.set(message);
    }
}
