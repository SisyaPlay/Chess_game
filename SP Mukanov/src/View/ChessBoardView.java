package View;

import Controllers.BoardController;
import Figures.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Trida View.ChessBoard dedi od JPanelu a implementuje MouseListener, MouseMotionListener.
 * Zde vykresli sachovnice 8x8 s bilimi a zasrafovanymi sedymi pruhy ctvercu.
 * Jeste tady implementovano drag and drop.
 */

public class ChessBoardView extends JPanel {

        public static final int ROW_COUNT = 8; // Pocet ctvercu
        private int square_size; // Velikost ctverce
        private Figure[][] board = new Figure[8][8]; // Pole, kde jsou figury
        private Figure selectedFigure; // Vybrana figura
        private int selectedFigureX; // Pozice vybranou figury podle osy X
        private int selectedFigureY; // Pozice vybranou figury podle osy Y
        private int startSelectedFigureX; // Pocatecni pozice vybranou figury podle osy X
        private int startSelectedFigureY; // Pocatecni pozice vybranou figury podle osy Y
        private int lastSelectedFigureX; // Konecna pozice vybranou figury podle osy X
        private int lastSelectedFigureY; // Konecna pozice vybranou figury podle osy Y
        private int shiftX; // Posun
        private int shiftY;
        private BoardController boardController; // Kontroler sachovnice
        private boolean doAnimate = false;              // Zobrazeni moznych pozic (zluta / modra / ..)
        private final int DELAY = 30; // Delka casu animace
        public int countOfBeingSelected = 0; // Pocet kolik bylo vybrano
        public KilledFigureView whiteFigureView; // JPanel pro vykres poctu zabitych figur
        public KilledFigureView blackFigureView;
        private int gameMinuts; // Pocet minut pro casovac
        private GameView gv;
        private Timer animationTimer; // Casovac pro animace


        /**
         * Konstruktor sachonvnice.
         * Zde je ComponentListener, kdery zmemi rozmer figur a sachovnice podle velikosti okna.
         * Nastavi preferovanou velikost sachovnice a inicializuje figury do dvojite pole board.
         * A zavola posluchac mysi.
         */
        public ChessBoardView(GameView gv, boolean playVSBot) {
                this.boardController = new BoardController(this, playVSBot);
                this.gv = gv;
                //setBackground(Color.BLUE);
                setPreferredSize(new Dimension(square_size * ROW_COUNT, square_size * ROW_COUNT));
                addMouseListener(boardController);

                whiteFigureView = new KilledFigureView(Color.BLACK, 45);
                blackFigureView = new KilledFigureView(Color.WHITE, 45);

                for (int i = 0; i < 8; i++) {
                        board[1][i] = new Pawns( i, 1, Color.BLACK, square_size);
                        board[6][i] = new Pawns( i, 6, Color.WHITE, square_size);
                }
                board[0][0] = new Rook( 0, 0, Color.BLACK, square_size);
                board[0][7] = new Rook( 7, 0, Color.BLACK, square_size);
                board[7][0] = new Rook( 0, 7, Color.WHITE, square_size);
                board[7][7] = new Rook( 7, 7, Color.WHITE, square_size);

                board[0][4] = new King(4, 0, Color.BLACK, square_size);
                board[7][4] = new King(4, 7, Color.WHITE, square_size);

                board[0][3] = new Queen(3, 0, Color.BLACK, square_size);
                board[7][3] = new Queen(3, 7, Color.WHITE, square_size);

                board[0][1] = new Knight( 1, 0, Color.BLACK, square_size);
                board[0][6] = new Knight( 6, 0, Color.BLACK, square_size);
                board[7][1] = new Knight( 1, 7, Color.WHITE, square_size);
                board[7][6] = new Knight( 6, 7, Color.WHITE, square_size);

                board[0][2] = new Bishop( 2, 0, Color.BLACK, square_size);
                board[0][5] = new Bishop( 5, 0, Color.BLACK, square_size);
                board[7][2] = new Bishop( 2, 7, Color.WHITE, square_size);
                board[7][5] = new Bishop( 5, 7, Color.WHITE, square_size);


                // Pat, Mat
//                board[1][6] = new King(6, 1, Color.WHITE, square_size);
//                board[3][5] = new Queen(5, 3, Color.BLACK, square_size);
//                board[2][1] = new King(1, 2, Color.BLACK, square_size);
//                board[1][6].setCountOfMove(5);
//                board[2][1].setCountOfMove(5);

                // Mat
//                board[7][4] = new King(4, 7, Color.WHITE, square_size);
//                board[0][4] = new King(4, 0, Color.BLACK, square_size);
//                board[6][4] = new Pawns(4, 6, Color.WHITE, square_size);
//                board[6][3] = new Pawns(3, 6, Color.WHITE, square_size);
//                board[7][3] = new Queen(3, 7, Color.WHITE, square_size);
//                board[6][7] = new Queen(7, 6, Color.BLACK, square_size);
//                board[7][5] = new Bishop(5, 7, Color.WHITE, square_size);
//                board[4][6] = new Knight(6, 4, Color.BLACK, square_size);
//                BoardController.setCurrentPlayer(Color.BLACK);

                // Mat
//                board[0][4] = new King(4, 0, Color.BLACK, square_size);
//                board[4][2] = new Knight(2, 4, Color.BLACK, square_size);
//                board[6][2] = new Pawns(2, 6, Color.WHITE, square_size);
//                board[6][0] = new Queen(0, 6, Color.BLACK, square_size);
//                board[7][2] = new King(2, 7, Color.WHITE, square_size);
//                board[7][3] = new Queen(3, 7, Color.WHITE, square_size);
//                BoardController.setCurrentPlayer(Color.BLACK);
//                board[0][4].setCountOfMove(4);
//                board[7][2].setCountOfMove(4);

                // Sach
//                board[0][4] = new King(4, 0, Color.BLACK, square_size);
//                board[7][4] = new King(4, 7, Color.WHITE, square_size);
//                board[7][7] = new Queen(7, 7, Color.BLACK, square_size);
//                board[7][5] = new Bishop(5, 7, Color.WHITE, square_size);
//                board[5][7] = new Bishop(7, 5, Color.BLACK, square_size);
//                board[7][3] = new Queen(3, 7, Color.WHITE, square_size);
//                board[6][5] = new Pawns( 5, 6, Color.WHITE, square_size);
//                board[5][4] = new Pawns( 4, 5, Color.WHITE, square_size);
//                board[6][3] = new Pawns( 3, 6, Color.WHITE, square_size);

        }

