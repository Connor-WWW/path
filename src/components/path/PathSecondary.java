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
            // Cast given point to our type of point.
            SimplePoint comparePoint = (SimplePoint) compareTo;
            // Grab starting coordinates.
            int xFirst = this.xCoordinate;
            int yFirst = this.yCoordinate;
            // Grab ending coordinates.
            int xSecond = comparePoint.xCoordinate;
            int ySecond = comparePoint.yCoordinate;
            // Find those differences
            int xDiff = Math.abs(xFirst - xSecond);
            int yDiff = Math.abs(yFirst - ySecond);
            // Square and add these.
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
        assert length > 0 : "Violation of: |s| > 0";
        int index = 0;
        Double compareTo = s.entry(0);
        // For every entry in s,
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
        assert length > 0 : "Violation of: |s| > 0";
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
        assert length > 2 : "Violation of: |s| > 2";
        if (length > 2) {
            // Create a temporary path of all non-connected points.
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            // Create a random object for pulling a random point as the starter.
            Random rand = new Random1L();
            int startingIndex = rand.nextInt();
            // Modify startingIndex to be positive and in range of our path.
            startingIndex = Math.abs(startingIndex);
            startingIndex = startingIndex % length;
            // Grab this starting point.
            Point starter = unvisited.remove(startingIndex);
            // add starter back to the path.
            p.add(0, starter.x(), starter.y());
            // Repeat until all points have been connected.
            while (unvisited.length() > 0) {
                // Create distance sequence.
                Sequence<Double> distances = new Sequence1L<>();
                // Calculate the distances from starter.
                distancesToPoint(starter, unvisited, distances);
                // Find the smallest distance.
                int smallest = indexOfSmallestDouble(distances);
                // Remove this from unvisited and add it to p.
                Point candidate = unvisited.remove(smallest);
                p.add(p.length(), candidate.x(), candidate.y());
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
        assert length > 2 : "Violation of: |s| > 2";
        // Check that indexOfStart is within p
        assert 0 <= indexOfStart
                && indexOfStart < length : "Violation of: 0 <= indexOfStart < length";
        if (length > 2) {
            // Create a temporary path of all non-connected points.
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            // Grab this starting point and add it back to p.
            Point starter = unvisited.remove(indexOfStart);
            p.add(0, starter.x(), starter.y());
            // Repeat until all points have been connected.
            while (unvisited.length() > 0) {
                // Create distance sequence.
                Sequence<Double> distances = new Sequence1L<>();
                // Calculate the distances from starter.
                distancesToPoint(starter, unvisited, distances);
                // Find the smallest distance.
                int smallest = indexOfSmallestDouble(distances);
                // Remove this from unvisited and add it to p.
                Point candidate = unvisited.remove(smallest);
                p.add(p.length(), candidate.x(), candidate.y());
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
    private static void nearestNeighborAggrigate(Path p) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert length > 2 : "Violation of: |s| > 2";
        if (length > 2) {
            // Duplicate the path to have index references to points.
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
            // Choose the path with the smallest distance.
            int smallest = indexOfSmallestDouble(allDistances);
            // Retreive this smallest path
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
        assert length > 2 : "Violation of: |s| > 2";
        if (length > 2) {
            // Create a temporary path of all non-connected points.
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            // Create a random object for pulling a random point as the starter.
            Random rand = new Random1L();
            int startingIndex = rand.nextInt();
            // Modify startingIndex to be positive and in range of our path.
            startingIndex = Math.abs(startingIndex);
            startingIndex = startingIndex % length;
            // Grab this starting point.
            Point starter = unvisited.remove(startingIndex);
            // Add starter back to path.
            p.add(0, starter.x(), starter.y());
            // Both endpoints initially refer to the same point.
            Point starterFront = starter;
            Point starterBack = starter;
            // Create distance sequence for each end.
            Sequence<Double> distancesFront = new Sequence1L<>();
            Sequence<Double> distancesBack = new Sequence1L<>();
            // Repeat until all points have been connected.
            while (unvisited.length() > 0) {
                // Calculate the distances from starterFront and starterBack.
                distancesToPoint(starterFront, unvisited, distancesFront);
                distancesToPoint(starterBack, unvisited, distancesBack);

                // Grab the next smallest from each
                int smallestFront = indexOfSmallestDouble(distancesFront);
                int smallestBack = indexOfSmallestDouble(distancesBack);
                // Grab distances and compare which end is better.
                double bestFrontDistance = distancesFront.remove(smallestFront);
                double bestBackDistance = distancesBack.remove(smallestBack);
                // Whichever is closer, connect that point.
                if (bestFrontDistance <= bestBackDistance) {
                    Point candidate = unvisited.remove(smallestFront);
                    // index is at p.length to add it to the front of the path.
                    p.add(p.length(), candidate.x(), candidate.y());

                    // Update the front endpoint
                    starterFront = candidate;
                } else {
                    Point candidate = unvisited.remove(smallestBack);
                    // index is at 0 to add it to the back of the path.
                    p.add(0, candidate.x(), candidate.y());

                    // Update the back endpoint
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
        assert length > 2 : "Violation of: |s| > 2";
        if (length > 2) {
            // Create a temporary path of all non-connected points.
            Path unvisited = p.newInstance();
            unvisited.transferFrom(p);
            // Grab this starting point.
            Point starter = unvisited.remove(indexOfStart);
            // Add starter back to path.
            p.add(0, starter.x(), starter.y());
            // Both endpoints initially refer to the same point.
            Point starterFront = starter;
            Point starterBack = starter;
            // Create distance sequence for each end.
            Sequence<Double> distancesFront = new Sequence1L<>();
            Sequence<Double> distancesBack = new Sequence1L<>();
            // Repeat until all points have been connected.
            while (unvisited.length() > 0) {
                // Calculate the distances from starterFront and starterBack.
                distancesToPoint(starterFront, unvisited, distancesFront);
                distancesToPoint(starterBack, unvisited, distancesBack);

                // Grab the next smallest from each
                int smallestFront = indexOfSmallestDouble(distancesFront);
                int smallestBack = indexOfSmallestDouble(distancesBack);
                // Grab distances and compare which end is better.
                double bestFrontDistance = distancesFront.remove(smallestFront);
                double bestBackDistance = distancesBack.remove(smallestBack);
                // Whichever is closer, connect that point.
                if (bestFrontDistance <= bestBackDistance) {
                    Point candidate = unvisited.remove(smallestFront);
                    // index is at p.length to add it to the front of the path.
                    p.add(p.length(), candidate.x(), candidate.y());

                    // Update the front endpoint
                    starterFront = candidate;
                } else {
                    Point candidate = unvisited.remove(smallestBack);
                    // index is at 0 to add it to the back of the path.
                    p.add(0, candidate.x(), candidate.y());

                    // Update the back endpoint
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
    private static void nearestNeighborBothEndsAggrigate(Path p) {
        // If there's only 2 or less points,
        // the path is already sorted to optimize distance.
        int length = p.length();
        assert length > 2 : "Violation of: |s| > 2";
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
            // Choose the path with the smallest distance.
            int smallest = indexOfSmallestDouble(allDistances);
            // Retreive this smallest path
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
        // First: check if object is a path
        if (compareTo instanceof Path) {
            Path comparePath = (Path) compareTo;
            // Second: compare if memory address are equal,
            // easy way to disqualify a match.
            match = this == comparePath;
            if (!match) {
                // Third: compare if lengths are equal. Easy way to disqualify a match.
                match = length == comparePath.length();
                // Fourth: compare every point's x and y coordinates.
                if (match) {
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
        switch (type) {
            case NEAREST_NEIGHBOR: {
                nearestNeighbor(this);
            }
            case NEAREST_NEIGHBOR_AGGRIGATE: {
                nearestNeighborAggrigate(this);
            }
            case NEAREST_NEIGHBOR_BOTH_ENDS: {
                nearestNeighborBothEnds(this);
            }
            case NEAREST_NEIGHBOR_BOTH_ENDS_AGGRIGATE: {
                nearestNeighborBothEndsAggrigate(this);
            }
            default: {
                // This shouldn't happen, the client is required to provide an enum.
            }
        }
    }

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
        // Add all distances between all points.
        while (it.hasNext()) {
            Point prev = next;
            next = it.next();
            distance += prev.distance(next);
        }
        // Add on the distance between the last point and first point.
        distance += next.distance(first);
        return distance;
    };
}
