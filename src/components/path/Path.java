package components.path;

/**
 * {@code PathKernel} enhanced with secondary methods.
 *
 */
public interface Path extends PathKernel {

    /**
     * A path entry (int x, int y).
     *
     * @mathmodel type Point is modeled by (x: int, y: int)
     * @initially <pre>
     * (int x, int y):
     *   ensures
     *     this = (x, y)
     * </pre>
     */
    interface Point {
        /**
         * Returns this {@code Point}'s x coordinate.
         *
         * @return the x coordinate
         */
        int x();

        /**
         * Returns this {@code Point}'s y coordinate.
         *
         * @return the y coordinate
         */
        int y();

        /**
         * Calculates and returns the euclidean distance between this
         * {@code point} and the compare {@code point}.
         *
         * @param compare
         *            The point to calculate a distance to.
         * @return the euclidean distance between the two points.
         * @ensures distance is a double that represents the compare point's
         *          position relative to this point's position.
         */
        double distance(Point compare);
    }

    /**
     * Copies the points from source into this.
     *
     * @param source
     *            The path to grab points from.
     * @replaces this
     * @ensures this contains the exact same points as source, if any.
     */
    void copyFrom(Path source);

    /**
     * Different methods for optimizing for distances within points.
     */
    enum OptimizationMethod {
        /**
         * Uses the nearest neighbor algorithm for optimizing minimal distances
         * between points.
         */
        NEAREST_NEIGHBOR,
        /**
         * Uses the nearest neighbor algorithm as well, but by testing every
         * single point as the starting point, choosing the layout with the
         * smallest total path distance.
         */
        NEAREST_NEIGHBOR_AGGRIGATE,
        /**
         * Uses the nearest neighbor algorithm, but however, checks both ends of
         * the path to see which out of both points has the next shortest
         * connection to a point.
         */
        NEAREST_NEIGHBOR_BOTH_ENDS,
        /**
         * Uses the nearest neighbor algorithm that checks both ends of the path
         * for each connection, and it starts with every point in the path and
         * chooses the path with the overall shortest total distance.
         */
        NEAREST_NEIGHBOR_BOTH_ENDS_AGGRIGATE
    }

    /**
     * Reorders {@code this} to minimize the euclidean distances between all
     * points within the entries of {@code this}.
     *
     * @param type
     *            the {@code OptimizationMethod} used by this method.
     * @replaces this
     * @requires #this is not <>
     * @ensures |#this| = |this| and this' points have a combined distance
     *          shorter than #this
     */
    void optimizeDistances(OptimizationMethod type);

    /**
     * Reports the euclidean distances of each point with each other, summed up.
     *
     * @return The combined distances between all points within this.
     * @requires #this contains more than one point.
     * @ensures for all points within this, n = |this|, <totalDistance> =
     *          {Point1.distance(Point2) + ... + Pointn.distance(Point1)}
     */
    double totalDistance();
}
