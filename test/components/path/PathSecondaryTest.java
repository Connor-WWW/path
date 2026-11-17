package components.path;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.path.Path.OptimizationMethod;

/**
 * Tests for all object and secondary methods defined in the class
 * PathSecondary.
 */
public class PathSecondaryTest {

    /*
     * Object methods.
     */

    /**
     * test equals with no points.
     */
    @Test
    public void testEqualsNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        assertEquals(true, p.equals(pCopy));
    }

    /**
     * test equals with one point being true.
     */
    @Test
    public void testEqualsOnePointTrue() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, -1, 1);
        pCopy.add(0, -1, 1);
        assertEquals(true, p.equals(pCopy));
    }

    /**
     * test equals with one point being false.
     */
    @Test
    public void testEqualsOnePointFalse() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, -1, 1);
        pCopy.add(0, -2, 2);
        assertEquals(false, p.equals(pCopy));
    }

    /**
     * test equals with many points being true.
     */
    @Test
    public void testEqualsManyPointsTrue() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, -1, 1);
        pCopy.add(0, -1, 1);
        p.add(1, -2, 2);
        pCopy.add(1, -2, 2);
        p.add(2, -3, 3);
        pCopy.add(2, -3, 3);
        assertEquals(true, p.equals(pCopy));
    }

    /**
     * test equals with many points, one making it false.
     */
    @Test
    public void testEqualsManyPointsFalse() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, -1, 1);
        pCopy.add(0, -1, 1);
        p.add(1, -2, 2);
        pCopy.add(1, -5, 5);
        p.add(2, -3, 3);
        pCopy.add(2, -3, 3);
        assertEquals(false, p.equals(pCopy));
    }

    /**
     * test equals with incompatiable objects.
     */
    @Test
    public void testEqualsDiffObjects() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, -1, 1);
        pCopy.add(0, -1, 1);
        p.add(1, -2, 2);
        pCopy.add(1, -2, 2);
        p.add(2, -3, 3);
        pCopy.add(2, -3, 3);
        String defNotEqual = "should cause a false";
        assertEquals(false, p.equals(defNotEqual));
        assertEquals(pCopy, p);
    }

    /**
     * test equals with paths with diff number of points.
     */
    @Test
    public void testEqualsDiffNumPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, -1, 1);
        pCopy.add(0, -1, 1);
        p.add(1, -2, 2);
        pCopy.add(1, -2, 2);
        p.add(2, -3, 3);
        assertEquals(false, p.equals(pCopy));
    }

    /**
     * test hashCode with a path with no points.
     */
    @Test
    public void testHashCodeNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        int pHash = p.hashCode();
        int pCopyHash = pCopy.hashCode();
        assertEquals(pCopyHash, pHash);
        assertEquals(pCopy, p);
    }

    /**
     * test hashCode with a path with one point.
     */
    @Test
    public void testHashCodeOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 1, 2);
        pCopy.add(0, 1, 2);
        int pHash = p.hashCode();
        int pCopyHash = pCopy.hashCode();
        assertEquals(pCopyHash, pHash);
        assertEquals(pCopy, p);
    }

    /**
     * test hashCode with a path with many points.
     */
    @Test
    public void testHashCodeManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 1, 2);
        pCopy.add(0, 1, 2);
        p.add(1, 3, 4);
        pCopy.add(1, 3, 4);
        p.add(2, 5, 6);
        pCopy.add(2, 5, 6);
        int pHash = p.hashCode();
        int pCopyHash = pCopy.hashCode();
        assertEquals(pCopyHash, pHash);
        assertEquals(pCopy, p);
    }

    /**
     * test toString with a path with no points.
     */
    @Test
    public void testToStringNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        String pString = p.toString();
        String pCopyString = pCopy.toString();
        assertEquals(pCopyString, pString);
        assertEquals(pCopy, p);
    }

    /**
     * test toString with a path with one point.
     */
    @Test
    public void testToStringOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 1, 2);
        pCopy.add(0, 1, 2);
        String pString = p.toString();
        String pCopyString = pCopy.toString();
        assertEquals(pCopyString, pString);
        assertEquals(pCopy, p);
    }

    /**
     * test toString with a path with many points.
     */
    @Test
    public void testToStringManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 1, 2);
        pCopy.add(0, 1, 2);
        p.add(1, 3, 4);
        pCopy.add(1, 3, 4);
        p.add(2, 5, 6);
        pCopy.add(2, 5, 6);
        String pString = p.toString();
        String pCopyString = pCopy.toString();
        assertEquals(pCopyString, pString);
        assertEquals(pCopy, p);
    }

    /*
     * Secondary methods.
     */

    /**
     * test copyFrom with a path with no points.
     */
    @Test
    public void testCopyFromNoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.copyFrom(pCopy);
        assertEquals(pCopy, p);
    }

    /**
     * test copyFrom with a path with one point.
     */
    @Test
    public void testCopyFromOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        pCopy.add(0, 0, 1);
        p.copyFrom(pCopy);
        assertEquals(pCopy, p);
    }

    /**
     * test copyFrom with a path with many points.
     */
    @Test
    public void testCopyFromManyPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        pCopy.add(0, 0, 1);
        pCopy.add(1, 2, 3);
        pCopy.add(2, 4, 5);
        p.copyFrom(pCopy);
        assertEquals(pCopy, p);
    }

    /**
     * test totalDistance with a path with two points.
     */
    @Test
    public void testTotalDistanceTwoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        final double expectedDistance = 2.8284271247462;
        double pDist = p.totalDistance();
        assertEquals(expectedDistance, pDist, 0.0000001);
        assertEquals(pCopy, p);
    }

    /**
     * test totalDistance with a path with many points.
     */
    @Test
    public void testTotalDistanceManyPoints() {
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
        final double expectedDistance = 8.48528137424;
        double pDist = p.totalDistance();
        assertEquals(expectedDistance, pDist, 0.0000001);
        assertEquals(pCopy, p);
    }

    /**
     * test totalDistanceConnected with a path with two points.
     */
    @Test
    public void testTotalDistanceConnectedTwoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        final double expectedDistance = 5.65685424949;
        double pDist = p.totalDistanceConnected();
        assertEquals(expectedDistance, pDist, 0.0000001);
        assertEquals(pCopy, p);
    }

    /**
     * test totalDistanceConnected with a path with many points.
     */
    @Test
    public void testTotalDistanceConnectedManyPoints() {
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
        final double expectedDistance = 16.9705627485;
        double pDist = p.totalDistanceConnected();
        assertEquals(expectedDistance, pDist, 0.0000001);
        assertEquals(pCopy, p);
    }

    // To test optimizeDistances, I will feed a purposefully un-optimized
    // path and then expect to get a total distance that is less than this
    // un-optimized path. Any distance decrease will be a success in my eyes.

    /**
     * tests optimizeDistances with nearestNeighbor and one point.
     */
    @Test
    public void testOptimizeDistancesNNOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR);
        // With one point, path shouldn't change.
        assertEquals(pCopy, p);
    }

    /**
     * tests optimizeDistances with nearestNeighbor and two points.
     */
    @Test
    public void testOptimizeDistancesNNTwoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR);
        double pDist = p.totalDistanceConnected();
        double pCopyDist = pCopy.totalDistanceConnected();
        // With two points, distance should always be optimal, so these should be equal.
        assertEquals(pCopyDist, pDist, 0.000000001);
    }

    /**
     * tests optimizeDistances with nearestNeighbor and many points.
     */
    @Test
    public void testOptimizeDistancesNNManyPoints() {
        Path p = new Path1();
        p.add(0, -2, -2);
        p.add(1, 2, 2);
        p.add(2, -2, 2);
        p.add(3, 2, -2);
        double pDist = p.totalDistanceConnected();
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR);
        double pDistBetter = p.totalDistanceConnected();
        assertEquals(true, pDistBetter < pDist);
    }

    /**
     * tests optimizeDistances with nearestNeighborAggregate and one point.
     */
    @Test
    public void testOptimizeDistancesNNAgOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR_AGGREGATE);
        // With one point, path shouldn't change.
        assertEquals(pCopy, p);
    }

    /**
     * tests optimizeDistances with nearestNeighborAggregate and two points.
     */
    @Test
    public void testOptimizeDistancesNNAgTwoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR_AGGREGATE);
        double pDist = p.totalDistanceConnected();
        double pCopyDist = pCopy.totalDistanceConnected();
        // With two points, distance should always be optimal, so these should be equal.
        assertEquals(pCopyDist, pDist, 0.000000001);
    }

    /**
     * tests optimizeDistances with nearestNeighborAggregate and many points.
     */
    @Test
    public void testOptimizeDistancesNNAgManyPoints() {
        Path p = new Path1();
        p.add(0, -2, -2);
        p.add(1, 2, 2);
        p.add(2, -2, 2);
        p.add(3, 2, -2);
        double pDist = p.totalDistanceConnected();
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR_AGGREGATE);
        double pDistBetter = p.totalDistanceConnected();
        assertEquals(true, pDistBetter < pDist);
    }

    /**
     * tests optimizeDistances with nearestNeighborBothEnds and one point.
     */
    @Test
    public void testOptimizeDistancesNNBothEndsOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS);
        // With one point, path shouldn't change.
        assertEquals(pCopy, p);
    }

    /**
     * tests optimizeDistances with nearestNeighborBothEnds and two points.
     */
    @Test
    public void testOptimizeDistancesNNBothEndsTwoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS);
        double pDist = p.totalDistanceConnected();
        double pCopyDist = pCopy.totalDistanceConnected();
        // With two points, distance should always be optimal, so these should be equal.
        assertEquals(pCopyDist, pDist, 0.000000001);
    }

    /**
     * tests optimizeDistances with nearestNeighbor and many points.
     */
    @Test
    public void testOptimizeDistancesNNBothEndsManyPoints() {
        Path p = new Path1();
        p.add(0, -2, -2);
        p.add(1, 2, 2);
        p.add(2, -2, 2);
        p.add(3, 2, -2);
        double pDist = p.totalDistanceConnected();
        p.optimizeDistances(OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS);
        double pDistBetter = p.totalDistanceConnected();
        assertEquals(true, pDistBetter < pDist);
    }

    /**
     * tests optimizeDistances with nearestNeighborBothEndsAggregate and one
     * point.
     */
    @Test
    public void testOptimizeDistancesNNBothEndsAgOnePoint() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.optimizeDistances(
                OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS_AGGREGATE);
        // With one point, path shouldn't change.
        assertEquals(pCopy, p);
    }

    /**
     * tests optimizeDistances with nearestNeighborBothEndsAggregate and two
     * points.
     */
    @Test
    public void testOptimizeDistancesNNBothEndsAgTwoPoints() {
        Path p = new Path1();
        Path pCopy = new Path1();
        p.add(0, 0, 1);
        pCopy.add(0, 0, 1);
        p.add(1, 2, 3);
        pCopy.add(1, 2, 3);
        p.optimizeDistances(
                OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS_AGGREGATE);
        double pDist = p.totalDistanceConnected();
        double pCopyDist = pCopy.totalDistanceConnected();
        // With two points, distance should always be optimal, so these should be equal.
        assertEquals(pCopyDist, pDist, 0.000000001);
    }

    /**
     * tests optimizeDistances with nearestNeighborBothEndsAggregate and many
     * points.
     */
    @Test
    public void testOptimizeDistancesNNBothEndsAgManyPoints() {
        Path p = new Path1();
        p.add(0, -2, -2);
        p.add(1, 2, 2);
        p.add(2, -2, 2);
        p.add(3, 2, -2);
        double pDist = p.totalDistanceConnected();
        p.optimizeDistances(
                OptimizationMethod.NEAREST_NEIGHBOR_BOTH_ENDS_AGGREGATE);
        double pDistBetter = p.totalDistanceConnected();
        assertEquals(true, pDistBetter < pDist);
    }
}
