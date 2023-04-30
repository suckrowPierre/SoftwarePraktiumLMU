package communication.client.chatgui;

import communication.client.data_handlers.GameDataOutHandeler;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import communication.client.*;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.LoggingTags;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Sarah, Tea
 * Controller for the lobby
 */
public class LobbyController {
    @FXML
    private Button readybtn;
    @FXML
    private Label status0, status1, status2, status3, status4, status5, name0, name1, name2, name3, name4, name5;
    @FXML
    private Circle circle0, circle1, circle2, circle3, circle4, circle5;
    @FXML
    private LinearGradient gradient;
    @FXML private ChoiceBox choicebox;
    @FXML private Button gomapbutton;
    @FXML private Label choosemaptext;
    @FXML private ImageView mapview;
    @FXML private VBox choosemapbox;
    @FXML private Hyperlink hyperlinkrules;
    private Image robot0, robot1, robot2, robot3, robot4, robot5;
    private GuiUserClient client1;
    private BooleanProperty loadmapselection, loadmap;
    private ArrayList<Circle> circles;
    private ArrayList<Image> robots;

    /**
     * this method will always be called if the FXMLLoader loads a scene
     */
    public void initialize() {

        // Initalize all the bindings
        createBindings();

        circles = new ArrayList<Circle>(Arrays.asList(circle0, circle1, circle2, circle3, circle4, circle5));
        choicebox.setItems(Model.getInstance().getMaplist());
        client1 = Model.getInstance().getMyClient();
        loadmapselection = Model.getInstance().getLoadmapselection();
        loadmap = Model.getInstance().getLoadMap();

        createImages();
        setInitialRobotimages();
        putChangeListenersNewPlayers();
        putChangeListenersViewActions();
        mapview.setVisible(false);
        mapview.setManaged(false);
        setChoosemapvisibility(false);
    }

    private void createBindings() {
        ObservableMap<Integer, String> playerstatus = LobbyModel.getInstance().getPlayerstatuslist();
        ObservableMap<Integer, String> playernames = LobbyModel.getInstance().getPlayernamelist();
        status0.textProperty().bind(Bindings.valueAt(playerstatus, 0));
        status1.textProperty().bind(Bindings.valueAt(playerstatus, 1));
        status2.textProperty().bind(Bindings.valueAt(playerstatus, 2));
        status3.textProperty().bind(Bindings.valueAt(playerstatus, 3));
        status4.textProperty().bind(Bindings.valueAt(playerstatus, 4));
        status5.textProperty().bind(Bindings.valueAt(playerstatus, 5));
        name0.textProperty().bind(Bindings.valueAt(playernames, 0));
        name1.textProperty().bind(Bindings.valueAt(playernames, 1));
        name2.textProperty().bind(Bindings.valueAt(playernames, 2));
        name3.textProperty().bind(Bindings.valueAt(playernames, 3));
        name4.textProperty().bind(Bindings.valueAt(playernames, 4));
        name5.textProperty().bind(Bindings.valueAt(playernames, 5));
    }

    private void createImages(){
        robot0 = new Image("/Robots/robot1.png");
        robot1 = new Image("/Robots/robot2.png");
        robot2 = new Image("/Robots/robot3.png");
        robot3 = new Image("/Robots/robot4.png");
        robot4 = new Image("/Robots/robot5.png");
        robot5 = new Image("/Robots/robot6.png");
        robots = new ArrayList<Image>(Arrays.asList(robot0, robot1, robot2, robot3, robot4, robot5));
    }

    private void putChangeListenersNewPlayers(){
        name0.textProperty().addListener((ov, t, t1) -> {
            if(!t1.equals("No Player")){
                circle0.setFill(new ImagePattern(robot0));
            }
            else{
                circle0.setFill(gradient);
            }
        });
        name1.textProperty().addListener((ov, t, t1) -> {
            if(!t1.equals("No Player")){
                circle1.setFill(new ImagePattern(robot1));
            }
            else{
                circle1.setFill(gradient);
            }
        });
        name2.textProperty().addListener((ov, t, t1) -> {
            if(!t1.equals("No Player")){
                circle2.setFill(new ImagePattern(robot2));
            }
            else{
                circle2.setFill(gradient);
            }
        });
        name3.textProperty().addListener((ov, t, t1) -> {
            if(!t1.equals("No Player")){
                circle3.setFill(new ImagePattern(robot3));
            }
            else{
                circle3.setFill(gradient);
            }
        });
        name4.textProperty().addListener((ov, t, t1) -> {
            if(!t1.equals("No Player")){
                circle4.setFill(new ImagePattern(robot4));
            }
            else{
                circle4.setFill(gradient);
            }
        });
        name5.textProperty().addListener((ov, t, t1) -> {
            if(!t1.equals("No Player")){
                circle5.setFill(new ImagePattern(robot5));
            }
            else{
                circle5.setFill(gradient);
            }
        });
    }

