package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class NaiveCanvas<T extends Polygon.Polygonable> implements Canvas<T> {

    private List<T> polygons = new ArrayList<>();

    @Override
    public void init(List<T> items) {
        this.polygons = items;
    }

    public Stream<T> findContaining(Point point) {
        return polygons
                .stream()
                .filter(item -> item.toPolygon().contains(point));
    }
}
