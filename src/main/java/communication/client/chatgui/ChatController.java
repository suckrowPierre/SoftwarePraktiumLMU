package communication.client.chatgui;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** @author Sarah, Tea Controller for the chatroom */
public class ChatController {
  @FXML private VBox container;
  @FXML private ListView<String> list;
  @FXML private TextField input;
  @FXML private Button btn;
  private BooleanProperty loadmap;

  /** this method will always be called if the FXMLLoader loads a scene */
  public void initialize() {
    // Bindings
    list.itemsProperty().set(Model.getInstance().messagelistProperty());
    input.textProperty().bindBidirectional(Model.getInstance().getTextFieldContent());
    this.loadmap = Model.getInstance().getLoadMap();

    //listens if the map should be loaded and the extra chat closed
    loadmap.addListener((observableValue, aBoolean, t1) -> {
      if (t1){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
      }
    });

    //scroll Listview to bottom
    list.getItems().addListener(new ListChangeListener() {
      @Override
      public void onChanged(ListChangeListener.Change change) {
        Platform.runLater(()->list.scrollTo(list.getItems().size()));
      }
    });

    setupInput();
    setupList();
  }

  /** this function is called if the send button is pressed */
  @FXML
  public void btnPress(ActionEvent actionEvent) {
    if (Model.getInstance().getTextFieldContent().isEmpty().get()) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Input is empty");
      alert.showAndWait();
    } else {
      Model.getInstance().saveMessage(Model.getInstance().getTextFieldContent().get());
      Model.getInstance()
          .getMyClient()
          .sendMessage(Model.getInstance().getTextFieldContent().get());
      Model.getInstance().getTextFieldContent().set("");
    }

    input.requestFocus();
  }

  /** handles enter key in input TextField */
  private void setupInput() {
    input.setOnKeyPressed(
        keyEvent -> {
          if (keyEvent.getCode() == KeyCode.ENTER) {
            btnPress(null);
          }
        });
  }

  /** handles pressing DELETE Button in listview */
  private void setupList() {
    list.setOnKeyPressed(
        keyEvent -> {
          int index = list.getSelectionModel().getSelectedIndex();

          if (keyEvent.getCode() == KeyCode.DELETE && index != -1) {
            Model.getInstance().remove(index);
          }
        });
  }
}
