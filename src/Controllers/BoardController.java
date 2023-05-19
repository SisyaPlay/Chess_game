package Controllers;

import Engine.Stockfish;
import Figures.Figure;
import Figures.King;
import Figures.Pawns;
import Figures.Queen;
import View.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import static View.ChessBoardView.ROW_COUNT;

/**
 * Trida kontroler, implementuje mouseListener a ma v sobe pravidla sachu
 */
public class BoardController implements MouseListener{

    private ChessBoardView chessBoardView; // Trida, ktera vykresli sachy
    private static Color currentPlayer = Color.WHITE; // Aktualni hrac
    private int countOfCheckWhite = 0; // Pocet sahu pro bileho hrace
    private int countOfCheckBlack = 0; // Pocet sahu pro cerneho hrace
    // private boolean animationIsOn; // Flag pro zakazani pouziti mysi behem animaci
    private int minToSec;
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
    public int WqC = 0, WbC = 0, WkC = 0, WrC = 0, WpC = 0;
    public int BqC = 0, BbC = 0, BkC = 0, BrC = 0, BpC = 0;
    public String positionAfter = null;
    public String positionBefore = null;
    public int bTotalSeconds;
    private Stockfish sf;
    private Figure killableFigure = null;

    /**
     * Konstruktor tridy BoardController.
     * Nastavi graficky tridu, zpusti casovaci a prida ji do listu
     * @param chessBoardView
     */
    public BoardController(ChessBoardView chessBoardView, Boolean playVSBot) {
        this.chessBoardView = chessBoardView;
        whiteTime.add(wTimeCounter);
        blackTime.add(bTimeCounter);
        whiteCountOfMove.add(wCount);
        blackCountOfMove.add(bCount);

        if(playVSBot) {
            sf = new Stockfish(this);
        }
    }

    /**
     * Posluchac mysi reaguje na kliknuti mysi.
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        if (chessBoardView.isAnimationOnProcess()) {
            return;
        }

        // Zakazat hrat za cerneho hrace jestli je hra proti botu
        if (currentPlayer == Color.BLACK && sf != null) {
            return;
        }

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

        // Ulozi vybranu figuru
        chessBoardView.setSelectedFigure(chessBoardView.getBoard()[row][col]);
        if (chessBoardView.getSelectedFigure() != null) {
            chessBoardView.setSelectedFigureX(col);
            chessBoardView.setSelectedFigureY(row);
            positionAfter = codeCoord(col, row);
        }
        if (chessBoardView.getSelectedFigure() != null &&
                chessBoardView.getSelectedFigure().getColor() == currentPlayer) {
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
            if (col > ROW_COUNT - 1 || row > ROW_COUNT - 1 || col < 0 || row < 0 || x < 0 || y < 0) {
                chessBoardView.getBoard()[chessBoardView.getSelectedFigureY()][chessBoardView.getSelectedFigureX()] = chessBoardView.getSelectedFigure();
            }
            killableFigure = chessBoardView.getBoard()[row][col];
            moveFigure(col, row);
        }
    }

    public void move(int col, int row) {
        chessBoardView.animate(col, row);
        if(killableFigure != null) {
            eatFigure(killableFigure);
        }
        chessBoardView.getBoard()[chessBoardView.getSelectedFigure().getRow()][chessBoardView.getSelectedFigure().getCol()] = null;
        chessBoardView.getBoard()[row][col] = chessBoardView.getSelectedFigure(); // Zapise do pole figuru
        chessBoardView.getSelectedFigure().setRow(row);
        chessBoardView.getSelectedFigure().setCol(col);
        positionBefore = codeCoord(col, row);
        chessBoardView.addCountOfBeingSelected();
        chessBoardView.setLastSelectedFigureX(col);
        chessBoardView.setLastSelectedFigureY(row);
        chessBoardView.setStartSelectedFigureX(chessBoardView.getSelectedFigureX());
        chessBoardView.setStartSelectedFigureY(chessBoardView.getSelectedFigureY());
        currentPlayerMove();
        changePawnToQueen();
        if (isCheckmate()) {
            chessBoardView.restart();
            countOfCheckWhite = 0;
            countOfCheckBlack = 0;
        }
        if (isStalemate()) {
            chessBoardView.restart();
            countOfCheckWhite = 0;
            countOfCheckBlack = 0;
        }
    }

    /**
     *
     * @param col   new column position
     * @param row   new row position
     */
    public void moveFigure(int col, int row) {
        if(!kingIsUnderAttack(chessBoardView.getBoard()) && !(chessBoardView.getSelectedFigure() instanceof King)) {
            if (chessBoardView.getSelectedFigure().moveTo(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row, chessBoardView.getBoard()) &&
                    posibleCheck(col, row, chessBoardView.getBoard()) &&
                    makeAMove(row, col) && chessBoardView.getSelectedFigure().getColor().equals(currentPlayer)) {
                move(col, row);
            }
        } else if(!(chessBoardView.getSelectedFigure() instanceof King)) {
            if(chessBoardView.getSelectedFigure().canSafeKing(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(),
                    col, row, chessBoardView.getBoard()) && makeAMove(row, col) && chessBoardView.getSelectedFigure().getColor().equals(currentPlayer)) {
                move(col, row);
            }
        } else {
            if(isSaveForKing(col, row, chessBoardView.getBoard()) &&
                    chessBoardView.getSelectedFigure().moveTo(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row, chessBoardView.getBoard())) {
                move(col, row);
            }
        }

    }

