package minesweeper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * An extention of the MouseAdapter class to dispatch various mouse commands.
 * @author Daniel
 */
public class BoardListener extends MouseAdapter {

    private boolean leftDown = false, rightDown = false, chordTeasing = false;
    private int lastX = -1, lastY = -1;
    private final Board board;

    /**
     * Class Constructor
     * @param board The Board which contains the Space
     */
    public BoardListener(Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() < 0 || e.getY() < 0 || e.getX() > Minesweeper.myWidth * Minesweeper.SIZE || e.getY() > Minesweeper.myHeight * Minesweeper.SIZE) {
            return;
        }
        boolean left = e.getButton() == MouseEvent.BUTTON1,
                right = e.getButton() == MouseEvent.BUTTON3,
                middle = e.getButton() == MouseEvent.BUTTON2;

        if (!board.isBegun() && left && !rightDown && !chordTeasing) {
            board.unteaseSmile();
            board.begin(e.getX() / Minesweeper.SIZE, e.getY() / Minesweeper.SIZE);
        } else if (left && !rightDown && !chordTeasing) {
            board.unteaseSmile();
            board.singleClear(board.spaceAt(e.getX(), e.getY()));
        } else if ((left && rightDown) || (right && leftDown) || middle) {
            board.unteaseSmile();
            board.quickClear(board.spaceAt(e.getX(), e.getY()));
        }

        if (left) {
            leftDown = false;
        } else if (right) {
            rightDown = false;
        }
        if (chordTeasing && left) {
            board.spaceAt(e.getX(), e.getY()).untease();
            for (Space s : board.spaceAt(e.getX(), e.getY()).getAllAdjecentSpaces(board)) {
                s.untease();
            }
            board.unteaseSmile();
            chordTeasing = false;
        }
        if (board.isWon()) {
            board.winSmile();
        }
        if (board.isLost()) {
            board.loseSmile();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 0 || e.getY() < 0 || e.getX() > Minesweeper.myWidth * Minesweeper.SIZE || e.getY() > Minesweeper.myHeight * Minesweeper.SIZE) {
            return;
        }
        boolean left = e.getButton() == MouseEvent.BUTTON1,
                right = e.getButton() == MouseEvent.BUTTON3,
                middle = e.getButton() == MouseEvent.BUTTON2;
        if (right) {
            rightDown = true;
            if (leftDown) {
                board.teaseChord(board.spaceAt(e.getX(), e.getY()));
                board.teaseSmile();
                chordTeasing = true;
            } else {
                board.cycleState(board.spaceAt(e.getX(), e.getY()));
            }
        } else if (left) {
            leftDown = true;
            if (rightDown) {
                board.teaseChord(board.spaceAt(e.getX(), e.getY()));
                board.teaseSmile();
                chordTeasing = true;
            } else {
                board.spaceAt(e.getX(), e.getY()).tease();
                board.teaseSmile();
            }
        }
        lastX = e.getX();
        lastY = e.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() < 0 || e.getY() < 0 || e.getX() > Minesweeper.myWidth * Minesweeper.SIZE || e.getY() > Minesweeper.myHeight * Minesweeper.SIZE) {
            if (lastX > -1 && lastY > -1) {
                if (chordTeasing) {
                    board.spaceAt(lastX, lastY).untease();
                    for (Space s : board.spaceAt(lastX, lastY).getAllAdjecentSpaces(board)) {
                        s.untease();
                    }
                    board.unteaseSmile();
                    chordTeasing = false;
                }
                lastX = -1;
                lastY = -1;
            }
            return;
        }
        if (lastX == -1 && lastY == -1) {
            board.teaseChord(board.spaceAt(e.getX(), e.getY()));
            chordTeasing = true;
        }
        Space center = board.spaceAt(e.getX(), e.getY());
        Space oldCenter = board.spaceAt(lastX, lastY);
        if (!center.equals(oldCenter)) {
            if (rightDown && leftDown) {
                continueChording(center, oldCenter);
            } else if (leftDown) {
                if (chordTeasing) {
                    continueChording(center, oldCenter);
                } else {
                    oldCenter.untease();
                    center.tease();
                }
            }
            if (leftDown || !rightDown) {
                board.teaseSmile();
            }
            lastX = e.getX();
            lastY = e.getY();
        }
    }

    /**
     * Moves the square of teasing spaces
     * @param newCenter
     * @param oldCenter 
     */
    public void continueChording(Space newCenter, Space oldCenter) {
        board.teaseChord(newCenter);
        for (Space s : oldCenter.getAllAdjecentSpaces(board)) {
            if (!newCenter.getAllAdjecentSpaces(board).contains(s) && !s.equals(newCenter)) {
                s.untease();
            }
        }
    }
}