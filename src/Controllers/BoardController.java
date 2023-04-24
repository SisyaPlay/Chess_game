package Controllers;

import View.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static View.ChessBoardView.ROW_COUNT;

public class BoardController implements MouseListener, MouseMotionListener, ActionListener {

    private ChessBoardView chessBoardView;
    public static Color currentPlayer = Color.WHITE;
    private static final int DELAY = 100;
    private Timer timer;

    public BoardController(ChessBoardView chessBoardView) {
        this.chessBoardView = chessBoardView;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Posluchac mysi reaguje na kliknuti mysi.
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        int x = e.getX() - chessBoardView.getShiftX();
        int y = e.getY() - chessBoardView.getShiftY();
        int row = y / chessBoardView.getSquare_size();
        int col = x / chessBoardView.getSquare_size();

        if(col > ROW_COUNT - 1) {
            col = ROW_COUNT - 1;
        }
        if(col < 0) {
            col = 0;
        }

        if(row > ROW_COUNT - 1) {
            row = ROW_COUNT - 1;
        }
        if(row < 0) {
            row = 0;
        }

        // Zapishe do chessBoard.getSelectedFigure() figuru z pole
        chessBoardView.setSelectedFigure(chessBoardView.getBoard()[row][col]);
        if (chessBoardView.getSelectedFigure() != null) {
            chessBoardView.setSelectedFigureX(col);
            chessBoardView.setSelectedFigureY(row);
        }
        if(chessBoardView.getSelectedFigure() != null && chessBoardView.getSelectedFigure().getColor() == currentPlayer) {
            chessBoardView.repaint();
        }
    }


    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Posluchac mysi reaguje na odklinuti mysi
     * @param e the event to be processed
     */
    public void mouseReleased(MouseEvent e) {
        if (chessBoardView.getSelectedFigure() != null) {
            int x = e.getX() - chessBoardView.getShiftX();
            int y = e.getY() - chessBoardView.getShiftY();
            int row = y / chessBoardView.getSquare_size();
            int col = x / chessBoardView.getSquare_size();
            if (chessBoardView.getSelectedFigure().moveTo(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row, chessBoardView.getBoard())
                                && currentPlayerMove(row, col)) {
                if (col > ROW_COUNT - 1 || row > ROW_COUNT - 1 || col < 0 || row < 0 || x < 0 || y < 0) {
                    chessBoardView.getBoard()[chessBoardView.getSelectedFigureY()][chessBoardView.getSelectedFigureX()] = chessBoardView.getSelectedFigure();
                } else {
                    chessBoardView.getBoard()[chessBoardView.getSelectedFigure().getRow()][chessBoardView.getSelectedFigure().getCol()] = null;
                    chessBoardView.getBoard()[row][col] = chessBoardView.getSelectedFigure(); // Zapise do pole figuru
                    chessBoardView.getSelectedFigure().setRow(row);
                    chessBoardView.getSelectedFigure().setCol(col);
                }
                chessBoardView.setSelectedFigure(null);
                chessBoardView.repaint();
            }

        }
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    private boolean currentPlayerMove(int row, int col) {
        if(chessBoardView.getSelectedFigureX() == col) {
            if(chessBoardView.getSelectedFigureY() == row){
                return false;
            }
        }
        if (chessBoardView.getSelectedFigure().getColor().equals(currentPlayer)) {
            if (currentPlayer == Color.WHITE) {
                currentPlayer = Color.BLACK;
            } else {
                currentPlayer = Color.WHITE;
            }
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
