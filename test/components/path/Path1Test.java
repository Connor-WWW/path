package components.path;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

import components.path.Path.Point;

/**
 * Tests for all standard, iterable, and kernel methods defined in class Path1L.
 */
public class Path1Test {
    /**
     * test NoArgs Constructor, making sure length is 0 and that two newly
     * constructed paths are equal.
     */
    @Test
    public void testNoArgsConstructor() {
        Path p = new Path1();
        Path pCopy = new Path1();
        assertEquals(pCopy, p);
        assertEquals(true, p.length() == 0);
    }

    /**
     * test length on path with zero points.
     */
    @Test
    public void testLengthZeroPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        final int expectedLength = 0;
        assertEquals(true, p.length() == expectedLength);
        assertEquals(pCopy, p);
    }

    /**
     * test length on path with one point.
     */
    @Test
    public void testLengthOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
        assertEquals(false, p.hasPoint(0, 0));
        assertEquals(pCopy, p);
    }

    /**
     * test hasPoint true with one starting point.
     */
    @Test
    public void testHasPointTrueOne() {
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
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
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        assertEquals(false, p.hasPoint(6, 7));
        assertEquals(pCopy, p);
    }

    /**
     * test newInstance with empty paths.
     */
    @Test
    public void testNewInstanceNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p = pCopy.newInstance();
        assertEquals(pCopy, p);
        pCopy = p.newInstance();
        assertEquals(pCopy, p);
    }

    /**
     * test newInstance with paths with one point.
     */
    @Test
    public void testNewInstanceOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        p = pCopy.newInstance();
        assertEquals(pCopy, p);
        pCopy = p.newInstance();
        assertEquals(pCopy, p);
    }

    /**
     * test newInstance with paths with many points.
     */
    @Test
    public void testNewInstanceManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        p.add(1, 2, 3);
        p.add(2, 4, 5);
        p = pCopy.newInstance();
        assertEquals(pCopy, p);
        pCopy = p.newInstance();
        assertEquals(pCopy, p);
    }

    /**
     * test clear with empty paths.
     */
    @Test
    public void testClearNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.clear();
        assertEquals(pCopy, p);
        pCopy.clear();
        assertEquals(pCopy, p);
    }

    /**
     * test clear with paths with one point.
     */
    @Test
    public void testClearOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        p.clear();
        assertEquals(pCopy, p);
        pCopy.clear();
        assertEquals(pCopy, p);
    }

    /**
     * test clear with paths with many points.
     */
    @Test
    public void testClearManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        p.add(1, 2, 3);
        p.add(2, 4, 5);
        p.clear();
        assertEquals(pCopy, p);
        pCopy.clear();
        assertEquals(pCopy, p);
    }

    /**
     * test transferFrom with paths with no points.
     */
    @Test
    public void testTransferFromNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        Path pTransfer = new Path1();
        p.transferFrom(pTransfer);
        assertEquals(pCopy, p);
        assertEquals(new Path1(), pTransfer);
    }

    /**
     * test transferFrom with paths with one point.
     */
    @Test
    public void testTransferFromOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        Path pTransfer = new Path1();
        pTransfer.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.transferFrom(pTransfer);
        assertEquals(pCopy, p);
        assertEquals(new Path1(), pTransfer);
    }

    /**
     * test transferFrom with paths with many points.
     */
    @Test
    public void testTransferFromManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        Path pTransfer = new Path1();
        pTransfer.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        pTransfer.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        pTransfer.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        p.transferFrom(pTransfer);
        assertEquals(pCopy, p);
        assertEquals(new Path1(), pTransfer);
    }

    /**
     * test the iterator's hasNext with no points.
     */
    @Test
    public void testIteratorHasNextNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        boolean pHasNext = it1.hasNext();
        boolean pCopyHasNext = it2.hasNext();
        assertEquals(false, pHasNext);
        assertEquals(pCopyHasNext, pHasNext);
        assertEquals(pCopy, p);
    }

    /**
     * test the iterator's hasNext true with one point.
     */
    @Test
    public void testIteratorHasNextTrueOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        boolean pHasNext = it1.hasNext();
        boolean pCopyHasNext = it2.hasNext();
        assertEquals(true, pHasNext);
        assertEquals(pCopyHasNext, pHasNext);
        assertEquals(pCopy, p);
    }

    /**
     * test the iterator's hasNext false with one point.
     */
    @Test
    public void testIteratorHasNextFalseOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        it1.next();
        it2.next();
        boolean pHasNext = it1.hasNext();
        boolean pCopyHasNext = it2.hasNext();
        assertEquals(false, pHasNext);
        assertEquals(pCopyHasNext, pHasNext);
        assertEquals(pCopy, p);
    }

    /**
     * test the iterator's hasNext true at index zero with many points.
     */
    @Test
    public void testIteratorHasNextAtZeroTrueManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        boolean pHasNext = it1.hasNext();
        boolean pCopyHasNext = it2.hasNext();
        assertEquals(true, pHasNext);
        assertEquals(pCopyHasNext, pHasNext);
        assertEquals(pCopy, p);
    }

    /**
     * test the iterator's hasNext true at a middle index with many points.
     */
    @Test
    public void testIteratorHasNextAtMiddleTrueManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        it1.next();
        it2.next();
        boolean pHasNext = it1.hasNext();
        boolean pCopyHasNext = it2.hasNext();
        assertEquals(true, pHasNext);
        assertEquals(pCopyHasNext, pHasNext);
        assertEquals(pCopy, p);
    }

    /**
     * test the iterator's hasNext false with many points.
     */
    @Test
    public void testIteratorHasNextFalseManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        it1.next();
        it2.next();
        it1.next();
        it2.next();
        it1.next();
        it2.next();
        boolean pHasNext = it1.hasNext();
        boolean pCopyHasNext = it2.hasNext();
        assertEquals(false, pHasNext);
        assertEquals(pCopyHasNext, pHasNext);
        assertEquals(pCopy, p);
    }

    /**
     * test the iterator's next with one point.
     */
    @Test
    public void testIteratorNextOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        Point pNext = it1.next();
        Point pCopyNext = it2.next();
        assertEquals(pCopyNext.x(), pNext.x());
        assertEquals(pCopyNext.y(), pNext.y());
        assertEquals(pCopy, p);
    }

    /**
     * test the iterator's next with many points.
     */
    @Test
    public void testIteratorNextManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.add(2, 4, 5);
        pCopy.add(2, 4, 5);
        Iterator<Point> it1 = p.iterator();
        Iterator<Point> it2 = pCopy.iterator();
        Point pNext = it1.next();
        Point pCopyNext = it2.next();
        Point pNextTwo = it1.next();
        Point pCopyNextTwo = it2.next();
        Point pNextThree = it1.next();
        Point pCopyNextThree = it2.next();
        assertEquals(pCopyNext.x(), pNext.x());
        assertEquals(pCopyNext.y(), pNext.y());
        assertEquals(pCopyNextTwo.x(), pNextTwo.x());
        assertEquals(pCopyNextTwo.y(), pNextTwo.y());
        assertEquals(pCopyNextThree.x(), pNextThree.x());
        assertEquals(pCopyNextThree.y(), pNextThree.y());
        assertEquals(pCopy, p);
    }
}
