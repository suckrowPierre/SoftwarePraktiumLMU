package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import game.data.Orientations;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/** @author Sarah, Tea GUI representation of push panel */
public class GUIPushPanel extends GUIBoardElement {

    private static final String IMAGEPATH = "/BoardElements/pushpanels/PushPanel";

    public GUIPushPanel(int x, int y, Orientations[] orientation, int[] register) {
        super(BoardGUI.TILESIZE, x, y);
        setStroke(Color.TRANSPARENT);
        Image fill;
        if (register.length==1){
            fill = new Image (IMAGEPATH+register[0]+".png");
        }
        else if(register.length==2){
            fill = new Image(IMAGEPATH+"24.png");
        }
        else { // Length 3
            fill = new Image(IMAGEPATH+"135.png");
        }
        setFill(new ImagePattern(fill));
        switch (orientation[0]){
            case top -> setRotate(270);
            case bottom -> setRotate(90);
            case left -> setRotate(180);
            case right -> setRotate(0);
        }
    }
}
