package components.path;

import java.util.Iterator;

import components.random.Random;
import components.random.Random1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Layered implementations of secondary methods for {@code Path}.
 */
public abstract class PathSecondary implements Path {
    /**
     * Implements the point class specified in Path.Point.
     */
    protected static final class SimplePoint implements Path.Point {
        /**
         * Stores the x coordinate associated with this point.
         */
        private int xCoordinate;

        /**
         * Stores the y coordinate associated with this point.
         */
        private int yCoordinate;

        /**
         * Constructor to setup xCoordinate and yCoordinate.
         *
         * @param x
         *            incoming value for xCoordinate.
         * @param y
         *            incoming value for yCoordinate.
         * @replaces this
         */
        SimplePoint(int x, int y) {
            this.xCoordinate = x;
            this.yCoordinate = y;
        }

        @Override
        public int x() {
            return this.xCoordinate;
        }

        @Override
        public int y() {
            return this.yCoordinate;
        }

        @Override
        public double distance(Point compareTo) {
            // Cast given point to our type of point to access direct coordinates.
            // Other components do this as well.
            SimplePoint comparePoint = (SimplePoint) compareTo;
            int xFirst = this.xCoordinate;
            int yFirst = this.yCoordinate;
            int xSecond = comparePoint.xCoordinate;
            int ySecond = comparePoint.yCoordinate;
            int xDiff = Math.abs(xFirst - xSecond);
            int yDiff = Math.abs(yFirst - ySecond);
            int cSquared = xDiff * xDiff + yDiff * yDiff;
            // Casting an int to a double shouldn't ever have an issue since it
            // involves just adding a decimal point, and we're going from a
            // smaller data type to a larger data type.
            return Math.sqrt(cSquared);
        }
    }

    /*
     * Private methods for distance optimization methods.
     */

    /**
     * Finds the index of the smallest double within the provided sequence.
     *
     * @param s
     *            the sequence to search through.
     * @return integer that is the index of the smallest double in the sequence.
     * @requires |s| > 0.
     * @ensures the provided integer is the index of the smallest double within
     *          s.
     */
    private static int indexOfSmallestDouble(Sequence<Double> s) {
        // Note: We can't use the iterator, because then we won't have index numbers
        int length = s.length();
        assert s.length() > 0 : "Violation of: |s| > 0";
        int index = 0;
        Double compareTo = s.entry(0);
        for (int i = 1; i < length; i++) {
            Double current = s.entry(i);
            if (current < compareTo) {
                compareTo = current;
                index = i;
            }
        }
        return index;
    }

    /**
     * Calculates every point in candidates euclidean distance to the target
     * point, and outputs these distances into the given sequence.
     *
     * @param target
     *            Point to compare every candidate with.
     * @param candidates
     *            Path full of points that are being compared with target.
     * @param distances
     *            Sequence of doubles that contains the euclidean distance each
     *            candidate is from target.
     * @replaces distances
     * @requires |candidates| > 0.
     * @ensures distances contains each candidate's euclidean distance to the
     *          target.
     */
    private static void distancesToPoint(Point target, Path candidates,
            Sequence<Double> distances) {
        int length = candidates.length();
        assert candidates.length() > 0 : "Violation of: |candidates| > 0";
        // Makes sure that distances doesn't have any trailing data.
        distances.clear();
        for (int i = 0; i < length; i++) {
            Point current = candidates.remove(i);
            Double currDist = target.distance(current);
            distances.add(i, currDist);
            candidates.add(i, current.x(), current.y());
        }
    }

