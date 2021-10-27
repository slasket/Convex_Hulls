public class Point implements Comparable<Point> {
    public float x;
    public float y;

    public Point(float x, float y){

        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) {
        return Float.compare(this.x, p.x);
    }

    @Override
    public String toString() {
        return x + ", " + y + ":";
    }
}
