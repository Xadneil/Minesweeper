package minesweeper;

/**
 * An empty Space with a number indicating how many mines are in adjacent spaces
 *
 * @author Daniel
 */
public class Empty extends Space {

    private int number;

    /**
     * Class constructor
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public Empty(int x, int y) {
        super(x, y);
    }

    /**
     * Sets this space's number.
     *
     * @param number The new number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets this space's number.
     *
     * @return The number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Tells this is not a mine
     *
     * @return false
     */
    @Override
    public boolean isMine() {
        return false;
    }
}