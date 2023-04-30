package communication.client.data_handlers;

import communication.client.chatgui.Model;
import javafx.application.Platform;
import org.tinylog.Logger;
import util.LoggingTags;


public abstract class DataHandler {

  protected Model model_data;
  protected boolean view_connected;
  protected boolean game_connected;


  public void setmodel(Model model_data) {
    this.model_data = model_data;
    view_connected = true;
  }

  protected void outputMessage(String stringtoshow) {
    Logger.info("chatoutput: " + stringtoshow);
    if (view_connected) {
      updateView(stringtoshow);
    }
  }

  protected void updateView(String stringtoshow) {
    Platform.runLater(() -> model_data.saveMessage(stringtoshow));
  }
}
