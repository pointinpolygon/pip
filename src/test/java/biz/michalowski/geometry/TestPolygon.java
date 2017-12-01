package biz.michalowski.geometry;

public class TestPolygon implements Polygon.Polygonable {

    private final SimpleBoundingBox boundingBox;

    private TestPolygon(SimpleBoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public static TestPolygon from(double minX, double maxX, double minY, double maxY) {
        return new TestPolygon(new SimpleBoundingBox(minX, maxX, minY, maxY));
    }

    @Override
    public String toString() {
        return boundingBox.toString();
    }

    @Override
    public Polygon toPolygon() {
        return new Polygon() {
            @Override
            public boolean contains(Point point) {
                return boundingBox.contains(point);
            }

            @Override
            public boolean intersects(BoundingBox inBoundingBox) {
                return boundingBox.intersects(inBoundingBox);
            }

        };
    }
}