    private boolean posibleCheck(int col, int row, Figure[][] board) {
        Figure figure = chessBoardView.getSelectedFigure();
        Figure king = null;
        for (int y = 0; y < ROW_COUNT; y++) {
            for (int x = 0; x < ROW_COUNT; x++) {
                Figure kFigure = board[y][x];
                if(kFigure instanceof King && kFigure.getColor().equals(currentPlayer)) {
                    king = kFigure;
                    break;
                }
            }
        }

        if(figure.moveTo(chessBoardView.getSelectedFigureX(), chessBoardView.getSelectedFigureY(), col, row, board)) {
            Figure prevFigure = board[figure.getRow()][figure.getCol()];
            board[figure.getRow()][figure.getCol()] = null;
            board[row][col] = figure;
            boolean isSafe = king != null && !king.isUnderAttack(king.getCol(), king.getRow(), board); // проверяем, является ли новое место безопасным
            board[row][col] = null; // удаляем короля с нового места
            board[figure.getRow()][figure.getCol()] = prevFigure; // восстанавливаем предыдущую фигуру на место короля
            return isSafe;
        }
        return false;
    }

    private Figure findKing(Figure[][] board) {
        Figure king = null;
        for (int y = 0; y < ROW_COUNT; y++) {
            for (int x = 0; x < ROW_COUNT; x++) {
                Figure figure = board[y][x];
                if(figure instanceof King && figure.getColor().equals(currentPlayer)) {
                    king = figure;
                    break;
                }
            }
        }
        return king;
    }


    private String codeCoord(int x, int y) {
        String outputX = null;
        String outputY = null;
        switch (x) {
            case 0 -> outputX = "a";
            case 1 -> outputX = "b";
            case 2 -> outputX = "c";
            case 3 -> outputX = "d";
            case 4 -> outputX = "e";
            case 5 -> outputX = "f";
            case 6 -> outputX = "g";
            case 7 -> outputX = "h";
        }
        switch (y) {
            case 0 -> outputY = "8";
            case 1 -> outputY = "7";
            case 2 -> outputY = "6";
            case 3 -> outputY = "5";
            case 4 -> outputY = "4";
            case 5 -> outputY = "3";
            case 6 -> outputY = "2";
            case 7 -> outputY = "1";
        }
        return outputX + outputY;
    }

    private void eatFigure(Figure figure) {
        if(figure.getColor().equals(Color.WHITE)) {
            switch (figure.getType()) {
                case PAWNS:
                    BpC++;
                    chessBoardView.getBlackFigureView().setPawnsCount(BpC);
                    break;
                case KNIGHT:
                    BkC++;
                    chessBoardView.getBlackFigureView().setKnightCount(BkC);
                    break;
                case BISHOP:
                    BbC++;
                    chessBoardView.getBlackFigureView().setBishopCount(BbC);
                    break;
                case QUEEN:
                    BqC++;
                    chessBoardView.getBlackFigureView().setQueenCount(BqC);
                    break;
                case ROOK:
                    BrC++;
                    chessBoardView.getBlackFigureView().setRookCount(BrC);
                    break;
            }
        } else {
            switch (figure.getType()) {
                case PAWNS:
                    WpC++;
                    chessBoardView.getWhiteFigureView().setPawnsCount(WpC);
                    break;
                case KNIGHT:
                    WkC++;
                    chessBoardView.getWhiteFigureView().setKnightCount(WkC);
                    break;
                case BISHOP:
                    WbC++;
                    chessBoardView.getWhiteFigureView().setBishopCount(WbC);
                    break;
                case QUEEN:
                    WqC++;
                    chessBoardView.getWhiteFigureView().setQueenCount(WqC);
                    break;
                case ROOK:
                    WrC++;
                    chessBoardView.getWhiteFigureView().setRookCount(WrC);
                    break;
            }
        }
    }

