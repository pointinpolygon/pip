package biz.michalowski.geography;

import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.Polygon;

public class Country implements Polygon.Polygonable {

    private final String name;
    private final Polygon polygon;

    public Country(String name, Polygon polygon) {
        this.name = name;
        this.polygon = polygon;
    }

    public String getName() {
        return name;
    }

    @Override
    public Polygon toPolygon() {
        return polygon;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean contains(Coordinate coordinate) {
        return toPolygon().contains(new Point(coordinate.lon, coordinate.lat));
    }
}
