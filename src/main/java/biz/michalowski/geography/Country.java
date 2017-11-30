package biz.michalowski.geography;

import biz.michalowski.geometry.Boundary;

public class Country implements Boundary {

    interface Borders {
        boolean contains(Coordinate coordinate);
    }

    private final String name;
    private final Borders borders;
    private final BoundingBox boundingBox;

    Country(String name, Borders borders, BoundingBox boundingBox) {
        this.name = name;
        this.borders = borders;
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
        return borders.contains(coordinate);
    }

    @Override
    public String toString() {
        return name;
    }
}
