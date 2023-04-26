package Controllers;

import Figures.Figure;
import Figures.King;
import View.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import static View.ChessBoardView.ROW_COUNT;

public class BoardController implements MouseListener {

    private ChessBoardView chessBoardView;
    public static Color currentPlayer = Color.WHITE;
    private static final int DELAY = 100;
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
                    animation(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row);
                    chessBoardView.getBoard()[(int)chessBoardView.getSelectedFigure().getRow()][(int)chessBoardView.getSelectedFigure().getCol()] = null;
                    chessBoardView.getBoard()[row][col] = chessBoardView.getSelectedFigure(); // Zapise do pole figuru
                    chessBoardView.getSelectedFigure().setRow(row);
                    chessBoardView.getSelectedFigure().setCol(col);
                }
                chessBoardView.setSelectedFigure(null);
                chessBoardView.repaint();
            }
        }
    }

    private void animation(int selectedFigureX, int selectedFigureY, int col, int row) {

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

    /*
    public boolean isCheck() {
        Figure king = null;
        // Находим короля текущего игрока
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure instanceof King && figure.getColor() == currentPlayer) {
                    king = figure;
                    break;
                }
            }
        }
        // Проверяем, есть ли у противника фигуры, которые могут ударить короля
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure != null && figure.getColor() != currentPlayer) {
                    if (figure.moveTo(king.getCol(), king.getRow(), col, row, chessBoardView.getBoard())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate() {
        // Проверяем, есть ли возможность убрать короля из-под шаха
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure != null && figure.getColor() == currentPlayer) {
                    for (int i = 0; i < ROW_COUNT; i++) {
                        for (int j = 0; j < ROW_COUNT; j++) {
                            if (figure.moveTo(col, row, j, i, chessBoardView.getBoard())
                                    && !isCheckAfterMove(col, row, j, i)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        // Если нет возможности убрать короля из-под шаха, то это мат
        return true;
    }

    public boolean isStalemate() {
        // Проверяем, есть ли возможность сделать ход
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure != null && figure.getColor() == currentPlayer) {
                    for (int i = 0; i < ROW_COUNT; i++) {
                        for (int j = 0; j < ROW_COUNT; j++) {
                            if (figure.moveTo(col, row, j, i, chessBoardView.getBoard())
                                    && !isCheckAfterMove(col, row, j, i)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isCheckAfterMove(int fromX, int fromY, int toX, int toY, Figure[][] board, Color currentPlayer) {
        // Сделать ход на доске.
        Figure movedFigure = board[toY][toX];
        board[toY][toX] = board[fromY][fromX];
        board[fromY][fromX] = null;
        board[toY][toX].setCol(toX);
        board[toY][toX].setRow(toY);
        // Проверить, находится ли король текущего игрока под шахом.
        boolean isCheck = isCheck();
        // Вернуть доску в исходное состояние.
        board[fromY][fromX] = board[toY][toX];
        board[fromY][fromX].setCol(fromX);
        board[fromY][fromX].setRow(fromY);
        board[toY][toX] = movedFigure;
        return isCheck;
    }

     */

}