    private void putChangeListenersViewActions(){
        //listens if the player has to choose the map
        loadmapselection.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                setChoosemapvisibility(true);
                Logger.tag(LoggingTags.gui.toString()).info("Loading GUI map selection.");
            }
        });

        //listens if the map should be loaded
        loadmap.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                closeLobbyandLoadGame();
                Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window for game and closing lobby");
            }
        });

        //listens what map is selected to load appropriate image
        choicebox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
                    boolean imagetoshow;
                    Image mapimage=null;
                    try {
                        String mapname= (String) choicebox.getItems().get((Integer) number2);
                        mapimage = new Image("/Maps/" + mapname + ".png");
                        imagetoshow = true;

                    }
                    catch (IllegalArgumentException | NullPointerException e){
                        imagetoshow = false;
                        mapview.setVisible(false);
                        mapview.setManaged(false);
                    }
                    if(imagetoshow){
                        mapview.setImage(mapimage);
                        mapview.setManaged(true);
                        mapview.setVisible(true);
                    }
                });
    }

    private void setInitialRobotimages(){
        ArrayList<Integer> usedrobots = LobbyModel.getInstance().getUsedRobots();
        for(int robotid: usedrobots) {
            circles.get(robotid).setFill(new ImagePattern(robots.get(robotid)));
        }

        accentuateOwnRobot();
    }

    private void accentuateOwnRobot() {
        int myrobot = Model.getInstance().getMyrobotid();
        circles.get(myrobot).setStrokeWidth(5.0);
        circles.get(myrobot).setStroke(Color.web("#FFB600"));
    }

    private void setChoosemapvisibility(boolean visible) {
        choosemapbox.setVisible(visible);
        choosemapbox.setManaged(visible);
        choicebox.setVisible(visible);
        choosemaptext.setVisible(visible);
        gomapbutton.setVisible(visible);
        choicebox.setManaged(visible);
        choosemaptext.setManaged(visible);
        gomapbutton.setManaged(visible);
    }

    /**
     * this function is called if the Ready/NotReady-button is pressed
     */
    @FXML
    public void readybtnPress(ActionEvent actionEvent) {
        if(readybtn.getText().equals("Ready")){
            GameDataOutHandeler.getInstance().setReady(true);
            LobbyModel.getInstance().setPlayerstatus(Model.getInstance().getMyrobotid(), true);
            readybtn.setText("Not ready");
        }
        else{
            GameDataOutHandeler.getInstance().setReady(false);
            LobbyModel.getInstance().setPlayerstatus(Model.getInstance().getMyrobotid(), false);
            readybtn.setText("Ready");
        }

    }

    /** this function is called if the map selection button is pressed */
    @FXML
    public void gomapbtnpressed(ActionEvent actionEvent) {
        if (choicebox.getSelectionModel().getSelectedItem()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose a map");
            alert.showAndWait();
        } else {
            String selectedmap = (String) choicebox.getSelectionModel().getSelectedItem();
            GameDataOutHandeler.getInstance().sendMapselection(selectedmap);
            //stop showing the selection
            Model.getInstance().setChooseMap(false);
            setChoosemapvisibility(false);
            mapview.setVisible(false);
            mapview.setManaged(false);
        }
    }

    /** this function is called if the hyperlink is clicked */
    @FXML
    public void openLink(ActionEvent actionEvent) {
        try{
            Desktop.getDesktop().browse(new URI("https://media.wizards.com/2017/rules/roborally_rules.pdf"));
        }
        catch (Exception e){
            System.out.println("URL could not be opened.");
        }
    }

    private void closeLobbyandLoadGame(){
        Parent root;
        //show the Board
        try {
            root = FXMLLoader.load(getClass().getResource("/board.fxml"));
            Stage stage = (Stage) readybtn.getScene().getWindow();
            String maptitle = "";
            if(Model.getInstance().getMapname() != null){
                maptitle = "         Map: "+ Model.getInstance().getMapname();
            }
            stage.setTitle("Roborally Board of "+ Model.getInstance().getusername().get() + maptitle);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
