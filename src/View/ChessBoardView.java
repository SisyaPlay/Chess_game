package View;

import Controllers.BoardController;
import Figures.*;

import javax.swing.*;
import java.awt.*;

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
        private int selectedFigureY = 0; // Pozice vybranou figury podle osy Y
        private int shiftX; // Posun
        private int shiftY;
        private BoardController boardController;

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
                addMouseMotionListener(boardController);

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


                if(selectedFigure != null) {
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

        private void showAvailableMoveToRook(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);
                g2.drawRect(x, y, square_size, square_size);

                for (int i = 0; i < ROW_COUNT; i++) {
                        if (i != selectedFigure.getCol()) {
                                g2.drawRect(i * square_size, y, square_size, square_size);
                        }
                }

                for (int j = 0; j < ROW_COUNT; j++) {
                        if (j != selectedFigure.getRow()) {
                                g2.drawRect(x, j * square_size, square_size, square_size);
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

                for (int i = 0; i < ROW_COUNT; i++) {
                        if (i != selectedFigure.getCol()) {
                                g2.drawRect(i * square_size, y, square_size, square_size);
                        }
                }

                for (int j = 0; j < ROW_COUNT; j++) {
                        if (j != selectedFigure.getRow()) {
                                g2.drawRect(x, j * square_size, square_size, square_size);
                        }
                }

                for (int i = 0; i < rowOffsets.length; i++) {
                        int newRow = selectedFigure.getRow() + rowOffsets[i];
                        int newCol = selectedFigure.getCol() + colOffsets[i];

                        // Пока можем продолжать двигаться по диагонали
                        while (newRow >= 0 && newRow < ROW_COUNT && newCol >= 0 && newCol < ROW_COUNT) {
                                int newX = newCol * square_size;
                                int newY = newRow * square_size;

                                g2.drawRect(newX, newY, square_size, square_size);

                                // Перемещаемся к следующей клетке по диагонали
                                newRow += rowOffsets[i];
                                newCol += colOffsets[i];
                        }
                }
        }
        private void showAvailableMoveToKing(Graphics2D g2) {
                int x = selectedFigure.getCol() * square_size;
                int y = selectedFigure.getRow() * square_size;
                g2.setColor(Color.BLUE);

                // Check and draw available moves to top row
                if (y - square_size >= 0) {
                        g2.drawRect(x, y - square_size, square_size, square_size);
                        if (x - square_size >= 0) g2.drawRect(x - square_size, y - square_size, square_size, square_size);
                        if (x + square_size < ROW_COUNT * square_size) g2.drawRect(x + square_size, y - square_size, square_size, square_size);
                }

                // Check and draw available moves to bottom row
                if (y + square_size < ROW_COUNT * square_size) {
                        g2.drawRect(x, y + square_size, square_size, square_size);
                        if (x - square_size >= 0) g2.drawRect(x - square_size, y + square_size, square_size, square_size);
                        if (x + square_size < ROW_COUNT * square_size) g2.drawRect(x + square_size, y + square_size, square_size, square_size);
                }

                // Check and draw available moves to left and right columns
                if (x - square_size >= 0) g2.drawRect(x - square_size, y, square_size, square_size);
                if (x + square_size < ROW_COUNT * square_size) g2.drawRect(x + square_size, y, square_size, square_size);
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
                                if (board[newRow][newCol] == null || board[newRow][newCol].getColor() != selectedFigure.getColor()) {
                                        // клетка пустая или на ней стоит фигура противника
                                        int x = newCol * square_size;
                                        int y = newRow * square_size;
                                        g2.drawRect(x, y, square_size, square_size);
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

        }
}
