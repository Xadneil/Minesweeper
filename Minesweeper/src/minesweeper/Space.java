package minesweeper;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 * A Space object, representing either an empty space or a mine.
 * @author Daniel
 */
public abstract class Space extends JLabel {

    private State state = State.COVERED;
    private final int myX;
    private final int myY;
    private final int size = Minesweeper.SIZE;

    /**
     * Class constructor
     * @param x The myWidth coordinate
     * @param y The myHeight coordinate
     */
    public Space(int x, int y) {
        myX = x;
        myY = y;
        setPreferredSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setIcon(Icon.COVERED.image);
    }

    /**
     * Indicates a mine
     * @return Whether this space is a mine
     */
    public abstract boolean isMine();

    /**
     * Gets this space's myWidth coordinate
     * @return myWidth coordinate
     */
    public int getMyX() {
        return myX;
    }

    /**
     * Gets this space's myHeight coordinate
     * @return myHeight coordinate
     */
    public int getMyY() {
        return myY;
    }

    /**
     * Gets this space's state
     * @return State
     */
    public State getState() {
        return state;
    }

    /**
     * Gets the space adjacent to this one in the specified direction.
     * @param dir The direction
     * @param board The playing board to reference
     * @return The adjacent space
     */
    public Space getAdjacentSpace(Direction dir, Board board) {
        return board.getSpace(myX + dir.x, myY + dir.y);
    }

    /**
     * Gets all spaces adjacent to this one.
     * @param board The playing board to reference
     * @return A list of adjacent spaces
     */
    public List<Space> getAllAdjecentSpaces(Board board) {
        List<Space> ret = new ArrayList<Space>();
        for (Direction dir : Direction.values()) {
            Space sp = getAdjacentSpace(dir, board);
            if (sp != null) {
                ret.add(sp);
            }
        }
        return ret;
    }

    /**
     * Sets this space to the specified State
     * @param s The new State
     */
    public void setState(State s) {
        switch (s) {
            case COVERED:
                setIcon(Icon.COVERED.image);
                break;
            case FLAGGED:
                setIcon(Icon.FLAG.image);
                break;
            case QUESTIONED:
                setIcon(Icon.QUESTION.image);
                break;
            case REVEALED:
                if (isMine()) {
                    setIcon(Icon.MINE.image);
                } else {
                    int number = ((Empty) this).getNumber();
                    setIcon(Icon.values()[number].image);
                }
                break;
            case LOST:
                setIcon(Icon.LOST.image);
                break;
        }
        state = s;
    }

    /**
     * Changes the icon to indicate pressed down
     */
    public void tease() {
        if (state != State.REVEALED && state != State.FLAGGED) {
            setIcon(Icon.I0.image);
        }
    }

    /**
     * Changes the icon to indicate not pressed down
     */
    public void untease() {
        setState(getState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Space)) {
            return false;
        }
        Space o = (Space) other;
        return o.getMyX() == getMyX() && o.getMyY() == getMyY() && o.getState() == getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.myX;
        hash = 53 * hash + this.myY;
        return hash;
    }
}