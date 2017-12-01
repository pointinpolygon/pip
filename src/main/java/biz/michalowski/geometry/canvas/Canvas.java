package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Boundary;
import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.SimpleBoundingBox;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Canvas<T extends Boundary> {

    interface CanvasFactory<T extends Boundary> extends Supplier<Canvas<T>> {

        static <T extends Boundary> CanvasFactory<T> getDefault() {
            return naive();
        }

        static <T extends Boundary> CanvasFactory<T> naive() {
            return NaiveCanvas::new;
        }

        static <T extends Boundary> CanvasFactory<T> binsearch() {
            return BinsearchCanvas::new;
        }

        static <T extends Boundary> CanvasFactory<T> quadtree() {
//            SimpleBoundingBox area = new SimpleBoundingBox(MIN_VALUE, MIN_VALUE, MAX_VALUE, MAX_VALUE);
            SimpleBoundingBox area = new SimpleBoundingBox(-180, -90, 180, 90);
            return () -> new QuadtreeCanvas<>(area, 10);
        }
    }

    void init(List<T> boundaries);

    Stream<T> findPossibleContainers(Point point);

}
