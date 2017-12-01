package biz.michalowski.geometry;

public interface Boundary {

    BoundingBox getBoundingBox();

    interface BoundingBox {

        boolean contains(Point point);

        double left();

        double right();

        double top();

        double bottom();
    }
}
