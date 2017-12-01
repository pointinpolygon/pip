package biz.michalowski.geometry;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;

public class SimpleBoundingBox implements BoundingBox {

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;

    public SimpleBoundingBox(double minX, double maxX, double minY, double maxY) {
        Preconditions.checkArgument(minX <= maxX);
        Preconditions.checkArgument(minY <= maxY);
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public boolean contains(Point point) {
        return this.minX <= point.x && point.x <= this.maxX
                && this.minY <= point.y && point.y <= this.maxY;
    }

    public List<SimpleBoundingBox> fork() {
        double newWidth = (maxX - minX) / 2;
        double newHeight = (maxY - minY) / 2;
        return Arrays.asList(
                new SimpleBoundingBox(minX, minX + newWidth, maxY - newHeight, maxY),
                new SimpleBoundingBox(minX + newWidth, maxX, maxY - newHeight, maxY),
                new SimpleBoundingBox(minX, minX + newWidth, minY, minY + newHeight),
                new SimpleBoundingBox(minX + newWidth, maxX, minY, minY + newHeight)
        );
    }

    boolean intersects(BoundingBox boundingBox) {
        return minX < boundingBox.maxX()
                && maxX > boundingBox.minX()
                && maxY > boundingBox.minY()
                && minY < boundingBox.maxY();
    }

    @Override
    public double minX() {
        return minX;
    }

    @Override
    public double maxX() {
        return maxX;
    }

    @Override
    public double maxY() {
        return maxY;
    }

    @Override
    public double minY() {
        return minY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleBoundingBox that = (SimpleBoundingBox) o;

        if (Double.compare(that.minX, minX) != 0) return false;
        if (Double.compare(that.maxX, maxX) != 0) return false;
        if (Double.compare(that.minY, minY) != 0) return false;
        return Double.compare(that.maxY, maxY) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(minX);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", minX, maxX, minY, maxY);
    }
}
