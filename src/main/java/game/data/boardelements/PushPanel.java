package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement PushPanel
 *
 * @author Pierre, Simon Hümmer
 */
public class PushPanel extends BoardElement {

    final private int[] registers;
    final private Orientations[] orientations;

    public PushPanel(String isOnBoard, Orientations[] orientation, int[] register) {
        super(BoardElementTypes.PushPanel, isOnBoard);
        this.registers = register;
        this.orientations = orientation;
    }

    public Orientations[] getOrientations() {
        return orientations;
    }

    public int[] getRegisters() {
        return registers;
    }

    @Override
    public String subClassToString() {
        return " , orientations = " + Arrays.toString(orientations) + " , register = " + Arrays.toString(registers);
    }
}
