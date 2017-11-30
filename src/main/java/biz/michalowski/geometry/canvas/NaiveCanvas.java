package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Boundary;
import biz.michalowski.geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class NaiveCanvas<T extends Boundary> implements Canvas<T> {

    private List<T> boundaries = new ArrayList<>();

    @Override
    public void init(List<T> boundaries) {
        this.boundaries = boundaries;
    }

    public Stream<T> findPossibleContainers(Point point) {
        return boundaries
                .stream()
                .filter(boundary -> boundary.getBoundingBox().contains(point));
    }
}
