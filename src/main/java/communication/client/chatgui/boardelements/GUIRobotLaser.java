package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import game.data.Position;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import game.data.Orientations;
import javafx.util.Duration;

/** @author Sarah GUI representation of laser shot by robot */
public class GUIRobotLaser extends GUIBoardElement {
    private static Image laser = new Image("/Robots/RobotLaser.png");
    private Orientations orientation;

    public GUIRobotLaser(int xStart, int yStart, Orientations orientation) {
        super(BoardGUI.TILESIZE, xStart, yStart);
        this.orientation = orientation;
        setStroke(Color.TRANSPARENT);
        setFill(new ImagePattern(laser));
        switch (orientation){
            case top -> setRotate(270);
            case bottom -> setRotate(90);
            case left -> setRotate(180);
            case right -> setRotate(0);
        }
        setVisible(false);
    }

    public void shoot(Position start, Position end){
        x = start.getX();
        y = start.getY();
        int newx = end.getX();
        int newy = end.getY();
        relocate(x * BoardGUI.TILESIZE, y * BoardGUI.TILESIZE);
        setVisible(true);
        Timeline t = new Timeline();
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(500),
                new KeyValue(this.xProperty(), newx * BoardGUI.TILESIZE),
                new KeyValue(this.yProperty(), newy * BoardGUI.TILESIZE),
                new KeyValue(this.visibleProperty(), true)
        ));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(550), new KeyValue(this.visibleProperty(), false)));
        t.play();
    }

    public Orientations getOrientation(){
        return orientation;
    }
}
