package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/** @author Sarah, Tea GUI representation of empty square */
public class GUITile extends GUIBoardElement {

    private static Image emptytile = new Image("/BoardElements/tile.png");

    public GUITile(int x, int y){
        super(BoardGUI.TILESIZE, x, y);
        setFill(new ImagePattern(emptytile));
        setStroke(Color.BLACK);
    }
}
