package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.Polygon;
import biz.michalowski.geometry.SimpleBoundingBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class QuadtreeCanvas<T extends Polygon.Polygonable> implements Canvas<T> {

    private static final int LEAF_FORK_SIZE = 1;

    private final SimpleBoundingBox coveredArea;
    private final int depthLimit;

    private List<T> directItems = new ArrayList<>();
    private List<QuadtreeCanvas<T>> subnodes = new ArrayList<>();

    QuadtreeCanvas(SimpleBoundingBox coveredArea, int depthLimit) {
        this.coveredArea = coveredArea;
        this.depthLimit = depthLimit;
    }

    @Override
    public void init(List<T> items) {
        items.forEach(this::put);
    }

    private void put(T item) {
        if (isLeaf()) {
            directItems.add(item);
            if (directItems.size() > LEAF_FORK_SIZE && depthLimit > 0) {
                fork();
            }
        } else {
            subnodes.stream()
                    .filter(subnode -> subnode.canContain(item))
                    .forEach(subnode -> subnode.put(item));
        }
    }

    boolean isLeaf() {
        return subnodes.isEmpty();
    }

    private void fork() {
        List<T> itemsToAdd = new ArrayList<>(directItems);
        directItems = Collections.emptyList();
        initSubnodes();
        itemsToAdd.forEach(this::put);
    }

    private void initSubnodes() {
        subnodes = coveredArea
                .fork()
                .stream()
                .map(area -> new QuadtreeCanvas<T>(area, depthLimit - 1))
                .collect(Collectors.toList());
    }

    @Override
    public Stream<T> findContaining(Point point) {
        if (isLeaf()) {
            return directItems
                    .stream()
                    .filter(item -> item.toPolygon().contains(point));
        } else {
            return subnodes.stream()
                    .filter(subnode -> subnode.canContain(point))
                    .findFirst()
                    .map(subnode -> subnode.findContaining(point))
                    .orElse(Stream.empty());
        }
    }

    private boolean canContain(T item) {
        return item.toPolygon().intersects(coveredArea);
    }

    private boolean canContain(Point point) {
        return coveredArea.contains(point);
    }
}
