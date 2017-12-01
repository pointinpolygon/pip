package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.Polygon;
import biz.michalowski.geometry.SimpleBoundingBox;
import biz.michalowski.geometry.TestPolygon;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuadtreeCanvasTest {

    private final SimpleBoundingBox coveredArea = new SimpleBoundingBox(-100, 100, -100, 100);
    private final QuadtreeCanvas<Polygon.Polygonable> canvas = new QuadtreeCanvas<>(coveredArea, 10);

    private final Polygon.Polygonable outerBox = TestPolygon.from(0d, 100d, 0d, 100d);
    private final Polygon.Polygonable innerBox = TestPolygon.from(10d, 60d, 20d, 80d);
    private final Polygon.Polygonable middleBox = TestPolygon.from(-10d, 10d, -10d, 10d);

    @Test
    public void finds_BoundingBox_when_point_is_inside() {
        canvas.init(Collections.singletonList(outerBox));

        Point point = new Point(50d, 50d);
        List<Polygon.Polygonable> matching = canvas.findContaining(point).collect(Collectors.toList());

        assertThat(matching, is(Collections.singletonList(outerBox)));
    }

    @Test
    public void finds_no_BoundingBox_when_point_is_outside() {
        canvas.init(Collections.singletonList(outerBox));

        Point point = new Point(250d, 50d);
        List<Polygon.Polygonable> matching = canvas.findContaining(point).collect(Collectors.toList());

        assertThat(matching, is(Collections.emptyList()));
    }

    @Test
    public void finds_two_BoundingBox_when_both_matching() {
        canvas.init(Arrays.asList(outerBox, innerBox));

        Point point = new Point(50d, 50d);
        List<Polygon.Polygonable> matching = canvas.findContaining(point).collect(Collectors.toList());

        assertThat(matching, is(Arrays.asList(outerBox, innerBox)));
    }

    @Test
    public void quadtree_with_one_item_is_leaf() {
        canvas.init(Collections.singletonList(outerBox));

        assertTrue(canvas.isLeaf());
    }

    @Test
    public void quadtree_with_two_items_is_forked() {
        canvas.init(Arrays.asList(outerBox, innerBox));

        assertFalse(canvas.isLeaf());
    }

    @Test
    public void polygon_in_the_middle_ends_up_in_all_subnodes() {
        canvas.init(Arrays.asList(innerBox, middleBox));
        List<Point> points = Arrays.asList(
                new Point(1, 1),
                new Point(-1, 1),
                new Point(1, -1),
                new Point(-1, -1)
        );

        List<Polygon.Polygonable> items = points.stream().flatMap(point -> canvas.findContaining(point)).collect(Collectors.toList());

        assertThat(items, is(Arrays.asList(middleBox, middleBox, middleBox, middleBox)));
    }

    @Test
    public void finds_a_point_on_the_edge() {
        canvas.init(Arrays.asList(innerBox, middleBox));
        Point point = new Point(0, 0);

        List<Polygon.Polygonable> items = canvas.findContaining(point).collect(Collectors.toList());

        assertThat(items, is(Collections.singletonList(middleBox)));
    }
}