        /**
         * Standartni metoda JPanelu.
         * Tady vykresli sachovnice a figury.
         * @param g the <code>Graphics</code> object to protect
         */
        public void paintComponent(Graphics g) {
                super.paintComponent(g);

                square_size = Math.min(getHeight(), getWidth()) / ROW_COUNT;
                shiftX = (getWidth() - (ROW_COUNT * square_size)) / 2;
                shiftY = (getHeight() - (ROW_COUNT * square_size)) / 2;

                for (int row = 0; row < ROW_COUNT; row++) {
                        for (int col = 0; col < ROW_COUNT; col++) {
                                Figure fgr = board[row][col];
                                if (fgr != null) {
                                        fgr.setSize(square_size);
                                }
                        }
                }

                Graphics2D g2 = (Graphics2D) g;
                g2.translate(shiftX, shiftY);



                // Vykresli sachovnice. Poprve zasrafovane ctverce, potom bile...
                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                if ((row + col) % 2 != 0) {
                                        g.setColor(Color.WHITE);
                                        g.fillRect(col * square_size, row * square_size, square_size, square_size);
                                        drawHetch(g, row, col); // Metoda, ktera dela srafovani
                                }
                        }
                }

                // Vykresli bile ctverce.
                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                if ((row + col) % 2 == 0) {
                                        g.setColor(Color.WHITE);
                                        g.fillRect(col * square_size, row * square_size, square_size, square_size);
                                }
                        }
                }

                // Vykresli figury
                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                Figure piece = board[row][col];
                                if (piece != null) {
                                        piece.paintComponent(g);
                                }
                        }
                }

                if(countOfBeingSelected != 0) {
                        drawLastStep(g2);
                }

                // Kresli mozne tahi
                if(!doAnimate && selectedFigure != null) {
                        switch(selectedFigure.getType()) {
                                case PAWNS:
                                        showAvailableMoveToPawn(g2);
                                        break;
                                case KNIGHT:
                                        showAvailableMoveToKnight(g2);
                                        break;
                                case BISHOP:
                                        showAvailableMoveToBishop(g2);
                                        break;
                                case KING:
                                        showAvailableMoveToKing(g2);
                                        break;
                                case QUEEN:
                                        showAvailableMoveToQueen(g2);
                                        break;
                                case ROOK:
                                        showAvailableMoveToRook(g2);
                                        break;
                        }
                }
        }

        /**
         * Vykresli odkud se presunula figura
         * @param g2
         */
        private void drawLastStep(Graphics2D g2) {
                g2.setColor(Color.ORANGE);
                g2.drawRect(startSelectedFigureX * square_size, startSelectedFigureY * square_size, square_size, square_size);
                g2.drawRect(lastSelectedFigureX * square_size, lastSelectedFigureY * square_size, square_size, square_size);
        }

        /**
         * Mozny tah pro vez
         * @param g2
         */
        private void showAvailableMoveToRook(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                int[] colOffsets = {1, 0, -1, 0};
                int[] rowOffsets = {0, 1, 0, -1};
                Figure king = boardController.findKing(board);
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];
                        boolean underAttack = king.isUnderAttack(king.getCol(), king.getRow(), board);
                        if(!underAttack) {
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Je-li na ceste protihrac
                                        if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(newX, newY, square_size, square_size);
                                                g2.setColor(Color.BLUE);
                                        }
                                        // Kontroluje jestli na ceste je figura sve barvy
                                        if (board[newRow][newCol] != null) {
                                                break;
                                        }

                                        g2.drawRect(newX, newY, square_size, square_size);

                                        newRow += rowOffsets[i];
                                        newCol += colOffsets[i];
                                }
                        } else {
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Kontroluje jestli muze zachranit krale
                                        if(selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, newCol, newRow, board)) {
                                                if (board[newRow][newCol] == null) {
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                } else if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                        g2.setColor(Color.RED);
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                }
                                        }

                                        newRow += rowOffsets[i];
                                        newCol += colOffsets[i];
                                }
                        }
                }
        }

        /**
         * Mozny tah pro damu
         * @param g2
         */
        private void showAvailableMoveToQueen(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                Figure king = boardController.findKing(board);
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);

                int[] rowOffsets = {-1, -1, 1, 1};
                int[] colOffsets = {1, -1, 1, -1};
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];
                        boolean underAttack = king.isUnderAttack(king.getCol(), king.getRow(), board);
                        // Kontroluje jestli kral pod utokem
                        if(!underAttack) {
                                // Pokracuje kreslit po diagonale
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Je-lo na diagonale protihrac
                                        if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(newX, newY, square_size, square_size);
                                                g2.setColor(Color.BLUE);
                                        }
                                        // Je-li na diagonale figura sve barvy
                                        if (board[newRow][newCol] != null) {
                                                break;
                                        }

                                        g2.drawRect(newX, newY, square_size, square_size);

                                        newRow += rowOffsets[i];
                                        newCol += colOffsets[i];
                                }
                        } else {
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Kontroluje jestli muze zachranit krale
                                        if(selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, newCol, newRow, board)) {
                                                if (board[newRow][newCol] == null) {
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                } else if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                        g2.setColor(Color.RED);
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                }
                                        }

                                        newRow += rowOffsets[i];
                                        newCol += colOffsets[i];
                                }
                        }
                }

                int[] colOffsetsGor = {1, 0, -1, 0};
                int[] rowOffsetsGor = {0, 1, 0, -1};
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsetsGor.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsetsGor[i];
                        int newCol = selectedFigure.getCol() + colOffsetsGor[i];
                        boolean underAttack = king.isUnderAttack(king.getCol(), king.getRow(), board);
                        if(!underAttack) {
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Je-li na ceste protihrac
                                        if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(newX, newY, square_size, square_size);
                                                g2.setColor(Color.BLUE);
                                        }
                                        // Kontroluje jestli na ceste je figura sve barvy
                                        if (board[newRow][newCol] != null) {
                                                break;
                                        }

                                        g2.drawRect(newX, newY, square_size, square_size);

                                        newRow += rowOffsetsGor[i];
                                        newCol += colOffsetsGor[i];
                                }
                        } else {
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Kontroluje jestli muze zachranit krale
                                        if(selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, newCol, newRow, board)) {
                                                if (board[newRow][newCol] == null) {
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                } else if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                        g2.setColor(Color.RED);
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                }
                                        }

                                        newRow += rowOffsetsGor[i];
                                        newCol += colOffsetsGor[i];
                                }
                        }
                }
        }

        /**
         * Mozny tah pro krala
         * @param g2
         */
        private void showAvailableMoveToKing(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);
                int[] xOffset = {-1, 0, 1, -1, 1, -1, 0, 1};
                int[] yOffset = {-1, -1, -1, 0, 0, 1, 1, 1};
                for (int i = 0; i < xOffset.length; i++) {
                        int row = selectedFigure.getRow() + yOffset[i];
                        int col = selectedFigure.getCol() + xOffset[i];
                        if (row >= 0 && row < ROW_COUNT && col >= 0 && col < ROW_COUNT) {
                                if (selectedFigure instanceof King && selectedFigure.isThisPlaceIsSafe(col, row, board, selectedFigure)) { // Mozne bezpecne tahy
                                        g2.drawRect(col * square_size, row * square_size, square_size, square_size);
                                }
                                if (board[row][col] != null && board[row][col].getColor() != selectedFigure.getColor() &&
                                        selectedFigure.isThisPlaceIsSafe(col, row, board, selectedFigure)) { // Kontroluje jestli je mozne zabit figuru
                                        g2.setColor(Color.RED);
                                        g2.drawRect(col * square_size, row * square_size, square_size, square_size);
                                        g2.setColor(Color.BLUE);
                                }
                        }
                }

                // Rosada
                if(selectedFigure.getCountOfMove() == 0 && (selectedFigure.getRow() == 0 || selectedFigure.getRow() == 7) && selectedFigure.getCol() == 4) {
                        if (board[selectedFigure.getRow()][selectedFigure.getCol() + 1] == null && board[selectedFigure.getRow()][selectedFigure.getCol() + 2] == null &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() + 3] instanceof Rook &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() + 3].getCountOfMove() == 0 &&
                                !selectedFigure.isUnderAttack(selectedFigureX, selectedFigureY, board)) {
                                g2.setColor(Color.GREEN);
                                g2.drawRect(x + 2 * square_size, y, square_size, square_size);
                        }
                        if (board[selectedFigure.getRow()][selectedFigure.getCol() - 1] == null && board[selectedFigure.getRow()][selectedFigure.getCol() - 2] == null &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() - 3] == null &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() - 4] instanceof Rook &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() - 4].getCountOfMove() == 0 &&
                                !selectedFigure.isUnderAttack(selectedFigureX, selectedFigureY, board)) {
                                g2.setColor(Color.GREEN);
                                g2.drawRect(x - 2 * square_size, y, square_size, square_size);
                        }
                }
        }

        /**
         * Mozny tah pro strelce
         * @param g2
         */
        private void showAvailableMoveToBishop(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                Figure king = boardController.findKing(board);
                g2.setColor(Color.BLUE);

                // Tahy po diagonale
                int[] colOffsets = {1, -1, 1, -1};
                int[] rowOffsets = {-1, -1, 1, 1};
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];
                        boolean underAttack = king.isUnderAttack(king.getCol(), king.getRow(), board);
                        // Kontroluje jestli kral pod utokem
                        if(!underAttack) {
                                // Pokracuje kreslit po diagonale
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Je-lo na diagonale protihrac
                                        if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(newX, newY, square_size, square_size);
                                                g2.setColor(Color.BLUE);
                                        }
                                        // Je-li na diagonale figura sve barvy
                                        if (board[newRow][newCol] != null) {
                                                break;
                                        }

                                        g2.drawRect(newX, newY, square_size, square_size);

                                        newRow += rowOffsets[i];
                                        newCol += colOffsets[i];
                                }
                        } else {
                                while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                        int newX = newCol * square_size;
                                        int newY = newRow * square_size;

                                        // Kontroluje jestli muze zachranit krale
                                        if(selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, newCol, newRow, board)) {
                                                if (board[newRow][newCol] == null) {
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                } else if (board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                                        g2.setColor(Color.RED);
                                                        g2.drawRect(newX, newY, square_size, square_size);
                                                }
                                        }

                                        newRow += rowOffsets[i];
                                        newCol += colOffsets[i];
                                }
                        }
                }
        }

        /**
         * Mozny tah pro jezdce
         * @param g2
         */
        private void showAvailableMoveToKnight(Graphics2D g2) {
                int sX = selectedFigure.getCol() * square_size;
                int sY = selectedFigure.getRow() * square_size;
                int[] rowOffsets = {-2, -1, 1, 2, 2, 1, -1, -2};
                int[] colOffsets = {1, 2, 2, 1, -1, -2, -2, -1};

                Figure king = boardController.findKing(board);

                g2.setColor(Color.BLUE);
                g2.drawRect(sX, sY, square_size, square_size);
                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];
                        if (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                boolean underAttack = king.isUnderAttack(king.getCol(), king.getRow(), board);
                                if (board[newRow][newCol] == null && !underAttack) {
                                        // Mozne tahy na prazdne misto
                                        int x = newCol * square_size;
                                        int y = newRow * square_size;
                                        g2.drawRect(x, y, square_size, square_size);
                                } else if(board[newRow][newCol] == null && underAttack && selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, newCol, newRow, board)) {
                                        // Muze zachranit krale
                                        int x = newCol * square_size;
                                        int y = newRow * square_size;
                                        g2.drawRect(x, y, square_size, square_size);
                                } else if (board[newRow][newCol] != null && board[newRow][newCol].getColor() != selectedFigure.getColor() && !underAttack) {
                                        // Muze zabit protihrace
                                        int x = newCol * square_size;
                                        int y = newRow * square_size;
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x, y, square_size, square_size);
                                        g2.setColor(Color.BLUE);
                                } else if (board[newRow][newCol] != null && board[newRow][newCol].getColor() != selectedFigure.getColor() && underAttack &&
                                        selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, newCol, newRow, board)) {
                                        // Muze zabit protihrace a zachranit krale
                                        int x = newCol * square_size;
                                        int y = newRow * square_size;
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x, y, square_size, square_size);
                                        g2.setColor(Color.BLUE);
                                }
                        }
                }

        }

        /**
         * Mozny tah pro pesce
         * @param g2
         */
        private void showAvailableMoveToPawn(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);
                Figure king = boardController.findKing(board);
                if (selectedFigure.getColor().equals(Color.WHITE)) {
                        if(!king.isUnderAttack(king.getCol(), king.getRow(), board)) {
                                // Kontroluje mozny tah na jeden cetverec
                                if (selectedFigure.getRow() > 0 && board[selectedFigure.getRow() - 1][selectedFigure.getCol()] == null) {
                                        g2.drawRect(x, y - square_size, square_size, square_size);
                                }
                                // Kontroluje mozny tah na dva cetverce z pocatecni pozice
                                if (selectedFigure.getRow() == 6) {
                                        if (board[selectedFigure.getRow() - 1][selectedFigure.getCol()] == null &&
                                                board[selectedFigure.getRow() - 2][selectedFigure.getCol()] == null) {
                                                g2.drawRect(x, y - square_size, square_size, square_size);
                                                g2.drawRect(x, y - 2 * square_size, square_size, square_size);
                                        }
                                }
                                // Kontroluje jestli muze zabit protihrace zleva
                                if (selectedFigure.getRow() > 0 && selectedFigure.getCol() > 0 &&
                                        board[selectedFigure.getRow() - 1][selectedFigure.getCol() - 1] != null &&
                                        !selectedFigure.getColor().equals(board[selectedFigure.getRow() - 1][selectedFigure.getCol() - 1].getColor())) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x - square_size, y - square_size, square_size, square_size);
                                }
                                // Kontroluje jestli muze zabit protihrace zprava
                                if (selectedFigure.getRow() > 0 && selectedFigure.getCol() < 7 &&
                                        board[selectedFigure.getRow() - 1][selectedFigure.getCol() + 1] != null &&
                                        !selectedFigure.getColor().equals(board[selectedFigure.getRow() - 1][selectedFigure.getCol() + 1].getColor())) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x + square_size, y - square_size, square_size, square_size);
                                }
                                // Kontroluje jestli muze vzit mimochodem zleva
                                if (selectedFigure.getCol() != 7) {
                                        if (board[selectedFigure.getRow()][selectedFigure.getCol() + 1] != null &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() + 1] instanceof Pawns &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getCountOfMove() == 1 &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getColor() != selectedFigure.getColor() &&
                                                ((Pawns) board[selectedFigure.getRow()][selectedFigure.getCol() + 1]).firstStep(board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getHistory())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(x + square_size, y, square_size, square_size);
                                                g2.setColor(Color.GREEN);
                                                g2.drawRect(x + square_size, y - square_size, square_size, square_size);
                                        }
                                }
                                // Kontroluje jestli muze vzit mimochodem zleva
                                if (selectedFigure.getCol() != 0) {
                                        if (board[selectedFigure.getRow()][selectedFigure.getCol() - 1] != null &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() - 1] instanceof Pawns &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getCountOfMove() == 1 &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getColor() != selectedFigure.getColor() &&
                                                ((Pawns) board[selectedFigure.getRow()][selectedFigure.getCol() - 1]).firstStep(board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getHistory())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(x - square_size, y, square_size, square_size);
                                                g2.setColor(Color.GREEN);
                                                g2.drawRect(x - square_size, y - square_size, square_size, square_size);
                                        }
                                }
                        } else {
                                // Kontroluje jestli muze zachranit krale
                                int[] xOffset = {-1, 1};
                                int[] yOffset = {-1, -2};
                                for (int i = 0; i < yOffset.length; i++) {
                                        if (selectedFigure.getRow() > 0 && board[selectedFigure.getRow() - 1][selectedFigure.getCol()] == null &&
                                                selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, (int)(x/square_size), (int)((y/square_size)+yOffset[i]), board)) {
                                                g2.drawRect(x, (y + yOffset[i] * square_size), square_size, square_size);
                                        }
                                }
                                for (int i = 0; i < xOffset.length; i++) {
                                        if(xOffset[i] + x / square_size > 0 && xOffset[i] + x / square_size < ROW_COUNT) {
                                                if(board[yOffset[0] + y  / square_size][xOffset[i] + x / square_size] != null &&
                                                        board[yOffset[0] + y  / square_size][xOffset[i] + x / square_size].getColor() != selectedFigure.getColor()) {
                                                        g2.setColor(Color.RED);
                                                        g2.drawRect(xOffset[i] * square_size + x, y + yOffset[0] * square_size, square_size, square_size);
                                                }
                                        }
                                }
                        }
                } else if (selectedFigure.getColor().equals(Color.BLACK)) {
                        if(!king.isUnderAttack(king.getCol(), king.getRow(), board)) {
                                if (selectedFigure.getRow() < ROW_COUNT - 1 && board[selectedFigure.getRow() + 1][selectedFigure.getCol()] == null) {
                                        g2.drawRect(x, y + square_size, square_size, square_size);
                                }

                                if (selectedFigure.getRow() == 1) {
                                        if (board[selectedFigure.getRow() + 1][selectedFigure.getCol()] == null &&
                                                board[selectedFigure.getRow() + 2][selectedFigure.getCol()] == null) {
                                                g2.drawRect(x, y + square_size, square_size, square_size);
                                                g2.drawRect(x, y + 2 * square_size, square_size, square_size);
                                        }
                                }
                                if (selectedFigure.getRow() < 7 && selectedFigure.getCol() > 0 &&
                                        board[selectedFigure.getRow() + 1][selectedFigure.getCol() - 1] != null &&
                                        !selectedFigure.getColor().equals(board[selectedFigure.getRow() + 1][selectedFigure.getCol() - 1].getColor())) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x - square_size, y + square_size, square_size, square_size);
                                }

                                if (selectedFigure.getRow() < 7 && selectedFigure.getCol() < 7 &&
                                        board[selectedFigure.getRow() + 1][selectedFigure.getCol() + 1] != null &&
                                        !selectedFigure.getColor().equals(board[selectedFigure.getRow() + 1][selectedFigure.getCol() + 1].getColor())) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x + square_size, y + square_size, square_size, square_size);
                                }
                                if (selectedFigure.getCol() != 7) {
                                        if (board[selectedFigure.getRow()][selectedFigure.getCol() + 1] != null &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() + 1] instanceof Pawns &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getCountOfMove() == 1 &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getColor() != selectedFigure.getColor() &&
                                                ((Pawns) board[selectedFigure.getRow()][selectedFigure.getCol() + 1]).firstStep(board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getHistory())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(x + square_size, y, square_size, square_size);
                                                g2.setColor(Color.GREEN);
                                                g2.drawRect(x + square_size, y + square_size, square_size, square_size);
                                        }
                                }
                                if (selectedFigure.getCol() != 0) {
                                        if (board[selectedFigure.getRow()][selectedFigure.getCol() - 1] != null &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() - 1] instanceof Pawns &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getCountOfMove() == 1 &&
                                                board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getColor() != selectedFigure.getColor() &&
                                                ((Pawns) board[selectedFigure.getRow()][selectedFigure.getCol() - 1]).firstStep(board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getHistory())) {
                                                g2.setColor(Color.RED);
                                                g2.drawRect(x - square_size, y, square_size, square_size);
                                                g2.setColor(Color.GREEN);
                                                g2.drawRect(x - square_size, y + square_size, square_size, square_size);
                                        }
                                }
                        } else {
                                int[] xOffset = {-1, 1};
                                int[] yOffset = {1, 2};
                                for (int i = 0; i < yOffset.length; i++) {
                                        if (selectedFigure.getRow() < ROW_COUNT && board[selectedFigure.getRow() + 1][selectedFigure.getCol()] == null &&
                                                selectedFigure.canSafeKing(selectedFigureX, selectedFigureY, (int)(x/square_size), (int)((y/square_size)+yOffset[i]), board)) {
                                                g2.drawRect(x, (y + yOffset[i] * square_size), square_size, square_size);
                                        }
                                }
                                for (int i = 0; i < xOffset.length; i++) {
                                        if(xOffset[i] + x / square_size > 0 && xOffset[i] + x / square_size < ROW_COUNT) {
                                                if(board[yOffset[0] + y  / square_size][xOffset[i] + x / square_size] != null &&
                                                        board[yOffset[0] + y  / square_size][xOffset[i] + x / square_size].getColor() != selectedFigure.getColor()) {
                                                        g2.setColor(Color.RED);
                                                        g2.drawRect(xOffset[i] * square_size + x, y + yOffset[0] * square_size, square_size, square_size);
                                                }
                                        }
                                }
                        }
                }
        }

        /**
         * Metoda udela srafovani ctverce
         * @param g Graphics
         * @param row rad
         * @param col sloupec
         */
        private void drawHetch(Graphics g, int row, int col) {
                Graphics2D g2 = (Graphics2D)g;
                int x = col * square_size;
                int y = row * square_size;
                g2.setStroke(new BasicStroke(3));
                g.setColor(Color.lightGray);
                for (int i = 0; i < square_size; i += 8) {
                        g.drawLine((int)x, (int)y + i, (int)x + i, (int)y);
                        g.drawLine((int)x + (int)square_size, (int)y + i, (int)x + i, (int)y + (int)square_size);
                }
        }

        /**
         * Vrati rozmer ctvrcu
         * @return
         */
        public int getSquare_size() {
                return square_size;
        }

        /**
         * Vrati pole figur
         * @return
         */
        public Figure[][] getBoard() {
                return board;
        }

        /**
         * Vrati vybranou figuru
         * @return
         */
        public Figure getSelectedFigure() {
                return selectedFigure;
        }

        /**
         * Vrati pozice vybranou figury podle osy X
         * @return
         */
        public int getSelectedFigureX() {
                return selectedFigureX;
        }

        /**
         * Vrati pozice vybranou figury podle osy Y
         * @return
         */
        public int getSelectedFigureY() {
                return selectedFigureY;
        }

        /**
         * Vrati posun sachovnice podle osy X
         * @return
         */
        public int getShiftX() {
                return shiftX;
        }

        /**
         * Vrati posun sachovnice podle osy Y
         * @return
         */
        public int getShiftY() {
                return shiftY;
        }

        /**
         * Nastavi vybranou figuru
         * @param selectedFigure
         */
        public void setSelectedFigure(Figure selectedFigure) {
                this.selectedFigure = selectedFigure;
        }

        /**
         * Nastavi pozice vybranou figury podle osy X
         * @return
         */
        public void setSelectedFigureX(int selectedFigureX) {
                this.selectedFigureX = selectedFigureX;
        }

        /**
         * Nastavi pozice vybranou figury podle osy Y
         * @return
         */
        public void setSelectedFigureY(int selectedFigureY) {
                this.selectedFigureY = selectedFigureY;
        }

        /**
         * Nastavi pocatecni pozice vybranou figury podle osy X
         * @return
         */
        public void setStartSelectedFigureX(int startSelectedFigureX) {
                this.startSelectedFigureX = startSelectedFigureX;
        }

        /**
         * Nastavi pocatecni pozice vybranou figury podle osy Y
         * @return
         */
        public void setStartSelectedFigureY(int startSelectedFigureY) {
                this.startSelectedFigureY = startSelectedFigureY;
        }

        /**
         * Nastavi posledni pozice vybranou figury podle osy X
         * @return
         */
        public void setLastSelectedFigureX(int lastSelectedFigureX) {
                this.lastSelectedFigureX = lastSelectedFigureX;
        }

        /**
         * Nastavi posledni pozice vybranou figury podle osy Y
         * @return
         */
        public void setLastSelectedFigureY(int lastSelectedFigureY) {
                this.lastSelectedFigureY = lastSelectedFigureY;
        }

        /**
         * Pridava pocet vybranych
         */
        public void addCountOfBeingSelected() {
                countOfBeingSelected++;
        }

        /**
         * Vrati pocet minut pro casovac
         * @return
         */
        public int getGameMinuts() {
                return gameMinuts;
        }

        /**
         * nastavi minuty pro casovac
         * @param gameMinuts
         */
        public void setGameMinuts(int gameMinuts) {
                this.gameMinuts = gameMinuts;
        }

        /**
         * Vrati kontroler sachovnice
         * @return
         */
        public BoardController getBoardController() {
                return boardController;
        }

        /**
         * Vrati JPanel zabitych figur cerneho hrace
         * @return
         */
        public KilledFigureView getWhiteFigureView() {
                return whiteFigureView;
        }

        /**
         * Vrati JPanel zabitych figur bileho hrace
         * @return
         */
        public KilledFigureView getBlackFigureView() {
                return blackFigureView;
        }

        /**
         * Restartuje
         */
        public void restart()  {
                board = new Figure[8][8];

                for (int i = 0; i < 8; i++) {
                        board[1][i] = new Pawns( i, 1, Color.BLACK, square_size);
                        board[6][i] = new Pawns( i, 6, Color.WHITE, square_size);
                }
                board[0][0] = new Rook( 0, 0, Color.BLACK, square_size);
                board[0][7] = new Rook( 7, 0, Color.BLACK, square_size);
                board[7][0] = new Rook( 0, 7, Color.WHITE, square_size);
                board[7][7] = new Rook( 7, 7, Color.WHITE, square_size);

                board[0][4] = new King(4, 0, Color.BLACK, square_size);
                board[7][4] = new King(4, 7, Color.WHITE, square_size);

                board[0][3] = new Queen(3, 0, Color.BLACK, square_size);
                board[7][3] = new Queen(3, 7, Color.WHITE, square_size);

                board[0][1] = new Knight( 1, 0, Color.BLACK, square_size);
                board[0][6] = new Knight( 6, 0, Color.BLACK, square_size);
                board[7][1] = new Knight( 1, 7, Color.WHITE, square_size);
                board[7][6] = new Knight( 6, 7, Color.WHITE, square_size);

                board[0][2] = new Bishop( 2, 0, Color.BLACK, square_size);
                board[0][5] = new Bishop( 5, 0, Color.BLACK, square_size);
                board[7][2] = new Bishop( 2, 7, Color.WHITE, square_size);
                board[7][5] = new Bishop( 5, 7, Color.WHITE, square_size);

                selectedFigure = null;

                BoardController.setCurrentPlayer(Color.WHITE);
                for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board.length; j++) {
                                if (board[i][j] != null) {
                                        board[i][j].setHistory(new ArrayList<Point2D[]>());
                                        board[i][j].setCountOfMove(0);
                                }
                        }
                }
                boardController.BbC = 0;
                boardController.BqC = 0;
                boardController.BkC = 0;
                boardController.BrC = 0;
                boardController.BpC = 0;
                boardController.WbC = 0;
                boardController.WqC = 0;
                boardController.WkC = 0;
                boardController.WrC = 0;
                boardController.WpC = 0;
                whiteFigureView.setEvNew();
                blackFigureView.setEvNew();
                countOfBeingSelected = 0;
                if(boardController.getSf() != null) {
                        boardController.getSf().restartEngine();
                }
                repaint();
                reset();
        }

        /**
         * Obnovi casovac
         */
        private void reset() {
                boardController.whiteStop();
                boardController.setwTimeCounter(0);
                boardController.blackStop();
                boardController.setbTimeCounter(0);
                boardController.updateTimeLabel();
                boardController.whiteStart();
        }

        /**
         * Animace posunu figur
         * @param endX konecna pozice na ose X
         * @param endY konecna pozice na ose Y
         */
        public void animate(int endX, int endY) {
                final double startX = selectedFigureX; // Pocatecna pozice na ose X
                final double startY = selectedFigureY; // Pocatecna pozice na ose X
                final double distanceX = Math.abs(endX - startX); // Vzdalenost podle osy X
                final double distanceY = Math.abs(endY - startY); // Vzdalenost podle osy Y
                final double totalDistance = Math.max(distanceX, distanceY); // Cesta animace
                final double stepSize = totalDistance / 10; // Krok animace
                final double xStep = distanceX / totalDistance * stepSize; // Krok animace podle osy X
                final double yStep = distanceY / totalDistance * stepSize; // Krok animace podle osy Y
                final int directionX = endX > startX ? 1 : -1; // Smer na ose X
                final int directionY = endY > startY ? 1 : -1; // Smer na ose X
                doAnimate = true; // Nastavi ze jde animace, nekresli mozne tahy figur
                //final long[] start = {0};
                animationTimer = new Timer(DELAY, new ActionListener() {
                        double x = startX;
                        double y = startY;
                        double distanceCovered = 0;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // Nastaveni novych souradnic
                                x += xStep * directionX;
                                y += yStep * directionY;
                                selectedFigure.setX(x);
                                selectedFigure.setY(y);
                                repaint();
                                distanceCovered += stepSize;

                                // Konecna pozice
                                if (distanceCovered >= totalDistance) {
                                        doAnimate = false;
                                        selectedFigure.setCol(endX);
                                        selectedFigure.setRow(endY);
                                        selectedFigure = null;
                                        ((Timer)e.getSource()).stop();
                                }
                        }
                });
                animationTimer.start();
        }

        /**
         * Vraci zda timer animace jeste bezi
         * @return true / false
         */
        public boolean isAnimationOnProcess() {
                if (animationTimer != null)
                        return animationTimer.isRunning();

                return false;
        }

        /**
         * Exportuje PNG obrazek
         * @param fileName nazev souboru
         */
        public void createPNGImage(String fileName) {
                BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2 = image.createGraphics();
                paintComponent(g2);

                try {
                        ImageIO.write(image, "png", new File(fileName));
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Linearni graf jednotlevich tahu
         * @return
         */
        public JFreeChart createXYGraf() {
                NumberAxis xAxis = new NumberAxis("Count of moves");
                xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                NumberAxis yAxis = new NumberAxis("Time in seconds");
                yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                JFreeChart chart = ChartFactory.createXYLineChart("Average time of move", null, null, createDatasetForXY());
                XYPlot plot = chart.getXYPlot();
                plot.setDomainAxis(xAxis);
                plot.setRangeAxis(yAxis);

                return chart;
        }

        /**
         * Sloupcovy graf
         * @return
         */
        public JFreeChart createBarGraf() {
                JFreeChart chart = ChartFactory.createBarChart("Average time of move", "Count of moves", "Time in seconds",
                        createDatasetFotBarChart(), PlotOrientation.VERTICAL, true, true, false);

                NumberAxis rangeAxis = (NumberAxis) chart.getCategoryPlot().getRangeAxis();
                rangeAxis.setTickUnit(new NumberTickUnit(1));
                rangeAxis.setNumberFormatOverride(new DecimalFormat("0s"));

                return chart;
        }

        /**
         * Databaza pro sloupcovy graf
         * @return
         */
        private CategoryDataset createDatasetFotBarChart() {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset ();

                for (int i = 1; i < boardController.getWhiteTime().size(); i++) {
                        dataset.addValue(boardController.getWhiteTime().get(i), "White", boardController.getWhiteCountOfMove().get(i));
                }
                for (int i = 1; i < boardController.getBlackTime().size(); i++) {
                        dataset.addValue(boardController.getBlackTime().get(i), "Black", boardController.getBlackCountOfMove().get(i));
                }
                return dataset;
        }

        /**
         * databaza pro linearni graf
         * @return
         */
        private XYDataset createDatasetForXY() {
                XYSeriesCollection dataset = new XYSeriesCollection();
                XYSeries series1 = new XYSeries("White");
                XYSeries series2 = new XYSeries("Black");

                for (int i = 0; i < boardController.getWhiteTime().size(); i++) {
                        series1.add(boardController.getWhiteCountOfMove().get(i), boardController.getWhiteTime().get(i));
                }
                for (int i = 0; i < boardController.getBlackTime().size(); i++) {
                        series2.add(boardController.getBlackCountOfMove().get(i), boardController.getBlackTime().get(i));
                }

                dataset.addSeries(series1);
                dataset.addSeries(series2);
                return dataset;
        }

        /**
         * Vytvori obrazek grafu v PNG
         * @param fileName nazev souboru
         * @param BarOrXY vyber slopoveho ci linearniho grafu
         */
        public void createPNGImageOfGraf(String fileName, boolean BarOrXY) {
                JFreeChart chart;
                if(BarOrXY) {
                        chart = createBarGraf();
                } else {
                        chart = createXYGraf();
                }
                int width = 640;
                int height = 480;

                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2 = image.createGraphics();
                chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
                g2.dispose();

                try {
                        ImageIO.write(image, "png", new File(fileName));
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Pusti casovac
         */
        public void startGame() {
                boardController.whiteStart();
        }

        /**
         * Nastavi text casovace bileho hrace
         * @param time
         */
        public void setTextToTimer1(String time) {
                gv.setTextToTimer1(time);
        }

        /**
         * Nastavi text casovace cerneho hrace
         * @param time
         */
        public void setTextToTimer2(String time) {
                gv.setTextToTimer2(time);
        }
}
