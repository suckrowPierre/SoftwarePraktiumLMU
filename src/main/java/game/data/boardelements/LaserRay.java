package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement LaserRay
 *
 * @author Pierre, Simon Hümmer
 */
public class LaserRay extends BoardElement {

    final private int count;
    final private Orientations[] orientations;

    public LaserRay(String isOnBoard, int count, Orientations[] orientations) {
        super(BoardElementTypes.LaserRay, isOnBoard);
        this.count = count;
        this.orientations = orientations;
    }

    public int getCount() {
        return count;
    }

    public Orientations[] getOrientations() {
        return orientations;
    }

    @Override
    public String subClassToString() {
        return " , orientations = " + Arrays.toString(orientations) + " ,count = " + count;
    }

}
