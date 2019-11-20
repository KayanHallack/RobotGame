import java.util.Comparator;

public class Robot implements Comparable<Robot> {
    private int x;
    private int y;
    private double distanceBetween;
    private boolean dead = false;

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(double distanceBetween) {
        this.distanceBetween = distanceBetween;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        this.dead = true;
    }

    @Override
    public int compareTo(Robot o) {
        if (this.distanceBetween > o.distanceBetween) {
            return -1;
        }
        if (o.distanceBetween > this.distanceBetween) {
            return 1;
        }
        return 0;
    }
}
