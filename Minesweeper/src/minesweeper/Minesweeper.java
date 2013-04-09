package minesweeper;

/**
 * A Minesweeper game similar to that of Windows
 * @author Daniel
 */
public class Minesweeper extends javax.swing.JFrame {

    private Board board = new Board(this);
    private javax.swing.Timer timer;
    private boolean won = false;
    private boolean lost = false;
    /**
     * The size in pixels of one side of a single Space in the grid
     */
    public final static int SIZE = 16;
    /**
     * Determines the version:
     * <tt>true</tt>: Windows Vista and 7
     * <tt>false</tt>: Windows XP and lower
     */
    public static boolean VISTA_MODE = true;
    /**
     * The number of Spaces in the horizontal direction
     */
    public static int myWidth = 16;
    /**
     * The number of Spaces in the vertical direction
     */
    public static int myHeight = 16;
    /**
     * The number of mines in the game
     */
    public static int Mines = 40;

    /**
     * Class Constructor
     */
    public Minesweeper() {
        initComponents();
        timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                timerField.setText("" + (Integer.parseInt(timerField.getText()) + 1));
            }
        });
        setLocationRelativeTo(null);
    }

    /**
     * Begins a new game.
     */
    public void newGame() {
        won = false;
        lost = false;
        timer.stop();
        if (!board.isFinished()) {
            mainGrid.removeMouseListener(mainGrid.getMouseListeners()[0]);
            mainGrid.removeMouseMotionListener(mainGrid.getMouseMotionListeners()[0]);
        }
        mainGrid.removeAll();
        board = new Board(this);
        sizeChanged();
        mainGrid.setLayout(new java.awt.GridLayout(myHeight, myWidth));
        loadSpaces();
        addListeners();
        setFlag(0);
        timerField.setText("0");
        smile.setIcon(Icon.SMILE.image);
        pack();
        repaint();
    }

    /**
     * Resizes the game grid if the game dimensions change
     */
    private void sizeChanged() {
        mainGrid.setMaximumSize(new java.awt.Dimension(myWidth * SIZE, myHeight * SIZE));
        mainGrid.setMinimumSize(new java.awt.Dimension(myWidth * SIZE, myHeight * SIZE));
        mainGrid.setPreferredSize(new java.awt.Dimension(myWidth * SIZE, myHeight * SIZE));
    }

    /**
     * Adds spaces in the board to the game grid. Called during
     * component initialization and new game.
     * @param panel 
     */
    private void loadSpaces() {
        for (Space s : board.getAllSpaces()) {
            mainGrid.add(s);
        }
        mainGrid.revalidate();
    }

    /**
     * Adds mouse and mouse motion listeners to the main grid
     */
    private void addListeners() {
        BoardListener bl = new BoardListener(board);
        mainGrid.addMouseListener(bl);
        mainGrid.addMouseMotionListener(bl);
    }

    /**
     * Reveals all unmarked mines and marks the specified mine in red in the
     * event of a loss.
     * @param loser The space to be marked red
     */
    public void lose(Space loser) {
        lost = true;
        timer.stop();
        for (Space s : board.getAllSpaces()) {
            if (s.isMine() && s.getState() != State.FLAGGED) {
                s.setState(State.REVEALED);
            }
            if (!s.isMine() && s.getState() == State.FLAGGED) {
                s.setIcon(Icon.BADMINE.image);
            }
        }
        loser.setState(State.LOST);
        smile.setIcon(Icon.DEAD.image);
        mainGrid.removeMouseListener(mainGrid.getMouseListeners()[0]);
        mainGrid.removeMouseMotionListener(mainGrid.getMouseMotionListeners()[0]);
    }

    /**
     * Flags all un-flagged spaces in the event of a win.
     */
    public void win() {
        won = true;
        timer.stop();
        for (Space sp : board.getAllSpaces()) {
            if (sp.isMine()) {
                sp.setState(State.FLAGGED);
            }
        }
        minesLeft.setText("0");
        smile.setIcon(Icon.SUN.image);
        mainGrid.removeMouseListener(mainGrid.getMouseListeners()[0]);
        mainGrid.removeMouseMotionListener(mainGrid.getMouseMotionListeners()[0]);
    }

    /**
     * Gets the finished state of the board
     * @return Whether the board has been finished
     */
    public boolean isFinished() {
        return board.isFinished();
    }

    /**
     * Updates the remaining mines text field.
     * @param flagged The number of flagged mines
     */
    public void setFlag(int flagged) {
        minesLeft.setText("" + Math.max(Mines - flagged, 0));
    }

    /**
     * Checks the custom radio button when a valid custom setting is selected
     */
    public void setCustom() {
        custom.setSelected(true);
    }

    /**
     * Starts a game after a starting position has been chosen.
     * @param startX The x coordinate of the starting space
     * @param startY The y coordinate of the starting space
     */
    public void start(int startX, int startY) {
        mainGrid.removeAll();
        loadSpaces();
        timer.start();
        board.singleClear(board.getSpace(startX, startY));
    }

    public javax.swing.JButton getSmile() {
        return smile;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isLost() {
        return lost;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        minesLeft = new javax.swing.JTextField();
        mainGrid = new javax.swing.JPanel();
        smile = new javax.swing.JButton();
        timerField = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        newGame = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        beginner = new javax.swing.JRadioButtonMenuItem();
        intermediate = new javax.swing.JRadioButtonMenuItem();
        expert = new javax.swing.JRadioButtonMenuItem();
        custom = new javax.swing.JRadioButtonMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exitGame = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        vistaOff = new javax.swing.JRadioButtonMenuItem();
        vistaOn = new javax.swing.JRadioButtonMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        aboutOption = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        minesLeft.setColumns(3);
        minesLeft.setEditable(false);
        minesLeft.setText("" + Mines);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        getContentPane().add(minesLeft, gridBagConstraints);

        mainGrid.setMaximumSize(new java.awt.Dimension(myWidth*SIZE, myHeight*SIZE));
        mainGrid.setMinimumSize(new java.awt.Dimension(myWidth*SIZE, myHeight*SIZE));
        mainGrid.setPreferredSize(new java.awt.Dimension(myWidth*SIZE, myHeight*SIZE));
        mainGrid.setLayout(new java.awt.GridLayout(myHeight, myWidth));
        loadSpaces();
        addListeners();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(mainGrid, gridBagConstraints);

        smile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/smile.gif"))); // NOI18N
        smile.setBorderPainted(false);
        smile.setPreferredSize(new java.awt.Dimension(26, 26));
        smile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                smileMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                smileMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        getContentPane().add(smile, gridBagConstraints);

        timerField.setColumns(3);
        timerField.setEditable(false);
        timerField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        timerField.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        getContentPane().add(timerField, gridBagConstraints);

        gameMenu.setText("Game");

        newGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        newGame.setText("New Game");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        gameMenu.add(newGame);
        gameMenu.add(jSeparator1);

        buttonGroup1.add(beginner);
        beginner.setText("Beginner");
        beginner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beginnerActionPerformed(evt);
            }
        });
        gameMenu.add(beginner);

        buttonGroup1.add(intermediate);
        intermediate.setSelected(true);
        intermediate.setText("Intermediate");
        intermediate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intermediateActionPerformed(evt);
            }
        });
        gameMenu.add(intermediate);

        buttonGroup1.add(expert);
        expert.setText("Expert");
        expert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expertActionPerformed(evt);
            }
        });
        gameMenu.add(expert);

        buttonGroup1.add(custom);
        custom.setText("Custom...");
        custom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customActionPerformed(evt);
            }
        });
        gameMenu.add(custom);
        gameMenu.add(jSeparator2);

        exitGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitGame.setText("Exit");
        exitGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitGameActionPerformed(evt);
            }
        });
        gameMenu.add(exitGame);

        menuBar.add(gameMenu);

        optionsMenu.setText("Options");

        buttonGroup2.add(vistaOff);
        vistaOff.setText("Vista Mode Off");
        vistaOff.setToolTipText("Makes only the first clicked cell free of mines.");
        vistaOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vistaOffActionPerformed(evt);
            }
        });
        optionsMenu.add(vistaOff);

        buttonGroup2.add(vistaOn);
        vistaOn.setSelected(true);
        vistaOn.setText("Vista Mode On");
        vistaOn.setToolTipText("Makes first cell clicked and all immediately surrounding cells free of mines.");
        vistaOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vistaOnActionPerformed(evt);
            }
        });
        optionsMenu.add(vistaOn);
        optionsMenu.add(jSeparator3);

        aboutOption.setText("About");
        aboutOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutOptionActionPerformed(evt);
            }
        });
        optionsMenu.add(aboutOption);

        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when the user clicks the New Game option in the Game menu
     */
    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        newGame();
    }//GEN-LAST:event_newGameActionPerformed
    /**
     * Called when the user clicks the Exit option in the Game menu
     */
    private void exitGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitGameActionPerformed
        dispose();
    }//GEN-LAST:event_exitGameActionPerformed
    /**
     * Called when the user clicks the Beginner option in the Game menu
     */
    private void beginnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beginnerActionPerformed
        if (myWidth != 9 || myHeight != 9 || Mines != 10) {
            myWidth = 9;
            myHeight = 9;
            Mines = 10;
            newGame();
        }
    }//GEN-LAST:event_beginnerActionPerformed
    /**
     * Called when the user clicks the Intermediate option in the Game menu
     */
    private void intermediateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intermediateActionPerformed
        if (myWidth != 16 || myHeight != 16 || Mines != 40) {
            myWidth = 16;
            myHeight = 16;
            Mines = 40;
            newGame();
        }
    }//GEN-LAST:event_intermediateActionPerformed
    /**
     * Called when the user clicks the Expert option in the Game menu
     */
    private void expertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expertActionPerformed
        if (myWidth != 30 || myHeight != 16 || Mines != 99) {
            myWidth = 30;
            myHeight = 16;
            Mines = 99;
            newGame();
        }
    }//GEN-LAST:event_expertActionPerformed
    /**
     * Called when the user presses the smile
     */
    private void smileMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smileMousePressed
        smile.setIcon(Icon.PRESSED.image);
    }//GEN-LAST:event_smileMousePressed
    /**
     * Called when the user releases the smile
     */
    private void smileMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smileMouseReleased
        smile.setIcon(Icon.SMILE.image);
        if (board.isBegun()) {
            newGame();
        }
    }//GEN-LAST:event_smileMouseReleased
    /**
     * Called when the user clicks the About option in the Options menu
     */
    private void aboutOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutOptionActionPerformed
        javax.swing.JOptionPane.showMessageDialog(this, "Minesweeper 1.5\r\nBy Daniel Flaws", "About", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_aboutOptionActionPerformed
    /**
     * Called when the user clicks the Vista Mode Off option in the Options menu
     */
    private void vistaOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vistaOffActionPerformed
        VISTA_MODE = false;
    }//GEN-LAST:event_vistaOffActionPerformed
    /**
     * Called when the user clicks the Vista Mode On option in the Options menu
     */
    private void vistaOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vistaOnActionPerformed
        VISTA_MODE = true;
    }//GEN-LAST:event_vistaOnActionPerformed
    /**
     * Called when the user clicks the Custom... option in the Game menu
     */
    private void customActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customActionPerformed
        new Options(this).setVisible(true);
    }//GEN-LAST:event_customActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutOption;
    private javax.swing.JRadioButtonMenuItem beginner;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButtonMenuItem custom;
    private javax.swing.JMenuItem exitGame;
    private javax.swing.JRadioButtonMenuItem expert;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JRadioButtonMenuItem intermediate;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPanel mainGrid;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField minesLeft;
    private javax.swing.JMenuItem newGame;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JButton smile;
    private javax.swing.JTextField timerField;
    private javax.swing.JRadioButtonMenuItem vistaOff;
    private javax.swing.JRadioButtonMenuItem vistaOn;
    // End of variables declaration//GEN-END:variables
}
