package minesweeper;

/**
 * The Launch class
 * @author Daniel
 */
public class Launch {

    /**
     * Starts the Minesweeper program
     * @param args The command line arguments
     */
    public static void main(String args[]) {
        switch (args.length) {
            case 1:
                Minesweeper.Mines = Integer.parseInt(args[0]);
                break;
            case 2:
                Minesweeper.myWidth = Integer.parseInt(args[0]);
                Minesweeper.myHeight = Integer.parseInt(args[1]);
                break;
            case 3:
                Minesweeper.myWidth = Integer.parseInt(args[0]);
                Minesweeper.myHeight = Integer.parseInt(args[1]);
                Minesweeper.Mines = Integer.parseInt(args[2]);
                break;
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Minesweeper m = new Minesweeper();
                m.setVisible(true);
            }
        });
    }
}