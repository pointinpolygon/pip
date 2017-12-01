package biz.michalowski.geometry;

public interface Polygon {

    interface Polygonable {
        Polygon toPolygon();
    }

    boolean contains(Point point);

    boolean intersects(BoundingBox boundingBox);

}
