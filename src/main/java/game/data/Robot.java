package game.data;

/**
 * @author Simon Hümmer
 */
public class Robot {

    private Position position;
    private Orientations lineOfSight;
    private boolean rearLaser;

    public Robot(Position position, Orientations lineOfSight) {
        this.position = position;
        this.lineOfSight = lineOfSight;
        this.rearLaser = false;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Orientations getLineOfSight() {
        return lineOfSight;
    }

    public void setLineOfSight(Orientations lineOfSight) {
        this.lineOfSight = lineOfSight;
    }

    public boolean isRearLaser() {
        return rearLaser;
    }

    public void setRearLaser(boolean rearLaser) {
        this.rearLaser = rearLaser;
    }
}
