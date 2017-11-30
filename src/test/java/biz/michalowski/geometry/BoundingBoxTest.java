package biz.michalowski.geometry;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoundingBoxTest {

    private final SimpleBoundingBox boundingBox = new SimpleBoundingBox(1, 1, 20, 200);

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
        new SimpleBoundingBox(10, 10, 5, 15);
    }
}