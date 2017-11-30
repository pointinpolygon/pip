package biz.michalowski.geometry;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BoundingBoxLookup<T> {

    private final Map<BoundingBox, T> boundingBoxes = new HashMap<>();

    public void put(BoundingBox boundingBox, T value) {
        boundingBoxes.put(boundingBox, value);
    }

    public Stream<T> find(Point point) {
        return boundingBoxes
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(point))
                .map(Map.Entry::getValue);
    }
}
