package biz.michalowski.geo;

import biz.michalowski.geometry.BoundingBox;

class Country {

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

    String getName() {
        return name;
    }

    BoundingBox getBoundingBox() {
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
