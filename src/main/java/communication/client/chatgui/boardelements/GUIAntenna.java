package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import game.data.Orientations;

/** @author Sarah, Tea GUI representation of antenna */
public class GUIAntenna extends GUIBoardElement {

    private static Image fill = new Image("/BoardElements/antenne.png");
    private Orientations orientation;

    public GUIAntenna(int x, int y, Orientations[] direction){
        super(BoardGUI.TILESIZE, x, y);
        orientation = direction[0];
        setFill(new ImagePattern(fill));
        setStroke(Color.TRANSPARENT);
        switch (direction[0]){
            case right -> setRotate(90);
            case left -> setRotate(270);
            case bottom -> setRotate(180);
        }
    }

    public Orientations getOrientation(){
        return orientation;
    }
}
