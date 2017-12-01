package biz.michalowski.geometry;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;

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

    public List<SimpleBoundingBox> divide() {
        double newWidth = (right - left) / 2;
        double newHeight = (bottom - top) / 2;
        return Arrays.asList(
                new SimpleBoundingBox(left, top, left + newWidth, top + newHeight),
                new SimpleBoundingBox(left + newWidth, top, right, top + newHeight),
                new SimpleBoundingBox(left, top + newHeight, left + newWidth, bottom),
                new SimpleBoundingBox(left + newWidth, top + newHeight, right, bottom)
        );
    }

    public boolean containsFully(Boundary.BoundingBox boundingBox) {
        return left < boundingBox.left()
                && top < boundingBox.top()
                && boundingBox.right() < right
                && boundingBox.bottom() < bottom;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", left, top, right, bottom);
    }
}
