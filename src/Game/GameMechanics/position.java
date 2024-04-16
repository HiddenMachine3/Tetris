package Game.GameMechanics;

public class position {
    public int x, y;

    public position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static position add(position p1, position p2) {
        return new position (p1.x + p2.x, p1.y + p2.y);
    }

    public position copy() {
        return new position (x, y);
    }
}
