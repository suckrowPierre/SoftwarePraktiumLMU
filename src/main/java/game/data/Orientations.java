package game.data;

public enum Orientations {
    top, bottom, right, left, clockwise, counterclockwise;

    public static Orientations getOppositeOrientation(Orientations orientation) {
        switch (orientation) {

            case top -> {
                return bottom;
            }
            case bottom -> {
                return top;
            }
            case right -> {
                return left;
            }
            case left -> {
                return right;
            }
            case clockwise -> {
                return counterclockwise;
            }
            case counterclockwise -> {
                return clockwise;
            }
        }
        return null;
    }
}
