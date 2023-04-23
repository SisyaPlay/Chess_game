import Figures.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Trida View.ChessBoard dedi od JPanelu a implementuje MouseListener, MouseMotionListener.
 * Zde vykresli sachovnice 8x8 s bilimi a zasrafovanymi sedymi pruhy ctvercu.
 * Jeste tady implementovano drag and drop.
 */

public class ChessBoard extends JPanel implements MouseListener, MouseMotionListener {

        private static final int ROW_COUNT = 8; // Pocet ctvercu
        private int square_size; // Velikost ctverce

        private Figure[][] board = new Figure[8][8]; // Pole, kde jsou figury
        private Figure selectedFigure; // Vybrana figura
        private int selectedFigureX; // Pozice vybranou figury podle osy X
        private int selectedFigureY = 0; // Pozice vybranou figury podle osy Y

        private int shiftX; // Posun
        private int shiftY;

        /**
         * Konstruktor sachonvnice.
         * Zde je ComponentListener, kdery zmemi rozmer figur a sachovnice podle velikosti okna.
         * Nastavi preferovanou velikost sachovnice a inicializuje figury do dvojite pole board.
         * A zavola posluchac mysi.
         */
        public ChessBoard() {
                addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                                super.componentResized(e);
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

                                setPreferredSize(new Dimension(square_size * ROW_COUNT, square_size * ROW_COUNT));
                                revalidate();
                        }
                });

                setPreferredSize(new Dimension(square_size * ROW_COUNT, square_size * ROW_COUNT));
                addMouseListener(this);
                addMouseMotionListener(this);

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


                //shiftX = (getWidth() - (ROW_COUNT * square_size)) / 2;
                //System.out.println(shiftX);
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
                        if(col > ROW_COUNT - 1 || row > ROW_COUNT - 1 || col < 0 || row < 0 || x < 0 || y < 0) {
                                board[selectedFigureY][selectedFigureX] = selectedFigure;
                        }
                        else {
                                board[selectedFigure.getRow()][selectedFigure.getCol()] = null;
                                board[row][col] = selectedFigure; // Zapise do pole figuru
                                selectedFigure.setRow(row);
                                selectedFigure.setCol(col);
                        }
                        selectedFigure = null;
                        repaint();

                }
        }

        public void mouseMoved(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
}
