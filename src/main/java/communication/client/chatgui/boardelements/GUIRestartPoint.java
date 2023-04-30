package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import game.data.Orientations;

/** @author Sarah, Tea GUI representation of restart point */
public class GUIRestartPoint extends GUIBoardElement {

    private static Image fill = new Image("/BoardElements/restartpoint.png");

    public GUIRestartPoint(int x, int y, Orientations[] direction){
        super(BoardGUI.TILESIZE, x, y);
        setFill(new ImagePattern(fill));
        setStroke(Color.BLACK);
        switch (direction[0]){
            case right -> setRotate(90);
            case left -> setRotate(270);
            case bottom -> setRotate(180);
        }
    }
}