    /**
     * Takes a random point from the path and connects it to its nearest
     * neighbor repeatedly until all points are connected together, minimizing
     * euclidean distance between points. Connected points are next to each
     * other within the path.
     *
     * @param p
     *            The path to run nearest neighbor on.
     * @updates p.
     * @requires |p| > 2, otherwise p is already optimized for euclidean
     *           distance.
     * @ensures p is sorted in a way that points are listed in an order that
     *          connects the nearest neighbors to each other.
     */
    private static void nearestNeighbor(Path p) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert p.length() > 2 : "Violation of: |p| > 2";
        if (length > 2) {
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            // Create a random object for pulling a random point as the starter.
            Random rand = new Random1L();
            int startingIndex = rand.nextInt();
            // Modify startingIndex to be positive and in range of our path.
            startingIndex = Math.abs(startingIndex);
            startingIndex = startingIndex % length;
            Point starter = unvisited.remove(startingIndex);
            p.add(0, starter.x(), starter.y());
            while (unvisited.length() > 0) {
                Sequence<Double> distances = new Sequence1L<>();
                distancesToPoint(starter, unvisited, distances);
                int smallest = indexOfSmallestDouble(distances);
                // Recycling starter to be the next point in the chain.
                starter = unvisited.remove(smallest);
                p.add(p.length(), starter.x(), starter.y());
            }
        }
    };

    /**
     * Takes the given point at index indexOfStart from the path and connects it
     * to its nearest neighbor repeatedly until all points are connected
     * together, minimizing euclidean distance between points. Connected points
     * are next to each other within the path.
     *
     * @param p
     *            The path to run nearest neighbor on.
     * @param indexOfStart
     *            The index of the point within p that marks the start of the
     *            path.
     * @updates p.
     * @requires |p| > 2, otherwise p is already optimized for euclidean
     *           distance and 0 <= indexOfStart < |p|.
     * @ensures p is sorted in a way that points are listed in an order that
     *          connects the nearest neighbors to each other.
     */
    private static void nearestNeighbor(Path p, int indexOfStart) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert p.length() > 2 : "Violation of: |p| > 2";
        assert 0 <= indexOfStart
                && indexOfStart < length : "Violation of: 0 <= indexOfStart < length";
        if (length > 2) {
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            Point starter = unvisited.remove(indexOfStart);
            p.add(0, starter.x(), starter.y());
            while (unvisited.length() > 0) {
                Sequence<Double> distances = new Sequence1L<>();
                distancesToPoint(starter, unvisited, distances);
                int smallest = indexOfSmallestDouble(distances);
                // Recycling starter to be the next point in the chain.
                starter = unvisited.remove(smallest);
                p.add(p.length(), starter.x(), starter.y());
            }
        }
    };

    /**
     * Performs the nearestNeighbor optimization method by starting from each
     * point within p, choosing the path with the shortest total euclidean
     * distance.
     *
     * @param p
     *            The path to optimize the euclidean distance of.
     * @updates p
     * @requires |p| > 2, otherwise p is already optimized for euclidean
     *           distance
     * @ensures p is sorted in a way that points are listed in an order that
     *          connects the nearest neighbors together, optimized with the best
     *          starting node.
     */
    private static void nearestNeighborAggregate(Path p) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert p.length() > 2 : "Violation of: |p| > 2";
        if (length > 2) {
            Path copy = p.newInstance();
            copy.copyFrom(p);
            // Store all point's NN path distance in a sequence
            Sequence<Double> allDistances = new Sequence1L<Double>();
            for (int i = 0; i < length; i++) {
                // Copy the untouched path from the copy
                p.copyFrom(copy);
                nearestNeighbor(p, i);
                double distance = p.totalDistance();
                allDistances.add(i, distance);
            }
            int smallest = indexOfSmallestDouble(allDistances);
            p.copyFrom(copy);
            nearestNeighbor(p, smallest);
        }
    }

    /**
     * Takes a random point from the path and connects it to its nearest
     * neighbor, and then repeatedly connects the closest neighbor out of the
     * two points at either end of the path until the whole path is connected,
     * minimizing euclidean distance between points. Connected points are next
     * to each other within the path.
     *
     * @param p
     *            The path to run nearest neighbor both ends on.
     * @updates p.
     * @requires |p| > 2, otherwise p is already optimized for euclidean
     *           distance.
     * @ensures p is sorted in a way that points are listed in an order that
     *          connects the nearest neighbors from both ends to each other.
     */
    private static void nearestNeighborBothEnds(Path p) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert p.length() > 2 : "Violation of: |p| > 2";
        if (length > 2) {
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            // Create a random object for pulling a random point as the starter.
            Random rand = new Random1L();
            int startingIndex = rand.nextInt();
            // Modify startingIndex to be positive and in range of our path.
            startingIndex = Math.abs(startingIndex);
            startingIndex = startingIndex % length;
            Point starter = unvisited.remove(startingIndex);
            p.add(0, starter.x(), starter.y());
            // Both endpoints initially refer to the same point.
            Point starterFront = starter;
            Point starterBack = starter;
            Sequence<Double> distancesFront = new Sequence1L<>();
            Sequence<Double> distancesBack = new Sequence1L<>();
            while (unvisited.length() > 0) {
                distancesToPoint(starterFront, unvisited, distancesFront);
                distancesToPoint(starterBack, unvisited, distancesBack);
                int smallestFront = indexOfSmallestDouble(distancesFront);
                int smallestBack = indexOfSmallestDouble(distancesBack);
                double bestFrontDistance = distancesFront.remove(smallestFront);
                double bestBackDistance = distancesBack.remove(smallestBack);
                if (bestFrontDistance <= bestBackDistance) {
                    Point candidate = unvisited.remove(smallestFront);
                    // index is at p.length to add it to the front of the path.
                    p.add(p.length(), candidate.x(), candidate.y());
                    starterFront = candidate;
                } else {
                    Point candidate = unvisited.remove(smallestBack);
                    // index is at 0 to add it to the back of the path.
                    p.add(0, candidate.x(), candidate.y());
                    starterBack = candidate;
                }
            }
        }
    }

    /**
     * Takes the given indexOfStart point from the path and connects it to its
     * nearest neighbor, and then repeatedly connects the closest neighbor out
     * of the two points at either end of the path until the whole path is
     * connected, minimizing euclidean distance between points. Connected points
     * are next to each other within the path.
     *
     * @param p
     *            The path to run nearest neighbor both ends on.
     * @param indexOfStart
     *            The index of the point to start from.
     * @updates p.
     * @requires |p| > 2, otherwise p is already optimized for euclidean
     *           distance.
     * @ensures p is sorted in a way that points are listed in an order that
     *          connects the nearest neighbors from both ends to each other.
     */
    private static void nearestNeighborBothEnds(Path p, int indexOfStart) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert p.length() > 2 : "Violation of: |ps| > 2";
        if (length > 2) {
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            Point starter = unvisited.remove(indexOfStart);
            p.add(0, starter.x(), starter.y());
            // Both endpoints initially refer to the same point.
            Point starterFront = starter;
            Point starterBack = starter;
            Sequence<Double> distancesFront = new Sequence1L<>();
            Sequence<Double> distancesBack = new Sequence1L<>();
            while (unvisited.length() > 0) {
                distancesToPoint(starterFront, unvisited, distancesFront);
                distancesToPoint(starterBack, unvisited, distancesBack);
                int smallestFront = indexOfSmallestDouble(distancesFront);
                int smallestBack = indexOfSmallestDouble(distancesBack);
                double bestFrontDistance = distancesFront.remove(smallestFront);
                double bestBackDistance = distancesBack.remove(smallestBack);
                if (bestFrontDistance <= bestBackDistance) {
                    Point candidate = unvisited.remove(smallestFront);
                    // index is at p.length to add it to the front of the path.
                    p.add(p.length(), candidate.x(), candidate.y());
                    starterFront = candidate;
                } else {
                    Point candidate = unvisited.remove(smallestBack);
                    // index is at 0 to add it to the back of the path.
                    p.add(0, candidate.x(), candidate.y());
                    starterBack = candidate;
                }
            }
        }
    }

    /**
     * Performs the nearestNeighborBothEnds optimization method by starting from
     * each point within p, choosing the path with the shortest total euclidean
     * distance.
     *
     * @param p
     *            The path to optimize the euclidean distance of.
     * @updates p
     * @requires |p| > 2, otherwise p is already optimized for euclidean
     *           distance
     * @ensures p is sorted in a way that points are listed in an order that
     *          connects the nearest neighbors together from both ends,
     *          optimized with the best starting node.
     */
    private static void nearestNeighborBothEndsAggregate(Path p) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert p.length() > 2 : "Violation of: |p| > 2";
        if (length > 2) {
            // Duplicate the path to have index references to points.
            Path copy = p.newInstance();
            copy.copyFrom(p);
            // Store all point's NN path distance in a sequence
            Sequence<Double> allDistances = new Sequence1L<Double>();
            for (int i = 0; i < length; i++) {
                // Copy the untouched path from the copy
                p.copyFrom(copy);
                nearestNeighborBothEnds(p, i);
                double distance = p.totalDistance();
                allDistances.add(i, distance);
            }
            int smallest = indexOfSmallestDouble(allDistances);
            p.copyFrom(copy);
            nearestNeighbor(p, smallest);
        }
    }

    /*
     * Common methods (from object).
     */

    /**
     * (non-Javadoc).
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object compareTo) {
        int length = this.length();
        boolean match = false;
        // First: check if object is not null
        if (compareTo != null) {
            // Second: check if object is a path
            if (compareTo instanceof Path) {
                Path comparePath = (Path) compareTo;
                // Third: compare if memory address are equal,
                // easy way to disqualify a match.
                match = this == comparePath;
                if (!match) {
                    // Fourth: compare if lengths are equal.
                    // Easy way to disqualify a match.
                    match = length == comparePath.length();
                    if (match) {
                        // Fifth: compare every point's x and y coordinates.
                        Iterator<Point> it1 = this.iterator();
                        Iterator<Point> it2 = comparePath.iterator();
                        while (it1.hasNext() && match) {
                            Point localPoint = it1.next();
                            Point compareToPoint = it2.next();
                            int localX = localPoint.x();
                            int localY = localPoint.y();
                            int compareToX = compareToPoint.x();
                            int compareToY = compareToPoint.y();
                            boolean matchX = localX == compareToX;
                            boolean matchY = localY == compareToY;
                            match = matchX && matchY;
                        }
                    }
                }
            }
        }
        return match;
    }

    /**
     * (non-Javadoc).
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int primeMultiplyBy = 31;
        int code = this.length();
        Iterator<Point> it = this.iterator();
        while (it.hasNext()) {
            Point current = it.next();
            code = primeMultiplyBy * code
                    + (primeMultiplyBy * current.x() + current.y());
        }
        return code;
    }

    /**
     * (non-Javadoc).
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("length: ");
        int length = this.length();
        result.append(length);
        result.append(", points: {");
        Iterator<Point> it = this.iterator();
        while (it.hasNext()) {
            Point current = it.next();
            result.append("(");
            result.append(current.x());
            result.append(", ");
            result.append(current.y());
            result.append(")");
            if (it.hasNext()) {
                result.append(", ");
            }
        }
        result.append("}");
        return result.toString();
    }
    /*
     * Other non-kernel methods
     */

    /**
     * (non-Javadoc).
     *
     * @see components.path.Path#copyFrom(components.path.Path)
     */
    @Override
    public void copyFrom(Path source) {
        int length = source.length();
        this.clear();
        for (int i = 0; i < length; i++) {
            Point current = source.remove(i);
            this.add(i, current.x(), current.y());
            source.add(i, current.x(), current.y());
        }
    }

    /**
     * (non-Javadoc).
     *
     * @see components.path.Path#optimizeDistances(
     *      components.path.Path.OptimizationMethod)
     */
    @Override
    public void optimizeDistances(OptimizationMethod type) {
        // if length <= 2, then distance is already optimal.
        if (this.length() > 2) {
            switch (type) {
                case NEAREST_NEIGHBOR: {
                    nearestNeighbor(this);
                    break;
                }
                case NEAREST_NEIGHBOR_AGGREGATE: {
                    nearestNeighborAggregate(this);
                    break;
                }
                case NEAREST_NEIGHBOR_BOTH_ENDS: {
                    nearestNeighborBothEnds(this);
                    break;
                }
                case NEAREST_NEIGHBOR_BOTH_ENDS_AGGREGATE: {
                    nearestNeighborBothEndsAggregate(this);
                    break;
                }
                default: {
                    // This shouldn't happen, the client is required to provide an enum.
                    break;
                }
            }
        }
    }

    /**
     * (non-Javadoc).
     *
     * @see components.path.Path#totalDistanceConnected()
     */
    @Override
    public double totalDistanceConnected() {
        assert this.length() > 1 : "Violation of: |this| > 1";
        Iterator<Point> it = this.iterator();
        Point first = it.next();
        Point next = first;
        double distance = 0.0;
        while (it.hasNext()) {
            Point prev = next;
            next = it.next();
            distance += prev.distance(next);
        }
        // Add on the distance between the last point and first point.
        distance += next.distance(first);
        return distance;
    };

    /**
     * (non-Javadoc).
     *
     * @see components.path.Path#totalDistance()
     */
    @Override
    public double totalDistance() {
        assert this.length() > 1 : "Violation of: |this| > 1";
        Iterator<Point> it = this.iterator();
        Point first = it.next();
        Point next = first;
        double distance = 0.0;
        while (it.hasNext()) {
            Point prev = next;
            next = it.next();
            distance += prev.distance(next);
        }
        return distance;
    };
}
