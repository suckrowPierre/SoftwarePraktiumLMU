package communication.client.chatgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.LoggingTags;

import java.io.IOException;
import java.util.Objects;

/** @author Sarah, Tea MainGUI class to create a client and its GUI */
public class MainGUI extends Application {
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
    if (!Model.getInstance().isAI() && Model.getInstance().getMyClient() != null){
      Model.getInstance().getMyClient().closeClient();
    }
  }

  /** creates the first scene (login page) */
  private void createScene(Stage stage) throws IOException {
    Parent root;
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
    stage.setTitle("Connect to Roborally");
    stage.setScene(new Scene(root));
  }
}
