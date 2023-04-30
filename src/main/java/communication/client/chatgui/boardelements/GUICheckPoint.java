package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import org.tinylog.Logger;
import util.LoggingTags;

/** @author Sarah, Tea GUI representation of checkpoint */
public class GUICheckPoint extends GUIBoardElement {

    private int number;

    public GUICheckPoint(int x, int y, int count){
        super(BoardGUI.TILESIZE, x, y);
        number = count;
        Image fill = new Image(new StringBuilder().append("/BoardElements/checkpoints/").append(count).append(".png").toString());
        setFill(new ImagePattern(fill));
        setStroke(Color.BLACK);
    }

    /** moves the checkpoint to the specified coordinates*/
    public void move(int newx, int newy){
        x = newx;
        y = newy;
        relocate(x * BoardGUI.TILESIZE, y * BoardGUI.TILESIZE);
        Logger.tag(LoggingTags.gui.toString()).info("Check Point "+number+" moved to: "+newx+","+newy);
    }

    public int getCheckpointId(){
        return number;
    }
}