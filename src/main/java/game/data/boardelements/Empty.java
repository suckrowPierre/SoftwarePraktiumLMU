package game.data.boardelements;

/**
 * This class implements the BoardElement Empty
 *
 * @author Pierre, Simon Hümmer
 */
public class Empty extends BoardElement {

    public Empty(String isOnBoard) {
        super(BoardElementTypes.Empty, isOnBoard);
    }

    @Override
    public String subClassToString() {
        return null;
    }
}
