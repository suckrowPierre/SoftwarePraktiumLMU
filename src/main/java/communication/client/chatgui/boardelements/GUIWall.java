package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import game.data.Orientations;

/** @author Sarah, Tea GUI representation of wall */
public class GUIWall extends GUIBoardElement {

    private static Image onewall = new Image("/BoardElements/Wall1.png");
    private static Image cornerwall = new Image("/BoardElements/Wall2.png");

    public GUIWall(int x, int y, Orientations[] orientation) {
        super(BoardGUI.TILESIZE, x, y);
        setStroke(Color.TRANSPARENT);
        if(orientation.length == 1)
        {
            setFill(new ImagePattern(onewall));
            switch (orientation[0]){
                case top -> setRotate(90);
                case bottom -> setRotate(270);
                case left -> setRotate(0);
                case right -> setRotate(180);
            }

        }
        else if(orientation.length==2){
            setFill(new ImagePattern(cornerwall));
            switch (orientation[0]){
                case top -> {
                    if(orientation[1] == Orientations.left) setRotate(90);
                    if(orientation[1] == Orientations.right) setRotate(180);
                }
                case bottom -> {
                    if(orientation[1] == Orientations.left) setRotate(0);
                    if(orientation[1] == Orientations.right) setRotate(270);
                }
                case left -> {
                    if(orientation[1] == Orientations.top) setRotate(90);
                    if(orientation[1] == Orientations.bottom) setRotate(0);
                }
                case right -> {
                    if(orientation[1] == Orientations.top) setRotate(180);
                    if(orientation[1] == Orientations.bottom) setRotate(270);
                }
            }

        }
    }

}
