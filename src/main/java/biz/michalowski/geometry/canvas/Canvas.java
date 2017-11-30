package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Boundary;
import biz.michalowski.geometry.Point;

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
    }

    void init(List<T> boundaries);

    Stream<T> findPossibleContainers(Point point);

}
