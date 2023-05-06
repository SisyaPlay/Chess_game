package Controllers;

import Figures.Figure;
import Figures.King;
import Figures.Pawns;
import Figures.Queen;
import View.ChessBoardView;
import View.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static View.ChessBoardView.ROW_COUNT;

public class BoardController implements MouseListener {

    private ChessBoardView chessBoardView;
    public static Color currentPlayer = Color.WHITE;
    public int countOfCheckWhite = 0;
    public int countOfCheckBlack = 0;
    public boolean animationIsOn = false;
    public Timer wTimer;
    public int wTimeCounter = 0;
    public Timer bTimer;
    public int bTimeCounter = 0;
    private final int DELAY = 1000;
    private int wCount = 0;
    private int bCount = 0;
    public ArrayList<Integer> whiteTime = new ArrayList<>();
    public ArrayList<Integer> blackTime = new ArrayList<>();
    public ArrayList<Integer> whiteCountOfMove = new ArrayList<>();
    public ArrayList<Integer> blackCountOfMove = new ArrayList<>();

    public BoardController(ChessBoardView chessBoardView) {
        this.chessBoardView = chessBoardView;
        whiteStart();
        whiteTime.add(wTimeCounter);
        blackTime.add(bTimeCounter);
        whiteCountOfMove.add(wCount);
        blackCountOfMove.add(bCount);
    }

    /**
     * Posluchac mysi reaguje na kliknuti mysi.
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        if(animationIsOn) {
            return;
        } else {
            int x = e.getX() - chessBoardView.getShiftX();
            int y = e.getY() - chessBoardView.getShiftY();
            int row = y / chessBoardView.getSquare_size();
            int col = x / chessBoardView.getSquare_size();

            if (col > ROW_COUNT - 1) {
                col = ROW_COUNT - 1;
            }
            if (col < 0) {
                col = 0;
            }

            if (row > ROW_COUNT - 1) {
                row = ROW_COUNT - 1;
            }
            if (row < 0) {
                row = 0;
            }

            // Zapishe do chessBoard.getSelectedFigure() figuru z pole
            chessBoardView.setSelectedFigure(chessBoardView.getBoard()[row][col]);
            if (chessBoardView.getSelectedFigure() != null) {
                chessBoardView.setSelectedFigureX(col);
                chessBoardView.setSelectedFigureY(row);
            }
            if (chessBoardView.getSelectedFigure() != null && chessBoardView.getSelectedFigure().getColor() == currentPlayer) {
                chessBoardView.repaint();
            }
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
                    chessBoardView.getSelectedFigure().setRow(row);
                    chessBoardView.getSelectedFigure().setCol(col);
                    chessBoardView.addCountOfBeingSelected();
                    chessBoardView.setLastSelectedFigureX(col);
                    chessBoardView.setLastSelectedFigureY(row);
                    chessBoardView.setStartSelectedFigureX(chessBoardView.getSelectedFigureX());
                    chessBoardView.setStartSelectedFigureY(chessBoardView.getSelectedFigureY());
                }
                changePawnToQueen();
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
                whiteStop();
                int lastWhiteTime = whiteTime.get(whiteTime.size() - 1);
                whiteTime.add(wTimeCounter - lastWhiteTime);
                wCount++;
                whiteCountOfMove.add(wCount);
                blackStart();
            } else {
                currentPlayer = Color.WHITE;
                blackStop();
                int lastBlackTime = blackTime.get(blackTime.size() - 1);
                blackTime.add(bTimeCounter - lastBlackTime);
                bCount++;
                blackCountOfMove.add(bCount);
                whiteStart();
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

    public void setAnimationIsOn(boolean animationIsOn) {
        this.animationIsOn = animationIsOn;
    }

    private void whiteStart() {
        wTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wTimeCounter++;
                updateTimeLabel();
            }
        });
        wTimer.start();
    }
    private void blackStart() {
        bTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bTimeCounter++;
                updateTimeLabel();
            }
        });
        bTimer.start();
    }

    private void whiteStop() {
        wTimer.stop();
    }

    private void blackStop() {
        bTimer.stop();
    }

    private void reset() {
        wTimer.stop();
        wTimeCounter = 0;
        bTimer.stop();
        bTimeCounter = 0;
        updateTimeLabel();
    }

    public void updateTimeLabel() {
        int wMinutes = (wTimeCounter % 3600) / 60;
        int wSeconds = wTimeCounter % 60;

        int bMinutes = (bTimeCounter % 3600) / 60;
        int bSeconds = bTimeCounter % 60;

        String wMinutesString = (wMinutes < 10) ? "0" + wMinutes : Integer.toString(wMinutes);
        String wSecondsString = (wSeconds < 10) ? "0" + wSeconds : Integer.toString(wSeconds);

        String bMinutesString = (bMinutes < 10) ? "0" + bMinutes : Integer.toString(bMinutes);
        String bSecondsString = (bSeconds < 10) ? "0" + bSeconds : Integer.toString(bSeconds);

        String wTimeString = "White" + wMinutesString + ":" + wSecondsString;
        String bTimeString = "Black" + bMinutesString + ":" + bSecondsString;

        chessBoardView.timer1.setText(wTimeString);
        chessBoardView.timer2.setText(bTimeString);
    }

    public ArrayList<Integer> getWhiteTime() {
        return whiteTime;
    }

    public ArrayList<Integer> getBlackTime() {
        return blackTime;
    }

    public ArrayList<Integer> getWhiteCountOfMove() {
        return whiteCountOfMove;
    }

    public ArrayList<Integer> getBlackCountOfMove() {
        return blackCountOfMove;
    }
}
