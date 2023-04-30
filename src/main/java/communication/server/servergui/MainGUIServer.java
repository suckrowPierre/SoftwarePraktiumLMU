package communication.server.servergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.LoggingTags;

import java.io.IOException;
import java.util.Objects;

/** @author Sarah Main class to create a server and its GUI */
public class MainGUIServer extends Application {
    /**
     * this main function starts the JavaFX application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        // do some pre work
        Logger.tag(LoggingTags.gui.toString()).info("init()");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // here the GUI magic happens
        Logger.tag(LoggingTags.gui.toString()).info("start()");

        // creating scene into primaryStage
        createScene(primaryStage);
        // showing the stage on the screen
        primaryStage.show();
    }

    @Override
    public void stop() {
        // do some manual cleanup
        Logger.tag(LoggingTags.gui.toString()).info("stop()");
    }

    /** creates the first scene (login page) */
    private void createScene(Stage stage) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/serverstart.fxml")));
        stage.setTitle("Roborally Server");
        stage.setScene(new Scene(root));
    }
}
