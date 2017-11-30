package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Boundary;
import biz.michalowski.geometry.Point;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BinsearchCanvas<T extends Boundary> implements Canvas<T> {

    private Map<Double, List<T>> map = new HashMap<>();
    private List<Double> index = new ArrayList<>();

    @Override
    public void init(List<T> boundaries) {
        index = Stream.concat(
                boundaries.stream().map(e -> e.getBoundingBox().left()),
                boundaries.stream().map(e -> e.getBoundingBox().right())
        ).sorted().collect(Collectors.toList());
        index.add(Double.MAX_VALUE);

        for (int i = 0; i < index.size() - 1; i++) {
            Double a = index.get(i);
            Double b = index.get(i + 1);

            List<T> matching = boundaries.stream()
                    .filter(e -> !(e.getBoundingBox().left() >= b || e.getBoundingBox().right() <= a))
                    .collect(Collectors.toList());
            map.put(a, matching);
        }
    }

    @Override
    public Stream<T> findPossibleContainers(Point point) {
        int insertionPoint = Collections.binarySearch(index, point.x);
        if (insertionPoint < 0) {
            insertionPoint = Math.abs(insertionPoint) - 1;
        }
        Double border = index.get(insertionPoint - 1);
        return map.get(border).stream().filter(e -> e.getBoundingBox().contains(point));
    }
}