    private boolean isSaveForKing(int col, int row, Figure[][] board) {
        if(chessBoardView.getSelectedFigure() instanceof King) {
            Figure king = chessBoardView.getSelectedFigure();
            return king != null && king.isThisPlaceIsSafe(col, row, board, king);
        }
        return false;
    }

    /**
     * Metoda vrati true, kdyz kral bude pod utokem
     * @param board
     * @return
     */
    public boolean kingIsUnderAttack(Figure[][] board) {
        Figure king = findKing(board);
        return king != null &&  king.isUnderAttack(king.getCol(), king.getRow(), board);
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
     * @return
     */
    private void currentPlayerMove() {
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
        }
    }

    private boolean makeAMove(int row, int col) {
        if(chessBoardView.getSelectedFigureX() == col) { // jestli figura se neposunula, aktualni hrac se nesmeni
            if(chessBoardView.getSelectedFigureY() == row){
                return false;
            }
        }
        return true;
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
        if(whiteKing != null && whiteKing.isUnderAttack(whiteKing.getCol(), whiteKing.getRow(), chessBoardView.getBoard())) {
            countOfCheckWhite++;
        } else {
            countOfCheckWhite = 0;
        }
        if(blackKing != null && blackKing.isUnderAttack(blackKing.getCol(), blackKing.getRow(), chessBoardView.getBoard())) {
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
        Figure king = findKing(chessBoardView.getBoard());

        // Kontroluje je-li kral pod utokem
        if (king != null && king.isUnderAttack(king.getCol(), king.getRow(), chessBoardView.getBoard())) {
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
    public void whiteStart() {
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
     * Black ~ StockFish
     * Zpusti casovac pro cerneho hrace
     */
    public void blackStart() {
        bTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bTimeCounter++;
                updateTimeLabel();
            }
        });
        bTimer.start();

        // Ziskat pozici od Stockfish
        if(sf != null) {
            try {
                sf.sendPositionCommand(positionAfter + positionBefore, bTotalSeconds * 1000);
                sf.output();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Zastavi casovac pro bileho hrace
     */
    public void whiteStop() {
        if (wTimer != null)
        {
            wTimer.stop();
        }
    }

    /**
     * Zastavi casovac pro cerneho hrace
     */
    public void blackStop() {
        if (bTimer != null)
        {
            bTimer.stop();
        }
    }

    /**
     * Obnovi text aktualnim casem
     */
    public void updateTimeLabel() {
        minToSec = chessBoardView.getGameMinuts() * 60;

        int wTotalSeconds = minToSec - wTimeCounter;
        int wMinutes = wTotalSeconds / 60;
        int wSeconds = wTotalSeconds % 60;

        bTotalSeconds = minToSec - bTimeCounter;
        int bMinutes = bTotalSeconds / 60;
        int bSeconds = bTotalSeconds % 60;

        String wMinutesString = (wMinutes < 10) ? "0" + wMinutes : Integer.toString(wMinutes);
        String wSecondsString = (wSeconds < 10) ? "0" + wSeconds : Integer.toString(wSeconds);

        String bMinutesString = (bMinutes < 10) ? "0" + bMinutes : Integer.toString(bMinutes);
        String bSecondsString = (bSeconds < 10) ? "0" + bSeconds : Integer.toString(bSeconds);

        String wTimeString = "White " + wMinutesString + ":" + wSecondsString;
        String bTimeString = "Black " + bMinutesString + ":" + bSecondsString;

        chessBoardView.setTextToTimer1(wTimeString);
        chessBoardView.setTextToTimer2(bTimeString);

        if(wMinutes == 0 && wSeconds == 0) {
            JOptionPane.showMessageDialog(null, "Black wins!");
            chessBoardView.restart();
        }
        if(bMinutes == 0 && bSeconds == 0) {
            JOptionPane.showMessageDialog(null, "White wins!");
            chessBoardView.restart();
        }
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

    public static Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void setSelectedFigure(int row, int col) {
        chessBoardView.setSelectedFigure(chessBoardView.getBoard()[row][col]);
    }

    public void setSelectedFigureX(int col) {
        chessBoardView.setSelectedFigureX(col);
    }

    public void setSelectedFigureY(int row) {
        chessBoardView.setSelectedFigureY(row);
    }

}
