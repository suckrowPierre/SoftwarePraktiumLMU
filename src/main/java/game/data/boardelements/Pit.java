package game.data.boardelements;

/**
 * This class implements the BoardElement Pit
 *
 * @author Pierre, Simon Hümmer
 */
public class Pit extends BoardElement {

    public Pit(String isOnBoard) {
        super(BoardElementTypes.Pit, isOnBoard);
    }

    @Override
    public String subClassToString() {
        return null;
    }
}
