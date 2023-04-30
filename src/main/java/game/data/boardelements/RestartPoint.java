package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement RestartPoint
 *
 * @author Pierre, Simon Hümmer
 */
public class RestartPoint extends BoardElement {

    final private Orientations[] orientations;

    public RestartPoint(String isOnBoard, Orientations[] orientations) {

        super(BoardElementTypes.RestartPoint, isOnBoard);
        this.orientations = orientations;
    }

    public Orientations[] getOrientations() {
        return orientations;
    }

    @Override
    public String subClassToString() {
        return " , orientations = " + Arrays.toString(orientations);
    }
}
