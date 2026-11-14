package components.path;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for all standard, iterable, and kernel methods defined in class Path1L.
 */
public class Path1LTest {
    /**
     * test NoArgs Constructor, making sure length is 0 and that two newly
     * constructed paths are equal.
     */
    @Test
    public void testNoArgsConstructor() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        assertEquals(pCopy, p);
        assertEquals(true, p.length() == 0);
    }

    /**
     * test length on path with zero points.
     */
    @Test
    public void testLengthZeroPoints() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        final int expectedLength = 0;
        assertEquals(true, p.length() == expectedLength);
        assertEquals(pCopy, p);
    }

    /**
     * test length on path with one point.
     */
    @Test
    public void testLengthOnePoint() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 0);
        pCopy.add(0, 0, 0);
        final int expectedLength = 1;
        assertEquals(true, p.length() == expectedLength);
        assertEquals(pCopy, p);
    }

    /**
     * test length on path with many points.
     */
    @Test
    public void testLengthManyPoints() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, -2, -3);
        pCopy.add(1, -2, -3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        final int expectedLength = 3;
        assertEquals(true, p.length() == expectedLength);
        assertEquals(pCopy, p);
    }

    /**
     * test add with one point.
     */
    @Test
    public void testAddOne() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        assertEquals(pCopy, p);
        assertEquals(true, p.hasPoint(0, 1));
    }

    /**
     * test add with one point with negative x coordinate.
     */
    @Test
    public void testAddOneXNeg() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, -1, 2);
        pCopy.add(0, -1, 2);
        assertEquals(pCopy, p);
        assertEquals(true, p.hasPoint(-1, 2));
    }

    /**
     * test add with one point with negative y coordinate.
     */
    @Test
    public void testAddOneYNeg() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 2, -1);
        pCopy.add(0, 2, -1);
        assertEquals(pCopy, p);
        assertEquals(true, p.hasPoint(2, -1));
    }

    /**
     * test add with one point with both negative coordinates.
     */
    @Test
    public void testAddOneBothNeg() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, -1, -3);
        pCopy.add(0, -1, -3);
        assertEquals(pCopy, p);
        assertEquals(true, p.hasPoint(-1, -3));
    }

    /**
     * test add with one point at integer limit.
     */
    @Test
    public void testAddOneLimit() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        int coordinate = Integer.MAX_VALUE;
        p.add(0, coordinate, coordinate);
        pCopy.add(0, coordinate, coordinate);
        assertEquals(pCopy, p);
        assertEquals(true, p.hasPoint(coordinate, coordinate));
    }

    /**
     * test add with many points.
     */
    @Test
    public void testAddMany() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        assertEquals(pCopy, p);
        assertEquals(true, p.hasPoint(0, 1));
        assertEquals(true, p.hasPoint(2, 3));
        assertEquals(true, p.hasPoint(4, 5));
    }

    /**
     * test remove with one point.
     */
    @Test
    public void testRemoveOne() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.remove(0);
        pCopy.remove(0);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(0, 1));
    }

    /**
     * test remove with many points.
     */
    @Test
    public void testRemoveMany() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        p.remove(2);
        pCopy.remove(2);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(4, 5));
        p.remove(1);
        pCopy.remove(1);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(2, 3));
        p.remove(0);
        pCopy.remove(0);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(0, 1));
    }

    /**
     * test remove with one unmodified point present.
     */
    @Test
    public void testRemoveOneTrailing() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.remove(1);
        pCopy.remove(1);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(2, 3));
    }

    /**
     * test remove from starting index with one unmodified point present.
     */
    @Test
    public void testRemoveOneStartIndexOneTrailing() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.remove(0);
        pCopy.remove(0);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(0, 1));
    }

    /**
     * test remove with many unmodified points present.
     */
    @Test
    public void testRemoveManyTrailing() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        p.add(3, 6, 7);
        pCopy.add(3, 6, 7);
        p.add(4, 8, 9);
        pCopy.add(4, 8, 9);
        p.add(5, 10, 11);
        pCopy.add(5, 10, 11);
        p.remove(2);
        pCopy.remove(2);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(4, 5));
        p.remove(1);
        pCopy.remove(1);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(2, 3));
        p.remove(0);
        pCopy.remove(0);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(0, 1));
    }

    /**
     * test remove from starting index with many unmodified points present.
     */
    @Test
    public void testRemoveManyStartIndexManyTrailing() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        p.add(3, 6, 7);
        pCopy.add(3, 6, 7);
        p.add(4, 8, 9);
        pCopy.add(4, 8, 9);
        p.add(5, 10, 11);
        pCopy.add(5, 10, 11);
        p.remove(0);
        pCopy.remove(0);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(0, 1));
        p.remove(0);
        pCopy.remove(0);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(2, 3));
        p.remove(0);
        pCopy.remove(0);
        assertEquals(pCopy, p);
        assertEquals(false, p.hasPoint(4, 5));
    }

    /**
     * test hasPoint false with zero starting points.
     */
    @Test
    public void testHasPointFalseZero() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        assertEquals(false, p.hasPoint(0, 0));
        assertEquals(pCopy, p);
    }

    /**
     * test hasPoint true with one starting point.
     */
    @Test
    public void testHasPointTrueOne() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 1, 2);
        pCopy.add(0, 1, 2);
        assertEquals(true, p.hasPoint(1, 2));
        assertEquals(pCopy, p);
    }

    /**
     * test hasPoint false with one starting point.
     */
    @Test
    public void testHasPointFalseOne() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 1, 2);
        pCopy.add(0, 1, 2);
        assertEquals(false, p.hasPoint(3, 4));
        assertEquals(pCopy, p);
    }

    /**
     * test hasPoint true with many starting points.
     */
    @Test
    public void testHasPointTrueMany() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        assertEquals(true, p.hasPoint(2, 3));
        assertEquals(pCopy, p);
    }

    /**
     * test hasPoint false with many starting points.
     */
    @Test
    public void testHasPointFalseMany() {
        Path p = new Path1L();
        Path pCopy = new Path1L();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        assertEquals(false, p.hasPoint(6, 7));
        assertEquals(pCopy, p);
    }

    //TODO: More test cases of course, but this is how far we got on November 13th.
}
