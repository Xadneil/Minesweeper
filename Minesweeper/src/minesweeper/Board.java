package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A playing field which holds a two-dimensional array of Spaces and which
 * coordinates between them.
 * @author Daniel
 */
public class Board {

    private Space[][] board;
    private int flagged = 0, revealed = 0;
    private Minesweeper parent;
    private boolean begun = false, finished = false;

    /**
     * Class Constructor
     * @param parent The Minesweeper object that contains this
     */
    public Board(Minesweeper parent) {
        board = new Space[Minesweeper.myHeight][Minesweeper.myWidth];
        this.parent = parent;
        initialize();
    }

    /**
     * Fills the board with dummy Empty's so the first click is not a mine
     */
    private void initialize() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = new Empty(x, y);
            }
        }
    }

    private List<Space> getFlaggedSpaces() {
        List<Space> ret = new ArrayList<Space>();
        for (Space s : getAllSpaces()) {
            if (s.getState() == State.FLAGGED) {
                ret.add(s);
            }
        }
        return ret;
    }

    /**
     * Generates the mine scatter and numbers, making sure the starting click
     * is an empty space
     * @param startWidth The starting click's myWidth
     * @param startHeight The starting click's myHeight
     */
    private void generate(int startWidth, int startHeight) {
        List<Space> flaggedSpaces = getFlaggedSpaces();
        board = new Space[Minesweeper.myHeight][Minesweeper.myWidth];
        board[startHeight][startWidth] = new Empty(startWidth, startHeight);
        if (Minesweeper.VISTA_MODE) {
            if (startHeight - 1 >= 0) {
                if (startWidth - 1 >= 0) {
                    board[startHeight - 1][startWidth - 1] = new Empty(startWidth - 1, startHeight - 1);
                }
                board[startHeight - 1][startWidth] = new Empty(startWidth, startHeight - 1);
                if (startWidth + 1 < board[startHeight].length) {
                    board[startHeight - 1][startWidth + 1] = new Empty(startWidth + 1, startHeight - 1);
                }
            }
            if (startWidth - 1 >= 0) {
                board[startHeight][startWidth - 1] = new Empty(startWidth - 1, startHeight);
            }
            if (startWidth + 1 < board[startHeight].length) {
                board[startHeight][startWidth + 1] = new Empty(startWidth + 1, startHeight);
            }
            if (startHeight + 1 < board.length) {
                if (startWidth - 1 >= 0) {
                    board[startHeight + 1][startWidth - 1] = new Empty(startWidth - 1, startHeight + 1);
                }
                board[startHeight + 1][startWidth] = new Empty(startWidth, startHeight + 1);
                if (startWidth + 1 < board[startHeight].length) {
                    board[startHeight + 1][startWidth + 1] = new Empty(startWidth + 1, startHeight + 1);
                }
            }
        }
        for (int i = 0; i < Minesweeper.Mines; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * board[0].length);
                y = (int) (Math.random() * board.length);
            } while (board[y][x] != null);
            board[y][x] = new Mine(x, y);
        }
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == null) {
                    board[y][x] = new Empty(x, y);
                }
            }
        }
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (!board[y][x].isMine()) {
                    Empty sp = (Empty) board[y][x];
                    sp.setNumber(countAround(x, y));
                }
            }
        }
        for (Space flag : flaggedSpaces) {
            board[flag.getMyY()][flag.getMyX()].setState(State.FLAGGED);
        }
        begun = true;
    }

    /**
     * Makes the smile button surprised
     */
    public void teaseSmile() {
        parent.getSmile().setIcon(Icon.TEASE.image);
    }

    /**
     * Makes the smile button un-surprised
     */
    public void unteaseSmile() {
        parent.getSmile().setIcon(Icon.SMILE.image);
    }

    public void loseSmile() {
        parent.getSmile().setIcon(Icon.DEAD.image);
    }

    /**
     * Makes the smile wear sunglasses
     */
    public void winSmile() {
        parent.getSmile().setIcon(Icon.SUN.image);
    }

    /**
     * @return Whether the game is won
     */
    public boolean isWon() {
        return parent.isWon();
    }

    public boolean isLost() {
        return parent.isLost();
    }

    /**
     * Gets all the Spaces in the board in order
     * @return A List of Spaces
     */
    public List<Space> getAllSpaces() {
        List<Space> ret = new ArrayList<Space>();
        for (int i = 0; i < board.length; i++) {
            ret.addAll(Arrays.asList(board[i]));
        }
        return ret;
    }

    /**
     * Gets the Space at the real coordinates specified
     * @param x The real x coordinate
     * @param y The real y coordinate
     * @return The Space at that position
     */
    public Space spaceAt(int x, int y) {
        return board[y / Minesweeper.SIZE][x / Minesweeper.SIZE];
    }

    /**
     * Gets the beginning state of this board
     * @return Whether this board has begun
     */
    public boolean isBegun() {
        return begun;
    }

    /**
     * Gets the ending state of this board
     * @return Whether this board has been finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Begins the game
     * @param x the starting x
     * @param y the starting y
     */
    public void begin(int x, int y) {
        generate(x, y);
        parent.start(x, y);
    }

    /**
     * Gets the number of mines around a certain space
     * @param x The myWidth coordinate
     * @param y The myWidth coordinate
     * @return the number of mines around it
     */
    public int countAround(int x, int y) {
        int ret = 0;
        for (Space s : getSpace(x, y).getAllAdjecentSpaces(this)) {
            if (s.isMine()) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * Gets the Space at the specified coordinates
     * @param x myWidth coordinate
     * @param y myHeight coordinate
     * @return the corresponding space
     */
    public Space getSpace(int x, int y) {
        if (y >= 0 && y < board.length && x >= 0 && x < board[y].length) {
            return board[y][x];
        }
        return null;
    }

    /**
     * Cycles the state of a space: Uncovered to Flagged to Questioned to
     * Uncovered. Called in the case of a right click.
     * @param sp The space to cycle
     */
    public void cycleState(Space sp) {
        State state = sp.getState();
        switch (state) {
            case COVERED:
                state = State.FLAGGED;
                parent.setFlag(++flagged);
                break;
            case FLAGGED:
                state = State.QUESTIONED;
                parent.setFlag(--flagged);
                break;
            case QUESTIONED:
                state = State.COVERED;
                break;
        }
        sp.setState(state);
    }

    /**
     * Clears a single space if is is not already revealed, losing if it is a
     * mine. If the space has no mines surrounding it, all spaces around it are
     * also revealed. Called in the case of a left click.
     * @param sp The space to clear
     */
    public void singleClear(Space sp) {
        if (sp.getState() == State.COVERED) {
            if (sp.isMine()) {
                parent.lose(sp);
                finished = true;
                return;
            }
            sp.setState(State.REVEALED);
            revealed++;
            if (revealed == (Minesweeper.myWidth * Minesweeper.myHeight - Minesweeper.Mines)) {
                parent.win();
                finished = true;
                return;
            }
            if (((Empty) sp).getNumber() == 0) {
                for (Space nextTo : sp.getAllAdjecentSpaces(this)) {
                    singleClear(nextTo);
                }
            }
        }
    }

    /**
     * If the specified space is revealed and its number matches the number of
     * flagged spaces surrounding it, all un-flagged spaces surrounding it are
     * revealed, even if this results in a loss. Called in the case of a both or
     * middle click.
     * @param center The space in the center of the reveal
     */
    public void quickClear(Space center) {
        if (center.getState() == State.REVEALED && !center.isMine()) {
            int nearFlags = 0;
            List<Space> adjacent = center.getAllAdjecentSpaces(this);
            for (Space s : adjacent) {
                if (s.getState() == State.FLAGGED) {
                    nearFlags++;
                }
                if (s.getState() == State.QUESTIONED) {
                    return;
                }
            }
            if (nearFlags == ((Empty) center).getNumber()) {
                for (Space s : adjacent) {
                    if (s.getState() == State.COVERED && !parent.isLost()) {
                        singleClear(s);
                    }
                }
            }
        }
    }

    /**
     * Teases all covered spaces around and including a center
     * @param center 
     */
    public void teaseChord(Space center) {
        for (Space o : center.getAllAdjecentSpaces(this)) {
            if (o.getState() == State.COVERED) {
                o.tease();
            }
        }
        center.tease();
    }
}