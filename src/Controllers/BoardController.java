package Controllers;

import Figures.Figure;
import Figures.King;
import Figures.Pawns;
import Figures.Queen;
import View.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

import static View.ChessBoardView.ROW_COUNT;

public class BoardController implements MouseListener {

    private ChessBoardView chessBoardView;
    public static Color currentPlayer = Color.WHITE;
    private static final int DELAY = 100;
    public int countOfCheckWhite = 0;
    public int countOfCheckBlack = 0;
    private Timer timer;

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
        if(chessBoardView.getSelectedFigure() != null && chessBoardView.getSelectedFigure().getColor() == currentPlayer) {
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
            if (chessBoardView.getSelectedFigure().moveTo(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row, chessBoardView.getBoard())
                                && currentPlayerMove(row, col)) {
                if (col > ROW_COUNT - 1 || row > ROW_COUNT - 1 || col < 0 || row < 0 || x < 0 || y < 0) {
                    chessBoardView.getBoard()[chessBoardView.getSelectedFigureY()][chessBoardView.getSelectedFigureX()] = chessBoardView.getSelectedFigure();
                } else {
                    chessBoardView.animate(col, row);
                    chessBoardView.getBoard()[chessBoardView.getSelectedFigure().getRow()][chessBoardView.getSelectedFigure().getCol()] = null;
                    chessBoardView.getBoard()[row][col] = chessBoardView.getSelectedFigure(); // Zapise do pole figuru
                    //chessBoardView.getSelectedFigure().setRow(row);
                    //chessBoardView.getSelectedFigure().setCol(col);
                }
                changePawnToQueen();
                //chessBoardView.setSelectedFigure(null);
                chessBoardView.repaint();
                if(isCheckmate()) {
                    chessBoardView.restart();
                    countOfCheckWhite = 0;
                    countOfCheckBlack = 0;
                }
                if(isStalemate()) {
                    chessBoardView.restart();
                    countOfCheckWhite = 0;
                    countOfCheckBlack = 0;
                }
            }
        }
    }

    private void changePawnToQueen() {
        if(chessBoardView.getSelectedFigure() instanceof Pawns) {
            if(chessBoardView.getSelectedFigure().getColor().equals(Color.WHITE) && chessBoardView.getSelectedFigure().getRow() == 0) {
                chessBoardView.getBoard()[chessBoardView.getSelectedFigure().getRow()][chessBoardView.getSelectedFigure().getCol()] =
                        new Queen(chessBoardView.getSelectedFigure().getCol(), chessBoardView.getSelectedFigure().getRow(), Color.WHITE, chessBoardView.getSquare_size());
            }else if(chessBoardView.getSelectedFigure().getColor().equals(Color.BLACK) && chessBoardView.getSelectedFigure().getRow() == 7) {
                chessBoardView.getBoard()[chessBoardView.getSelectedFigure().getRow()][chessBoardView.getSelectedFigure().getCol()] =
                        new Queen(chessBoardView.getSelectedFigure().getCol(), chessBoardView.getSelectedFigure().getRow(), Color.BLACK, chessBoardView.getSquare_size());
            }
        }
    }


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

    private boolean isCheckmate() {
        Figure whiteKing = null;
        Figure blackKing = null;
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure instanceof King && figure.getColor().equals(Color.WHITE)) {
                    whiteKing = figure;
                } else if (figure instanceof King && figure.getColor().equals(Color.BLACK)) {
                    blackKing = figure;
                }
            }
        }
        if(whiteKing.isUnderAttack(whiteKing.getCol(), whiteKing.getRow(), chessBoardView.getBoard())) {
            countOfCheckWhite++;
        } else {
            countOfCheckWhite = 0;
        }
        if(blackKing.isUnderAttack(blackKing.getCol(), blackKing.getRow(), chessBoardView.getBoard())) {
            countOfCheckBlack++;
        } else {
            countOfCheckBlack = 0;
        }
        if(countOfCheckWhite == 2) {
            JOptionPane.showMessageDialog(null, "Black wins!");
            return true;
        }
        if(countOfCheckBlack == 2) {
            JOptionPane.showMessageDialog(null, "White wins!");
            return true;
        }
        return false;
    }


    public boolean isStalemate() {
        Figure king = null;

        // Находим короля текущего игрока на доске
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure instanceof King && figure.getColor().equals(currentPlayer)) {
                    king = figure;
                    break;
                }
            }
        }

        // Проверяем, находится ли король под атакой
        if (king.isUnderAttack(king.getCol(), king.getRow(), chessBoardView.getBoard())) {
            return false;
        }

        // Перебираем все фигуры текущего игрока и проверяем, есть ли у них допустимый ход
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure != null && figure.getColor().equals(currentPlayer)) {
                    if (figure.hasMoves(col, row, chessBoardView.getBoard())) {
                        return false;
                    }
                }
            }
        }

        // Если ни один ход не найден, то это пат
        JOptionPane.showMessageDialog(null, "Stalemate!");
        return true;
    }
}
