package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.Polygon;
import biz.michalowski.geometry.SimpleBoundingBox;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Canvas<T extends Polygon.Polygonable> {

    interface CanvasFactory<T extends Polygon.Polygonable> extends Supplier<Canvas<T>> {

        static <T extends Polygon.Polygonable> CanvasFactory<T> getDefault() {
            return quadtree();
        }

        static <T extends Polygon.Polygonable> CanvasFactory<T> naive() {
            return NaiveCanvas::new;
        }

        static <T extends Polygon.Polygonable> CanvasFactory<T> quadtree() {
            SimpleBoundingBox area = new SimpleBoundingBox(-180, 180, -90, 90);
            return () -> new QuadtreeCanvas<>(area, 10);
        }
    }

    void init(List<T> items);

    Stream<T> findContaining(Point point);

}
