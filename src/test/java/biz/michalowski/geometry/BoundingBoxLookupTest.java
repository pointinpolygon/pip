package biz.michalowski.geometry;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BoundingBoxLookupTest {

    @Test
    public void finds_BoundingBox_when_point_is_inside() {
        BoundingBoxLookup<String> boundingBoxLookup = new BoundingBoxLookup<>();
        boundingBoxLookup.put(new SimpleBoundingBox(0d, 0d, 100d, 100d), "box1");

        Point point = new Point(50d, 50d);
        List<String> matching = boundingBoxLookup.find(point).collect(Collectors.toList());

        assertThat(matching, is(Collections.singletonList("box1")));
    }

    @Test
    public void finds_no_BoundingBox_when_point_is_outside() {
        BoundingBoxLookup<String> boundingBoxLookup = new BoundingBoxLookup<>();
        boundingBoxLookup.put(new SimpleBoundingBox(0d, 0d, 100d, 100d), "box1");

        Point point = new Point(250d, 50d);
        List<String> matching = boundingBoxLookup.find(point).collect(Collectors.toList());

        assertThat(matching, is(Collections.emptyList()));
    }

    @Test
    public void finds_two_BoundingBox_when_both_matching() {
        BoundingBoxLookup<String> boundingBoxLookup = new BoundingBoxLookup<>();
        boundingBoxLookup.put(new SimpleBoundingBox(0d, 0d, 100d, 100d), "box1");
        boundingBoxLookup.put(new SimpleBoundingBox(10d, 20d, 80d, 60d), "box2");

        Point point = new Point(50d, 50d);
        List<String> matching = boundingBoxLookup.find(point).collect(Collectors.toList());

        assertThat(matching, is(Arrays.asList("box1", "box2")));
    }
}
