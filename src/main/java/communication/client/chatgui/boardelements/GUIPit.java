package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.*;

/** @author Sarah, Tea GUI representation of pit */
public class GUIPit extends GUIBoardElement {

    private static Image fill = new Image("/BoardElements/Pit.png");

    public GUIPit(int x, int y){
        super(BoardGUI.TILESIZE, x, y);
        setFill(new ImagePattern(fill));
        setStroke(Color.BLACK);
    }
}
