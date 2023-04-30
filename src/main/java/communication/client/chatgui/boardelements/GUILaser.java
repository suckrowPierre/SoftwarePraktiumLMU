package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import game.data.Orientations;

/** @author Sarah, Tea GUI representation of laser (start point of ray) */
public class GUILaser extends GUIBoardElement {

    private static Image one = new Image("/BoardElements/lasers/BoardLaser1.png");
    private static Image two = new Image("/BoardElements/lasers/BoardLaser2.png");
    private static Image three = new Image("/BoardElements/lasers/BoardLaser3.png");

    public GUILaser(int x, int y, int count, Orientations[] orientation) {
        super(BoardGUI.TILESIZE, x, y);
        setStroke(Color.TRANSPARENT);
        switch (count) {
            case 1 -> setFill(new ImagePattern(one));
            case 2 -> setFill(new ImagePattern(two));
            default -> setFill(new ImagePattern(three));
        }
        switch (orientation[0]){
            case top -> setRotate(270);
            case bottom -> setRotate(90);
            case left -> setRotate(180);
            case right -> setRotate(0);
        }
    }
}
