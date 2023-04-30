package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import game.data.Orientations;

/** @author Sarah, Tea GUI representation of gears */
public class GUIGear extends GUIBoardElement {

    private static Image fillcw = new Image("/BoardElements/GearCW.png");
    private static Image fillccw = new Image("/BoardElements/GearCCW.png");

    public GUIGear(int x, int y, Orientations[] direction){
        super(BoardGUI.TILESIZE, x, y);
        setStroke(Color.TRANSPARENT);
        switch (direction[0]){
            case clockwise -> setFill(new ImagePattern(fillcw));
            case counterclockwise -> setFill(new ImagePattern(fillccw));
        }
    }
}
