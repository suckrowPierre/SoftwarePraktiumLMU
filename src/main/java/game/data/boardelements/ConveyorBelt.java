package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement ConveyorBelt
 *
 * @author Pierre, Simon Hümmer
 */
public class ConveyorBelt extends BoardElement {

    final private int speed;
    final private Orientations[] orientations;

    public ConveyorBelt(String isOnBoard, Orientations[] orientations, int speed) {
        super(BoardElementTypes.ConveyorBelt, isOnBoard);
        this.speed = speed;
        this.orientations = orientations;
    }

    public int getSpeed() {
        return speed;
    }

    public Orientations[] getOrientations() {
        return orientations;
    }

    @Override
    public String subClassToString() {
        return " , orientations = " + Arrays.toString(orientations) + " ,speed = " + speed;
    }
}
