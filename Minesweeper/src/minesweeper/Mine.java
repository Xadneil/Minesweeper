package minesweeper;

/**
 * An Space with a mine in it.
 * @author Daniel
 */
public class Mine extends Space {

    /**
     * Class constructor
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public Mine(int x, int y) {
        super(x, y);
    }

    /**
     * Tells this is a mine
     * @return true
     */
    @Override
    public boolean isMine() {
        return true;
    }
}