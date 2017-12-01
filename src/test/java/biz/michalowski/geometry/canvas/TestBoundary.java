package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Boundary;
import biz.michalowski.geometry.SimpleBoundingBox;

class TestBoundary implements Boundary {

    private final BoundingBox boundingBox;

    private TestBoundary(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    static Boundary from(final double left, final double top, final double right, final double bottom) {
        return new TestBoundary(new SimpleBoundingBox(left, top, right, bottom));
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    @Override
    public String toString() {
        return boundingBox.toString();
    }
}
