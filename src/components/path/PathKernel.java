package components.path;

import components.standard.Standard;

/**
 * Path kernel component with primary methods. All references are non-null.
 *
 * @mathmodel type PathKernel is modeled by string of (int X, int Y)
 * @initially {@code
 * ():
 *   ensures
 *     this = <>
 * }
 * @iterator ~this.seen * ~this.unseen = this
 */
public interface PathKernel extends Standard<Path>, Iterable<Path.Point> {
    /**
     * Adds the point ({@code x}, {@code y}) at position {@code pos} of
     * {@code this}.
     *
     * @param pos
     *            the position at which to add a point.
     * @param x
     *            the x coordinate to be added.
     * @param y
     *            the y coordinate to be added, associated with given x value.
     * @updates this
     * @requires exact point (x, y) is not in this,
     *           {@code 0 <= pos and pos <= |this|}
     * @ensures {@code this = #this[0, pos) * <(x, y)> * #this[pos, |#this|)}
     */
    void add(int pos, int x, int y);

    /**
     * Removes and returns the point at position {@code pos} pf {@code this}.
     *
     * @param pos
     *            the position at which to remove a point.
     * @return the point removed.
     * @updates this
     * @requires {@code 0 <= pos and pos < |this|}
     * @ensures {@code
     * this = #this[0, pos) * #this[pos+1, |#this|) and <remove> = #this[pos, pos+1)
     * }
     */
    Path.Point remove(int pos);

    /**
     * Reports whether there is a point in {@code this} whose components equal
     * {@code (x, y)}.
     *
     * @param x
     *            the x coordinate to be checked
     * @param y
     *            the y coordinate to be checked
     * @return true iff there is a point whose components match directly with
     *         {@code (x, y)}
     * @ensures hasPoint = ((x, y) is in ENTRIES(this))
     */
    boolean hasPoint(int x, int y);

    /**
     * Reports the length of {@code this}.
     *
     * @return the length
     * @ensures length = |this|
     */
    int length();
}
