package communication.client.chatgui;

import game.data.Rotations;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.tinylog.Logger;
import util.LoggingTags;

/** @author Sarah, Tea GUI representation of robot on the board */
public class Robot extends Rectangle {
    private int x;
    private int y;
    private final int robotid;
    private Tooltip text;
    private String owner, lastcard, energy;

    public Robot(int id, int x, int y) {
        super(BoardGUI.TILESIZE, BoardGUI.TILESIZE);
        this.x = x;
        this.y = y;
        this.robotid = id;
        setVisible(false);
        Image robotimage;
        switch (id) {
            case 0 -> robotimage = new Image("/Robots/Rob.png");
            case 1 -> robotimage = new Image("/Robots/Rob2.png");
            case 2 -> robotimage = new Image("/Robots/Rob3.png");
            case 3 -> robotimage = new Image("/Robots/Rob4.png");
            case 4 -> robotimage = new Image("/Robots/Rob5.png");
            default -> robotimage = new Image("/Robots/Rob6.png");
        }
        setFill(new ImagePattern(robotimage));
        setStroke(Color.TRANSPARENT);
        relocate(x * BoardGUI.TILESIZE, y * BoardGUI.TILESIZE);
        owner = LobbyModel.getInstance().getPlayernamelist().get(id);
        lastcard = "";
        energy = "\nEnergy: 5";
        if (Model.getInstance().getMyrobotid()==robotid){
            owner = "You";
        }
        text = new Tooltip(owner+energy);
        text.setShowDelay(Duration.seconds(0.5));
        Tooltip.install(this, text);
    }

    /** moves the robot in an animated was to the specified coordingates - not currently used */
    public void moveanimate(int newx, int newy){
        Platform.runLater(()->{
            TranslateTransition trans = new TranslateTransition(Duration.millis(200), this);
            trans.setByX((newx-x)*BoardGUI.TILESIZE);
            trans.setByY((newy-y)*BoardGUI.TILESIZE);
            this.x = newx;
            this.y = newy;
            //relocate(x * BoardGUI.TILESIZE, y * BoardGUI.TILESIZE);
            trans.play();
        });

        Logger.tag(LoggingTags.gui.toString()).info("Robot " + robotid + " moved to : " + newx + "," +newy);

    }

    /** moves the robot to the specified coordingates*/
    public void move(int newx, int newy){
        this.x = newx;
        this.y = newy;
        relocate(x * BoardGUI.TILESIZE, y * BoardGUI.TILESIZE);
        Logger.tag(LoggingTags.gui.toString()).info("Robot " + robotid + " moved to : " + newx + "," +newy);
    }

    /** turns the robot 90 degrees in the given direction*/
    public void turn(Rotations orientation){
        double currentrotation = getRotate();
        switch (orientation){
            case clockwise -> setRotate((currentrotation+90)%360);
            case counterclockwise-> setRotate((currentrotation-90)%360);
        }
        Logger.tag(LoggingTags.gui.toString()).info("Robot " + robotid + " turned " + orientation.toString());

    }

    public void setVisibility(boolean isVisible){
        setVisible(isVisible);
        toFront();
    }

    public int getRobotid(){
        return robotid;
    }

    /** shows text when hovering over the robot */
    public void showTextInTooltip(String toshow){
        lastcard = "\n"+toshow;
        text.setText(owner+lastcard+energy);
    }

    /** changes energy point number when hovering over the robot */
    public void showEnergyPointsInTooltip(int points){
        energy = "\nEnergy: "+points;
        text.setText(owner+lastcard+energy);

    }

    public int getxPos(){
        return x;
    }

    public int getyPos(){
        return y;
    }
}
