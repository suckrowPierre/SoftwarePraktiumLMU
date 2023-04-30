package game.data;

import game.data.boardelements.BoardElement;

public class BoardElementPlusPosition {
    BoardElement boardElement;
    Position position;

    public BoardElementPlusPosition(BoardElement boardElement, Position position) {
        this.boardElement = boardElement;
        this.position = position;
    }

    public BoardElement getBoardElement() {
        return boardElement;
    }

    public Position getPosition() {
        return position;
    }
}
