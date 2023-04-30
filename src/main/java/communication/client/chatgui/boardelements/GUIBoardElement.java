package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.shape.Rectangle;

/** @author Sarah, Tea superclass for all elements on the board */
public class GUIBoardElement extends Rectangle {
    protected int x;
    protected int y;

    public GUIBoardElement(int kantenlaenge, int x, int y){
        super(kantenlaenge, kantenlaenge);
        this.x = x;
        this.y = y;
        int diff = BoardGUI.TILESIZE - kantenlaenge;
        relocate(x * BoardGUI.TILESIZE + (diff/2.0), y * BoardGUI.TILESIZE + (diff/2.0));
    }

    public int getxPos(){
        return x;
    }

    public int getYPos(){
        return y;
    }

}
