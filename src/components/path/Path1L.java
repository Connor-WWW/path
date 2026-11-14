package components.path;

import java.util.Iterator;
import java.util.NoSuchElementException;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * {@code Path} represented as a {@link components.sequence components.sequence}
 * with implementations of primary methods.
 *
 * @convention
 * @correspondence this = [value of $this.rep]
 */
public class Path1L extends PathSecondary {
    /*
     * Private members.
     */

    /**
     * Representation of {@code this}.
     */
    private Sequence<Point> rep;
    /**
     * Length is tracked internally as an optimization.
     */
    private int length;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = new Sequence1L<Point>();
        this.length = 0;
    }
    /*
     * Constructors
     */

    /**
     * No-argument constructor.
     */
    public Path1L() {
        this.createNewRep();
    }

    /*
     * Standard Methods
     */

    @Override
    public final Path newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Path source) {
        assert source != null : "Violation of: source is not null";
        assert this != null : "Violation of: this is not null";
        assert source instanceof Path1L : "Violation of: source is of dynamic type Path1L";
        /*
         * The below cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        Path1L localSource = (Path1L) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel Methods
     */

    @Override
    public final void add(int pos, int x, int y) {
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos <= this.length : "Violation of: pos <= |this|";
        assert !this.hasPoint(x,
                y) : "Violation of: point made of x, y is not in this";
        this.rep.add(pos, new SimplePoint(x, y));
        // Increase tracked length.
        this.length++;
    }

    @Override
    public final Point remove(int pos) {
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos < this.length : "Violation of: pos < |this|";
        Point result = this.rep.remove(pos);
        // Decrease tracked length.
        this.length--;
        return result;
    }

    @Override
    public final int length() {
        return this.length;
    }

    @Override
    public final boolean hasPoint(int x, int y) {
        boolean output = false;
        Point input = new SimplePoint(x, y);
        int i = 0;
        while (!output && i < this.length) {
            Point current = this.rep.entry(i);
            if (current.x() == input.x() && current.y() == input.y()) {
                output = true;
            }
            i++;
        }
        return output;
    }

    @Override
    public final Iterator<Point> iterator() {
        return new Path1LIterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Path1L}.
     */
    private final class Path1LIterator implements Iterator<Point> {
        /**
         * Representation iterator.
         */
        private final Iterator<Point> iterator;

        /**
         * No-argument constructor.
         */
        private Path1LIterator() {
            this.iterator = Path1L.this.rep.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Point next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                /*
                 * Exception is supposed to be thrown in this case, but with
                 * assertion-checking enabled it cannot happen because of assert
                 * above.
                 */
                throw new NoSuchElementException();
            }
            return this.iterator.next();
        }

        // All OSU CSE components just don't support this, so uh I guess I won't either?
        // Got to follow OSU convention.

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }
    }
}
