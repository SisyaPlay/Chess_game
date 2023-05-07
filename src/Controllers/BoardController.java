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

/**
 * Trida kontroler, implementuje mouseListener a ma v sobe pravidla sachu
 */
public class BoardController implements MouseListener {

    private ChessBoardView chessBoardView; // Trida, ktera vykresli sachy
    private static Color currentPlayer = Color.WHITE; // Aktualni hrac
    private int countOfCheckWhite = 0; // Pocet sahu pro bileho hrace
    private int countOfCheckBlack = 0; // Pocet sahu pro cerneho hrace
    private boolean animationIsOn = false; // Kontrola jestli animate zapnuto
    private Timer wTimer; // Casovac bileho hrace
    private int wTimeCounter = 0; // Pocet secund
    private Timer bTimer; // Casovac cerneho hrace
    private int bTimeCounter = 0; // Pocet secund
    private final int DELAY = 1000; // Zpozdeni 1 sekundu
    private int wCount = 0; // Pocet tahu bileho hrace
    private int bCount = 0; // Pocet tahu cerneho hrace
    private ArrayList<Integer> whiteTime = new ArrayList<>(); // List sekund bileho hrace
    private ArrayList<Integer> blackTime = new ArrayList<>(); // List sekund cerneho hrace
    private ArrayList<Integer> whiteCountOfMove = new ArrayList<>(); // List poctu tahu bileho hrace
    private ArrayList<Integer> blackCountOfMove = new ArrayList<>(); // List poctu tahu bileho hrace

    /**
     * Konstruktor tridy BoardController.
     * Nastavi graficky tridu, zpusti casovaci a prida ji do listu
     * @param chessBoardView
     */
    public BoardController(ChessBoardView chessBoardView) {
        this.chessBoardView = chessBoardView;
        whiteStart();
        blackStart();
        blackStop();
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

    /**
     * Proměna pesce
     */
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

    /**
     * Aktualni hrac
     * @param row sloupec
     * @param col rada
     * @return
     */
    private boolean currentPlayerMove(int row, int col) {
        if(chessBoardView.getSelectedFigureX() == col) { // jestli figura se neposunula, aktualni hrac se nesmeni
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

    /**
     * Kontrola na mat
     * @return
     */
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

    /**
     * Kontrola na pat
     * @return
     */
    public boolean isStalemate() {
        Figure king = null;

        // Hlada krala aktualniho hrace
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                Figure figure = chessBoardView.getBoard()[row][col];
                if (figure instanceof King && figure.getColor().equals(currentPlayer)) {
                    king = figure;
                    break;
                }
            }
        }

        // Kontroluje je-li kral pod utokem
        if (king.isUnderAttack(king.getCol(), king.getRow(), chessBoardView.getBoard())) {
            return false;
        }

        // Kontroluje pokud figury ma tahy
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
        JOptionPane.showMessageDialog(null, "Stalemate!");
        return true;
    }

    /**
     * Zpusti casovac pro bileho hrace
     */
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

    /**
     * Zpusti casovac pro cerneho hrace
     */
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

    /**
     * Zastavi casovac pro bileho hrace
     */
    private void whiteStop() {
        wTimer.stop();
    }

    /**
     * Zastavi casovac pro cerneho hrace
     */
    private void blackStop() {
        bTimer.stop();
    }

    /**
     * Obnovi text aktualnim casem
     */
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

    /**
     * Vrati list sekund bileho hrace
     * @return
     */
    public ArrayList<Integer> getWhiteTime() {
        return whiteTime;
    }

    /**
     * Vrati list sekund cerneho hrace
     * @return
     */
    public ArrayList<Integer> getBlackTime() {
        return blackTime;
    }

    /**
     * Vrati list tahu bileho hrace
     * @return
     */
    public ArrayList<Integer> getWhiteCountOfMove() {
        return whiteCountOfMove;
    }

    /**
     * Vrati list tahu cerneho hrace
     * @return
     */
    public ArrayList<Integer> getBlackCountOfMove() {
        return blackCountOfMove;
    }

    /**
     * Nastavi aktualniho hrace
     * @param currentPlayer
     */
    public static void setCurrentPlayer(Color currentPlayer) {
        BoardController.currentPlayer = currentPlayer;
    }

    /**
     * Nastavi pocet sekund bileho hrace
     * @param wTimeCounter
     */
    public void setwTimeCounter(int wTimeCounter) {
        this.wTimeCounter = wTimeCounter;
    }

    /**
     * Nastavi pocet sekund cerneho hrace
     * @param bTimeCounter
     */
    public void setbTimeCounter(int bTimeCounter) {
        this.bTimeCounter = bTimeCounter;
    }

    /**
     * Vrati casovac bileho hrace
     * @return
     */
    public Timer getwTimer() {
        return wTimer;
    }

    /**
     * Vrati casovac cerneho hrace
     * @return
     */
    public Timer getbTimer() {
        return bTimer;
    }

    /**
     * Nastavi jestli animace zapnuta
     * @param animationIsOn
     */
    public void setAnimationIsOn(boolean animationIsOn) {
        this.animationIsOn = animationIsOn;
    }
}
