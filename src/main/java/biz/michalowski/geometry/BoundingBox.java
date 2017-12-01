package biz.michalowski.geometry;

public interface BoundingBox {

    boolean contains(Point point);

    double minX();

    double maxX();

    double maxY();

    double minY();
}
