package Controllers;

import ChessView.ChessBoard;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoardController implements MouseListener, MouseMotionListener {

    private ChessBoard chessBoard;

    public BoardController(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    /**
     * Posluchac mysi reaguje na kliknuti mysi.
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        int x = e.getX() - shiftX;
        int y = e.getY() - shiftY;
        int row = y / square_size;
        int col = x / square_size;

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

        selectedFigure = board[row][col]; // Zapishe do selectedFigure figuru z pole
        if (selectedFigure != null) {
            selectedFigureX = col;
            selectedFigureY = row;
        }
        repaint();
    }


    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Posluchac mysi reaguje na odklinuti mysi
     * @param e the event to be processed
     */
    public void mouseReleased(MouseEvent e) {
        if (selectedFigure != null) {
            int x = e.getX() - shiftX;
            int y = e.getY() - shiftY;
            int row = y / square_size;
            int col = x / square_size;
            if (selectedFigure.moveTo(selectedFigureX, selectedFigureY, col, row, board)) {
                if (col > ROW_COUNT - 1 || row > ROW_COUNT - 1 || col < 0 || row < 0 || x < 0 || y < 0) {
                    board[selectedFigureY][selectedFigureX] = selectedFigure;
                } else {
                    board[selectedFigure.getRow()][selectedFigure.getCol()] = null;
                    board[row][col] = selectedFigure; // Zapise do pole figuru
                    selectedFigure.setRow(row);
                    selectedFigure.setCol(col);
                }
                selectedFigure = null;
                repaint();
            }

        }
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
