package Controllers;

import View.ChessBoardView;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static View.ChessBoardView.ROW_COUNT;

public class BoardController implements MouseListener, MouseMotionListener {

    private ChessBoardView chessBoardView;

    public BoardController(ChessBoardView chessBoardView) {
        this.chessBoardView = chessBoardView;
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
        chessBoardView.repaint();
    }


    public void mouseDragged(MouseEvent e) {
        int x = e.getX() - chessBoardView.getShiftX();
        int y = e.getY() - chessBoardView.getShiftY();
        int row = y / chessBoardView.getSquare_size();
        int col = x / chessBoardView.getSquare_size();
        if (chessBoardView.getSelectedFigure().moveTo(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row, chessBoardView.getBoard())
                && chessBoardView.getSelectedFigure() != null) {
            chessBoardView.getSelectedFigure().setX(col);
            chessBoardView.getSelectedFigure().setY(row);
            chessBoardView.repaint();
        }
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
            if (chessBoardView.getSelectedFigure().moveTo(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row, chessBoardView.getBoard())) {
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
}
