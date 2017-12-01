package biz.michalowski.geography;

import biz.michalowski.geometry.Boundary;

public class Country implements Boundary {

    interface Border {
        boolean contains(Coordinate coordinate);
    }

    private final String name;
    private final Border border;
    private final BoundingBox boundingBox;

    Country(String name, Border border, BoundingBox boundingBox) {
        this.name = name;
        this.border = border;
        this.boundingBox = boundingBox;
    }

    public String getName() {
        return name;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    boolean contains(Coordinate coordinate) {
        return border.contains(coordinate);
    }

    @Override
    public String toString() {
        return name;
    }
}
