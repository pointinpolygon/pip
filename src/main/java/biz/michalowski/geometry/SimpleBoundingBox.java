package biz.michalowski.geometry;

import com.google.common.base.Preconditions;

public class SimpleBoundingBox implements BoundingBox {

    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;

    public SimpleBoundingBox(double x1, double y1, double x2, double y2) {
        Preconditions.checkArgument(x1 <= x2);
        Preconditions.checkArgument(y1 <= y2);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public boolean contains(Point point) {
        return this.x1 <= point.x && point.x <= this.x2
                && this.y1 <= point.y && point.y <= this.y2;
    }
}
