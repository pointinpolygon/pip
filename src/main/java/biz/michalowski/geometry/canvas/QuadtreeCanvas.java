package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Boundary;
import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.SimpleBoundingBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuadtreeCanvas<T extends Boundary> implements Canvas<T> {

    private final List<T> directChildren = new ArrayList<>();
    private final int maxNodeSize = 4;
    private final int maxDepth;

    private final SimpleBoundingBox myArea;

    private List<QuadtreeCanvas<T>> subnodes = new ArrayList<>();

    public QuadtreeCanvas(SimpleBoundingBox myArea, int maxDepth) {
        this.myArea = myArea;
        this.maxDepth = maxDepth;
    }

    @Override
    public void init(List<T> boundaries) {
        boundaries.forEach(this::put);
    }

    private void put(T boundary) {
        directChildren.add(boundary);
        if (directChildren.size() > maxNodeSize && maxDepth > 0) {
            rebalance();
        }
    }

    private void rebalance() {
        if (subnodes.isEmpty()) {
            subnodes = myArea.divide().stream().map(area -> new QuadtreeCanvas<T>(area, maxDepth - 1)).collect(Collectors.toList());
        }

        Iterator<T> iterator = directChildren.iterator();

        while (iterator.hasNext()) {
            T child = iterator.next();
            Optional<QuadtreeCanvas<T>> matchingNode = subnodes.stream().filter(subnode -> subnode.myArea.containsFully(child.getBoundingBox())).findFirst();
            matchingNode.ifPresent(node -> {
                node.put(child);
                iterator.remove();
            });
        }
    }

    @Override
    public Stream<T> findPossibleContainers(Point point) {
        if (!myArea.contains(point)) {
            return Stream.empty();
        } else {
            return Stream.concat(
                subnodes.stream().flatMap(subnode -> subnode.findPossibleContainers(point)),
                directChildren.stream().filter(child -> child.getBoundingBox().contains(point))
            );
        }
    }
}
