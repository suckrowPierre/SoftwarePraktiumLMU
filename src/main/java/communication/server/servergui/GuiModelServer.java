package communication.server.servergui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** @author Sarah Stores data for the server GUI */
public class GuiModelServer {

    private static GuiModelServer instance;

    /**
     * Singleton
     *
     * @return instance of the server datamodel
     */
    public static GuiModelServer getInstance() {
        if (instance == null) instance = new GuiModelServer();
        return instance;
    }

    private StringProperty portnumber = new SimpleStringProperty("12345");

    public StringProperty getPortnumber(){
        return portnumber;
    }
}
