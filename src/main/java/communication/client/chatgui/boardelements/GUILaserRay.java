package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import game.data.Orientations;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/** @author Sarah, Tea GUI representation of laser (just the continued ray) */
public class GUILaserRay extends GUIBoardElement {

    private static Image one = new Image("/BoardElements/lasers/Laser1.png");
    private static Image two = new Image("/BoardElements/lasers/Laser2.png");
    private static Image three = new Image("/BoardElements/lasers/Laser3.png");

    public GUILaserRay(int x, int y, int count, Orientations[] orientation) {
        super(BoardGUI.TILESIZE, x, y);
        setStroke(Color.TRANSPARENT);
        switch (count) {
            case 1 -> setFill(new ImagePattern(one));
            case 2 -> setFill(new ImagePattern(two));
            default -> setFill(new ImagePattern(three));
        }
        if (orientation[0] == Orientations.top || orientation[0] == Orientations.bottom){
            setRotate(90);
        }
    }
}
