package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import game.data.Orientations;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/** @author Sarah, Tea GUI representation of conveyorbelt */
public class GUIConveyorBelt extends GUIBoardElement {
    public final String FOLDER = "/BoardElements/conveyorbelts/";

    public GUIConveyorBelt(int x, int y, Orientations[] orientation, int speed) {
        super(BoardGUI.TILESIZE, x, y);
        setStroke(Color.TRANSPARENT);
        if (speed==1){
            if(orientation.length==2){
                //straight belt
                if(areopposite(orientation[0], orientation[1])){
                    setFill(new ImagePattern(new Image(FOLDER + "ConveyorBeltStraight.png")));
                    switch (orientation[0]){
                        case left -> setRotate(270);
                        case right -> setRotate(90);
                        case bottom -> setRotate(180);
                    }
                }
                else {
                    //curved belt
                    setFill(new ImagePattern(new Image(FOLDER+ "ConveyorBeltTurn.png")));
                    switch (orientation[0]){
                        case top -> {
                            setRotate(90);
                            if(orientation[1]== Orientations.right) setScaleY(-1);
                        }
                        case right -> {
                            setRotate(180);
                            if(orientation[1]== Orientations.bottom) setScaleY(-1);
                        }
                        case bottom -> {
                            setRotate(270);
                            if(orientation[1]== Orientations.left) setScaleY(-1);
                        }
                        case left -> {
                            if(orientation[1]== Orientations.top) setScaleY(-1);
                        }
                    }
                }
            }
            else if(orientation.length==3){
                setFill(new ImagePattern(new Image(FOLDER+ "ConveyorBeltStraightTurn.png")));
                switch (orientation[0]){
                    case left -> setRotate(90);
                    case right -> setRotate(270);
                    case top -> setRotate(180);
                }
                if(imageneedsmirroring(orientation)){
                    setScaleX(-1);
                }
            }

        }
        else if (speed==2){
            if(orientation.length==2){
                //straight belt
                if(areopposite(orientation[0], orientation[1])){
                    setFill(new ImagePattern(new Image(FOLDER + "ConveyorBelt2Straight.png")));
                    switch (orientation[0]){
                        case left -> setRotate(270);
                        case right -> setRotate(90);
                        case bottom -> setRotate(180);
                    }
                }
                else {
                    //curved belt
                    setFill(new ImagePattern(new Image(FOLDER+ "ConveyorBelt2Turn.png")));
                    switch (orientation[0]){
                        case top -> {
                            setRotate(90);
                            if(orientation[1]== Orientations.right) setScaleY(-1);
                        }
                        case right -> {
                            setRotate(180);
                            if(orientation[1]== Orientations.bottom) setScaleY(-1);
                        }
                        case bottom -> {
                            setRotate(270);
                            if(orientation[1]== Orientations.left) setScaleY(-1);
                        }
                        case left -> {
                            if(orientation[1]== Orientations.top) setScaleY(-1);
                        }
                    }
                }
            }
            else if(orientation.length==3){
                setFill(new ImagePattern(new Image(FOLDER+ "ConveyorBelt2StraightTurn.png")));
                if(imageneedsmirroring(orientation)){
                    setScaleX(-1);
                }
                switch (orientation[0]){
                    case left -> setRotate(90);
                    case right -> setRotate(270);
                    case top -> setRotate(180);
                }
            }

        }

    }

    private boolean areopposite(Orientations orientation1, Orientations orientation2){
        return (orientation1 == Orientations.top && orientation2 == Orientations.bottom
        || orientation1 == Orientations.bottom && orientation2 == Orientations.top
        || orientation1 == Orientations.left && orientation2 == Orientations.right
        || orientation1 == Orientations.right && orientation2 == Orientations.left);
    }

    private boolean imageneedsmirroring(Orientations[] orientations){
        switch (orientations[0]){
            case top -> {
                return contains(orientations, Orientations.right);
            }
            case bottom -> {
                return contains(orientations, Orientations.left);
            }
            case left -> {
                return contains(orientations, Orientations.top);
            }
            case right -> {
                return contains(orientations, Orientations.bottom);
            }
            default -> {
                return false;
            }
        }
    }

    private boolean contains(Orientations[] orientations, Orientations search){
        for (Orientations o: orientations){
            if (o == search) return true;
        }
        return false;
    }
}
