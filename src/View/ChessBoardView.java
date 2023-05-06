package View;

import Controllers.BoardController;
import Figures.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        private int startSelectedFigureX;
        private int startSelectedFigureY;
        private int lastSelectedFigureX;
        private int lastSelectedFigureY;
        private int shiftX; // Posun
        private int shiftY;
        private BoardController boardController;
        private boolean doAnimate = false;
        private final int DELAY = 15;
        public int countOfBeingSelected = 0;
        public JLabel timer1 = new JLabel();
        public JLabel timer2 = new JLabel();


        /**
         * Konstruktor sachonvnice.
         * Zde je ComponentListener, kdery zmemi rozmer figur a sachovnice podle velikosti okna.
         * Nastavi preferovanou velikost sachovnice a inicializuje figury do dvojite pole board.
         * A zavola posluchac mysi.
         */
        public ChessBoardView() {
                this.boardController = new BoardController(this);

                setPreferredSize(new Dimension(square_size * ROW_COUNT, square_size * ROW_COUNT));
                addMouseListener(boardController);


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


                /*
                board[0][7] = new King(7, 0, Color.BLACK, square_size);
                board[2][5] = new Queen(5, 2, Color.WHITE, square_size);
                board[2][1] = new King(1, 2, Color.WHITE, square_size);
                 */

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

        private void drawLastStep(Graphics2D g2) {
                g2.setColor(Color.ORANGE);
                g2.drawRect(startSelectedFigureX * square_size, startSelectedFigureY * square_size, square_size, square_size);
                g2.drawRect(lastSelectedFigureX * square_size, lastSelectedFigureY * square_size, square_size, square_size);
        }

        private void showAvailableMoveToRook(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                int[] colOffsets = {1, 0, -1, 0};
                int[] rowOffsets = {0, 1, 0, -1};
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];

                        // Пока можем продолжать двигаться по диагонали
                        while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                int newX = newCol * square_size;
                                int newY = newRow * square_size;

                                if(board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(newX, newY, square_size, square_size);
                                        g2.setColor(Color.BLUE);
                                }
                                // Если на пути по диагонали есть фигура, то останавливаемся
                                if (board[newRow][newCol] != null) {
                                        break;
                                }

                                g2.drawRect(newX, newY, square_size, square_size);

                                // Перемещаемся к следующей клетке по диагонали
                                newRow += rowOffsets[i];
                                newCol += colOffsets[i];
                        }
                }
        }


        private void showAvailableMoveToQueen(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);

                int[] rowOffsets = {-1, -1, 1, 1};
                int[] colOffsets = {1, -1, 1, -1};
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];

                        // Пока можем продолжать двигаться по диагонали
                        while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                int newX = newCol * square_size;
                                int newY = newRow * square_size;

                                if(board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(newX, newY, square_size, square_size);
                                        g2.setColor(Color.BLUE);
                                }
                                // Если на пути по диагонали есть фигура, то останавливаемся
                                if (board[newRow][newCol] != null) {
                                        break;
                                }

                                g2.drawRect(newX, newY, square_size, square_size);

                                // Перемещаемся к следующей клетке по диагонали
                                newRow += rowOffsets[i];
                                newCol += colOffsets[i];
                        }
                }

                int[] colOffsetsGor = {1, 0, -1, 0};
                int[] rowOffsetsGor = {0, 1, 0, -1};
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsetsGor.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsetsGor[i];
                        int newCol = selectedFigure.getCol() + colOffsetsGor[i];

                        // Пока можем продолжать двигаться по диагонали
                        while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                int newX = newCol * square_size;
                                int newY = newRow * square_size;

                                if(board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(newX, newY, square_size, square_size);
                                        g2.setColor(Color.BLUE);
                                }
                                // Если на пути по диагонали есть фигура, то останавливаемся
                                if (board[newRow][newCol] != null) {
                                        break;
                                }

                                g2.drawRect(newX, newY, square_size, square_size);

                                // Перемещаемся к следующей клетке по диагонали
                                newRow += rowOffsetsGor[i];
                                newCol += colOffsetsGor[i];
                        }
                }
        }
        private void showAvailableMoveToKing(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);
                if (y - square_size >= 0) {
                        if (board[selectedFigure.getRow() - 1][selectedFigure.getCol()] == null ||
                                board[selectedFigure.getRow() - 1][selectedFigure.getCol()].getColor() != selectedFigure.getColor()) {
                                g2.drawRect(x, y - square_size, square_size, square_size);
                                if (x - square_size >= 0) {
                                        if (board[selectedFigure.getRow() - 1][selectedFigure.getCol() - 1] == null ||
                                                board[selectedFigure.getRow() - 1][selectedFigure.getCol() - 1].getColor() != selectedFigure.getColor()) {
                                                g2.drawRect(x - square_size, y - square_size, square_size, square_size);
                                        }
                                }
                                if (x + square_size < ROW_COUNT * square_size) {
                                        if (board[selectedFigure.getRow() - 1][selectedFigure.getCol() + 1] == null ||
                                                board[selectedFigure.getRow() - 1][selectedFigure.getCol() + 1].getColor() != selectedFigure.getColor()) {
                                                g2.drawRect(x + square_size, y - square_size, square_size, square_size);
                                        }
                                }
                        }
                }

                if (y + square_size < ROW_COUNT * square_size) {
                        if (board[selectedFigure.getRow() + 1][selectedFigure.getCol()] == null ||
                                board[selectedFigure.getRow() + 1][selectedFigure.getCol()].getColor() != selectedFigure.getColor()) {
                                g2.drawRect(x, y + square_size, square_size, square_size);
                                if (x - square_size >= 0) {
                                        if (board[selectedFigure.getRow() + 1][selectedFigure.getCol() - 1] == null ||
                                                board[selectedFigure.getRow() + 1][selectedFigure.getCol() - 1].getColor() != selectedFigure.getColor()) {
                                                g2.drawRect(x - square_size, y + square_size, square_size, square_size);
                                        }
                                }
                                if (x + square_size < ROW_COUNT * square_size) {
                                        if (board[selectedFigure.getRow() + 1][selectedFigure.getCol() + 1] == null ||
                                                board[selectedFigure.getRow() + 1][selectedFigure.getCol() + 1].getColor() != selectedFigure.getColor()) {
                                                g2.drawRect(x + square_size, y + square_size, square_size, square_size);
                                        }
                                }
                        }
                }

                if (x - square_size >= 0) {
                        if (board[selectedFigure.getRow()][selectedFigure.getCol() - 1] == null ||
                                board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getColor() != selectedFigure.getColor()) {
                                g2.drawRect(x - square_size, y, square_size, square_size);
                        }
                }
                if (x + square_size < ROW_COUNT * square_size) {
                        if (board[selectedFigure.getRow()][selectedFigure.getCol() + 1] == null ||
                                board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getColor() != selectedFigure.getColor()) {
                                g2.drawRect(x + square_size, y, square_size, square_size);
                        }
                }
                if(selectedFigure.getCountOfMove() == 0) {
                        if (board[selectedFigure.getRow()][selectedFigure.getCol() + 1] == null && board[selectedFigure.getRow()][selectedFigure.getCol() + 2] == null &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() + 3] instanceof Rook &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() + 3].getCountOfMove() == 0) {
                                g2.setColor(Color.GREEN);
                                g2.drawRect(x + 2 * square_size, y, square_size, square_size);
                        }
                        if (board[selectedFigure.getRow()][selectedFigure.getCol() - 1] == null && board[selectedFigure.getRow()][selectedFigure.getCol() - 2] == null &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() - 3] == null &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() - 4] instanceof Rook &&
                                board[selectedFigure.getRow()][selectedFigure.getCol() - 4].getCountOfMove() == 0) {
                                g2.setColor(Color.GREEN);
                                g2.drawRect(x - 2 * square_size, y, square_size, square_size);
                        }
                }
        }
        private void showAvailableMoveToBishop(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);

                // Проверяем возможные ходы по диагоналям
                int[] rowOffsets = {-1, -1, 1, 1};
                int[] colOffsets = {1, -1, 1, -1};
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];

                        // Пока можем продолжать двигаться по диагонали
                        while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                int newX = newCol * square_size;
                                int newY = newRow * square_size;

                                if(board[newRow][newCol] != null && !board[newRow][newCol].getColor().equals(selectedFigure.getColor())) {
                                    g2.setColor(Color.RED);
                                    g2.drawRect(newX, newY, square_size, square_size);
                                    g2.setColor(Color.BLUE);
                                }
                                // Если на пути по диагонали есть фигура, то останавливаемся
                                if (board[newRow][newCol] != null) {
                                        break;
                                }

                                g2.drawRect(newX, newY, square_size, square_size);

                                // Перемещаемся к следующей клетке по диагонали
                                newRow += rowOffsets[i];
                                newCol += colOffsets[i];
                        }
                }
        }

        private void showAvailableMoveToKnight(Graphics2D g2) {
                int[] rowOffsets = {-2, -1, 1, 2, 2, 1, -1, -2};
                int[] colOffsets = {1, 2, 2, 1, -1, -2, -2, -1};

                g2.setColor(Color.BLUE);
                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];
                        if (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                if (board[newRow][newCol] == null) {
                                        // клетка пустая
                                        int x = newCol * square_size;
                                        int y = newRow * square_size;
                                        g2.drawRect(x, y, square_size, square_size);
                                } else if (board[newRow][newCol].getColor() != selectedFigure.getColor()) {
                                        // на клетке стоит конь противника
                                        int x = newCol * square_size;
                                        int y = newRow * square_size;
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x, y, square_size, square_size);
                                        g2.setColor(Color.BLUE);
                                }
                        }
                }
        }


        private void showAvailableMoveToPawn(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);
                if (selectedFigure.getColor().equals(Color.WHITE)) {
                        // Проверяем возможный ход на одну клетку вверх
                        if (selectedFigure.getRow() > 0 && board[selectedFigure.getRow() - 1][selectedFigure.getCol()] == null) {
                                g2.drawRect(x, y - square_size, square_size, square_size);
                        }

                        // Проверяем возможный ход на две клетки вверх из начальной позиции
                        if (selectedFigure.getRow() == 6) {
                                if (board[selectedFigure.getRow() - 1][selectedFigure.getCol()] == null &&
                                        board[selectedFigure.getRow() - 2][selectedFigure.getCol()] == null) {
                                        g2.drawRect(x, y - square_size, square_size, square_size);
                                        g2.drawRect(x, y - 2 * square_size, square_size, square_size);
                                }
                        }
                        if (selectedFigure.getRow() > 0 && selectedFigure.getCol() > 0 &&
                                board[selectedFigure.getRow() - 1][selectedFigure.getCol() - 1] != null &&
                                !selectedFigure.getColor().equals(board[selectedFigure.getRow() - 1][selectedFigure.getCol() - 1].getColor())) {
                                g2.setColor(Color.RED);
                                g2.drawRect(x - square_size, y - square_size, square_size, square_size);
                        }
                        if (selectedFigure.getRow() > 0 && selectedFigure.getCol() < 7 &&
                                board[selectedFigure.getRow() - 1][selectedFigure.getCol() + 1] != null &&
                                !selectedFigure.getColor().equals(board[selectedFigure.getRow() - 1][selectedFigure.getCol() + 1].getColor())) {
                                g2.setColor(Color.RED);
                                g2.drawRect(x + square_size, y - square_size, square_size, square_size);
                        }
                        if(selectedFigure.getCol() != 7) {
                                if (board[selectedFigure.getRow()][selectedFigure.getCol() + 1] != null &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() + 1] instanceof Pawns &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getCountOfMove() == 1 &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getColor() != selectedFigure.getColor()) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x + square_size, y, square_size, square_size);
                                        g2.setColor(Color.GREEN);
                                        g2.drawRect(x + square_size, y - square_size, square_size, square_size);
                                }
                        }
                        if(selectedFigure.getCol() != 0) {
                                if (board[selectedFigure.getRow()][selectedFigure.getCol() - 1] != null &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() - 1] instanceof Pawns &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getCountOfMove() == 1 &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getColor() != selectedFigure.getColor()) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x - square_size, y, square_size, square_size);
                                        g2.setColor(Color.GREEN);
                                        g2.drawRect(x - square_size, y - square_size, square_size, square_size);
                                }
                        }
                } else if (selectedFigure.getColor().equals(Color.BLACK)) {
                        // Проверяем возможный ход на одну клетку вниз
                        if (selectedFigure.getRow() < ROW_COUNT - 1 && board[selectedFigure.getRow() + 1][selectedFigure.getCol()] == null) {
                                g2.drawRect(x, y + square_size, square_size, square_size);
                        }

                        // Проверяем возможный ход на две клетки вниз из начальной позиции
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

                        // Проверяем возможный ход по диагонали вправо-вверх, если там есть фигура противоположного цвета
                        if (selectedFigure.getRow() < 7 && selectedFigure.getCol() < 7 &&
                                board[selectedFigure.getRow() + 1][selectedFigure.getCol() + 1] != null &&
                                !selectedFigure.getColor().equals(board[selectedFigure.getRow() + 1][selectedFigure.getCol() + 1].getColor())) {
                                g2.setColor(Color.RED);
                                g2.drawRect(x + square_size, y + square_size, square_size, square_size);
                        }
                        if(selectedFigure.getCol() != 7) {
                                if (board[selectedFigure.getRow()][selectedFigure.getCol() + 1] != null &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() + 1] instanceof Pawns &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getCountOfMove() == 1 &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() + 1].getColor() != selectedFigure.getColor()) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x + square_size, y, square_size, square_size);
                                        g2.setColor(Color.GREEN);
                                        g2.drawRect(x + square_size, y + square_size, square_size, square_size);
                                }
                        }
                        if(selectedFigure.getCol() != 0) {
                                if (board[selectedFigure.getRow()][selectedFigure.getCol() - 1] != null &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() - 1] instanceof Pawns &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getCountOfMove() == 1 &&
                                        board[selectedFigure.getRow()][selectedFigure.getCol() - 1].getColor() != selectedFigure.getColor()) {
                                        g2.setColor(Color.RED);
                                        g2.drawRect(x - square_size, y, square_size, square_size);
                                        g2.setColor(Color.GREEN);
                                        g2.drawRect(x - square_size, y + square_size, square_size, square_size);
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

        public int getSquare_size() {
                return square_size;
        }

        public Figure[][] getBoard() {
                return board;
        }

        public Figure getSelectedFigure() {
                return selectedFigure;
        }

        public int getSelectedFigureX() {
                return selectedFigureX;
        }

        public int getSelectedFigureY() {
                return selectedFigureY;
        }

        public int getShiftX() {
                return shiftX;
        }

        public int getShiftY() {
                return shiftY;
        }

        public void setSelectedFigure(Figure selectedFigure) {
                this.selectedFigure = selectedFigure;
        }

        public void setSelectedFigureX(int selectedFigureX) {
                this.selectedFigureX = selectedFigureX;
        }

        public void setSelectedFigureY(int selectedFigureY) {
                this.selectedFigureY = selectedFigureY;
        }

        public void setStartSelectedFigureX(int startSelectedFigureX) {
                this.startSelectedFigureX = startSelectedFigureX;
        }

        public void setStartSelectedFigureY(int startSelectedFigureY) {
                this.startSelectedFigureY = startSelectedFigureY;
        }

        public void setLastSelectedFigureX(int lastSelectedFigureX) {
                this.lastSelectedFigureX = lastSelectedFigureX;
        }

        public void setLastSelectedFigureY(int lastSelectedFigureY) {
                this.lastSelectedFigureY = lastSelectedFigureY;
        }

        public void addCountOfBeingSelected() {
                countOfBeingSelected++;
        }

        public void restart() {
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

                BoardController.currentPlayer = Color.WHITE;
                repaint();
                reset();
        }

        private void reset() {
                boardController.wTimer.stop();
                boardController.wTimeCounter = 0;
                boardController.bTimer.stop();
                boardController.bTimeCounter = 0;
                boardController.updateTimeLabel();
                boardController.wTimer.start();
        }

        public void animate(int endX, int endY) {
                final double startX = selectedFigureX;
                final double startY = selectedFigureY;
                final double distanceX = Math.abs(endX - startX);
                final double distanceY = Math.abs(endY - startY);
                final double totalDistance = Math.max(distanceX, distanceY);
                final double stepSize = totalDistance / 10;
                final double xStep = distanceX / totalDistance * stepSize;
                final double yStep = distanceY / totalDistance * stepSize;
                final int directionX = endX > startX ? 1 : -1;
                final int directionY = endY > startY ? 1 : -1;
                boardController.setAnimationIsOn(true);
                doAnimate = true;
                Timer timer = new Timer(DELAY, new ActionListener() {
                        double x = startX;
                        double y = startY;
                        double distanceCovered = 0;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                x += xStep * directionX;
                                y += yStep * directionY;
                                selectedFigure.setX(x);
                                selectedFigure.setY(y);
                                repaint();
                                distanceCovered += stepSize;
                                if (distanceCovered >= totalDistance) {
                                        doAnimate = false;
                                        selectedFigure.setCol(endX);
                                        selectedFigure.setRow(endY);
                                        selectedFigure = null;
                                        boardController.setAnimationIsOn(false);
                                        ((Timer)e.getSource()).stop();
                                }
                        }
                });
                timer.start();
        }

        public void createPNGImage(String fileName) {
                // Создаем изображение с размерами компонента
                BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

                // Рисуем компонент на изображении
                Graphics2D g2 = image.createGraphics();
                paintComponent(g2);
                //g2.dispose();

                // Сохраняем изображение в файл
                try {
                        ImageIO.write(image, "png", new File(fileName));
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public JFreeChart createGraf() {
                // Создаем объект NumberAxis для оси x и настраиваем его
                NumberAxis xAxis = new NumberAxis("Count of moves");
                xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                NumberAxis yAxis = new NumberAxis("Time in seconds");
                yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

// Создаем график с настроенным NumberAxis
                JFreeChart chart = ChartFactory.createXYLineChart("Average time of move", null, null, createDataset());
                XYPlot plot = chart.getXYPlot();
                plot.setDomainAxis(xAxis);
                plot.setRangeAxis(yAxis);

                return chart;
        }

        private XYDataset createDataset() {
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

        public void createPNGImageOfGraf(String fileName) {
                JFreeChart chart = createGraf();
                int width = 640; // ширина изображения в пикселях
                int height = 480; // высота изображения в пикселях

                // Создаем изображение с указанными размерами и типом
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                // Рисуем график на изображении
                Graphics2D g2 = image.createGraphics();
                chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
                g2.dispose();

                // Сохраняем изображение в файл
                try {
                        ImageIO.write(image, "png", new File(fileName));
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
