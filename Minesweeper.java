import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class MinesBoard extends JButton {
    private int row;
    private int column;
    private int countMine;
    private boolean mine;

    public MinesBoard(int row, int column) {
        this.row = row;
        this.column = column;
        this.countMine = 0;
        this.mine = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getCountMine() {
        return countMine;
    }

    public void setCountMine(int countMine) {
        this.countMine = countMine;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}


class Minesweeper implements MouseListener {
    JFrame frame;
    MinesBoard[][] board = new MinesBoard[16][30];
    int countOpBut;

    public Minesweeper() {
        countOpBut = 0;
        frame = new JFrame("Minesweeper");
        frame.setSize(1400, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(16, 30));

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                MinesBoard boardButton = new MinesBoard(row, col);
                frame.add(boardButton);
                boardButton.addMouseListener(this);
                board[row][col] = boardButton;
            }
        }
        putMines();
        countMines();
        frame.setVisible(true);
    }

    public void putMines() {
        int numberofMines = 0;
        while (numberofMines < 60) {
            int randomRow = (int) (Math.random() * board.length);
            int randomColumn = (int) (Math.random() * board[0].length);

            while (board[randomRow][randomColumn].isMine()) {
                randomRow = (int) (Math.random() * board.length);
                randomColumn = (int) (Math.random() * board[0].length);
            }
            board[randomRow][randomColumn].setMine(true);
            numberofMines++;
        }
    }

    public void countMines() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {

                if (board[row][col].isMine()) {
                    for (int chkRw = row - 1; chkRw <= row + 1; chkRw++) {
                        for (int chkCl = col - 1; chkCl <= col + 1; chkCl++) {
                            try {
                                int value = board[chkRw][chkCl].getCountMine();
                                value++;
                                board[chkRw][chkCl].setCountMine(value);
                            } catch (Exception ignored) { }
                        }
                    }
                }
            }
        }
    }


    public void show() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col].isMine()) {
                    board[row][col].setIcon(new ImageIcon("mines1.png"));
                }
                else {
                    board[row][col].setText(board[row][col].getCountMine() + "");
                    board[row][col].setEnabled(false);
                }
            }
        }
    }


    public void openUpButtons(int row, int col) {
        if ((row < 0 || row >= board.length) || (col < 0 || col >= board[0].length )||
                board[row][col].getText().length() > 0 || !board[row][col].isEnabled()) {
        }
        else if (board[row][col].getCountMine() != 0) {
            board[row][col].setText(board[row][col].getCountMine() + "");
            board[row][col].setEnabled(false);
            countOpBut++;
        }
        else {
            countOpBut++;
            board[row][col].setEnabled(false);
            openUpButtons(row + 1, col);
            openUpButtons(row - 1, col);
            openUpButtons(row, col + 1);
            openUpButtons(row, col - 1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MinesBoard mButton = (MinesBoard) e.getComponent();
        if (e.getButton() == 1) {
            if (mButton.isMine()) {
                JOptionPane.showMessageDialog(frame, "You lose the game!");
                show();

            } else {
                openUpButtons(mButton.getRow(), mButton.getColumn());
                if (countOpBut == ((board.length * board[0].length) - 60)) {
                    JOptionPane.showMessageDialog(frame, "You win the game!");
                    show();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

class MinesweeperMain {
    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
    }
}