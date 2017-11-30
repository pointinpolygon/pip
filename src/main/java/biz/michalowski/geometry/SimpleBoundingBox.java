package biz.michalowski.geometry;

import com.google.common.base.Preconditions;

public class SimpleBoundingBox implements Boundary.BoundingBox {

    private final double left;
    private final double top;
    private final double right;
    private final double bottom;

    public SimpleBoundingBox(double left, double top, double right, double bottom) {
        Preconditions.checkArgument(left <= right);
        Preconditions.checkArgument(top <= bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public boolean contains(Point point) {
        return this.left <= point.x && point.x <= this.right
                && this.top <= point.y && point.y <= this.bottom;
    }

    @Override
    public double left() {
        return left;
    }

    @Override
    public double right() {
        return right;
    }

    @Override
    public double top() {
        return top;
    }

    @Override
    public double bottom() {
        return bottom;
    }
}
