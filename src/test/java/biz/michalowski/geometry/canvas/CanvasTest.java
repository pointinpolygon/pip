package biz.michalowski.geometry.canvas;

import biz.michalowski.geometry.Boundary;
import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.SimpleBoundingBox;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CanvasTest {

//    private final Canvas<String> canvas = new NaiveCanvas<>();
    private final Canvas<Boundary> canvas = new BinsearchCanvas<>();

    private final Boundary outerBox = boundary(0d, 0d, 100d, 100d);
    private final Boundary innerBox = boundary(10d, 20d, 80d, 60d);

    @Test
    public void finds_BoundingBox_when_point_is_inside() {
        canvas.init(Collections.singletonList(outerBox));

        Point point = new Point(50d, 50d);
        List<Boundary> matching = canvas.findPossibleContainers(point).collect(Collectors.toList());

        assertThat(matching, is(Collections.singletonList(outerBox)));
    }

    @Test
    public void finds_no_BoundingBox_when_point_is_outside() {
        canvas.init(Collections.singletonList(outerBox));

        Point point = new Point(250d, 50d);
        List<Boundary> matching = canvas.findPossibleContainers(point).collect(Collectors.toList());

        assertThat(matching, is(Collections.emptyList()));
    }

    @Test
    public void finds_two_BoundingBox_when_both_matching() {
        canvas.init(Arrays.asList(outerBox, innerBox));

        Point point = new Point(50d, 50d);
        List<Boundary> matching = canvas.findPossibleContainers(point).collect(Collectors.toList());

        assertThat(matching, is(Arrays.asList(outerBox, innerBox)));
    }

    private Boundary boundary(final double left, final double top, final double right, final double bottom) {
        return () -> new SimpleBoundingBox(left, top, right, bottom);
    }
}
