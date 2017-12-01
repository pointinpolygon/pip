package biz.michalowski.geometry;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SimpleBoundingBoxTest {

    private final SimpleBoundingBox boundingBox = new SimpleBoundingBox(1, 20, 1, 200);

    @Test
    public void contains_the_point_inside() {
        assertTrue(boundingBox.contains(new Point(10, 30)));
    }

    @Test
    public void does_not_contain_the_point_outside() {
        assertFalse(boundingBox.contains(new Point(10, 0)));
    }

    @Test
    public void contains_the_point_on_the_top_left_corner() {
        assertTrue(boundingBox.contains(new Point(1, 1)));
    }

    @Test
    public void contains_the_point_on_the_top_right_corner() {
        assertTrue(boundingBox.contains(new Point(20, 1)));
    }

    @Test
    public void contains_the_point_on_the_bottom_left_corner() {
        assertTrue(boundingBox.contains(new Point(1, 200)));
    }

    @Test
    public void contains_the_point_on_the_bottom_right_corner() {
        assertTrue(boundingBox.contains(new Point(20, 200)));
    }

    @Test
    public void single_point_bounding_box_is_legal_and_contains_this_point() {
        SimpleBoundingBox singlePointBoundingBox = new SimpleBoundingBox(10, 10, 10, 10);

        assertTrue(singlePointBoundingBox.contains(new Point(10, 10)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannot_create_BoundingBox_with_unordered_corners() {
        new SimpleBoundingBox(10, 1, 5, 15);
    }

    @Test
    public void divides_bounding_box_into_4_equally_sized_parts() {
        SimpleBoundingBox boundingBox = new SimpleBoundingBox(0, 20, 0, 20);

        List<SimpleBoundingBox> divide = boundingBox.fork();

        assertThat(divide, is(Arrays.asList(
                new SimpleBoundingBox(0, 10, 10, 20),
                new SimpleBoundingBox(10, 20, 10, 20),
                new SimpleBoundingBox(0, 10, 0, 10),
                new SimpleBoundingBox(10, 20, 0, 10)
        )));
    }

    @Test
    public void two_overlyng_boxes_intersect() {
        assertTrue(boundingBox.intersects(new SimpleBoundingBox(0, 20, 0, 20)));
    }

    @Test
    public void two_separate_boxes_dont_intersect() {
        assertFalse(boundingBox.intersects(new SimpleBoundingBox(-20, -10, 0, 20)));
    }
}