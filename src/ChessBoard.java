import Figures.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessBoard extends JPanel implements MouseListener, MouseMotionListener {

        private static final int ROW_COUNT = 8;
        private int square_size;

        private Figure[][] board = new Figure[8][8];
        private Figure selectedFigure;
        private int selectedFigureX;
        private int selectedFigureY;

        private int shiftX;


        public ChessBoard() {
                addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                                super.componentResized(e);
                                square_size = getHeight() / ROW_COUNT;
                                shiftX = (getWidth() - (ROW_COUNT * square_size)) / 2;

                                for (int row = 0; row < ROW_COUNT; row++) {
                                        for (int col = 0; col < ROW_COUNT; col++) {
                                                Figure fgr = board[row][col];
                                                if (fgr != null) {
                                                        fgr.setSize(square_size);
                                                }
                                        }
                                }

                                // Update the preferred size of the panel
                                setPreferredSize(new Dimension(square_size * ROW_COUNT, square_size * ROW_COUNT));
                                revalidate();
                        }
                });

                setPreferredSize(new Dimension(square_size * ROW_COUNT, square_size * ROW_COUNT));
                addMouseListener(this);
                addMouseMotionListener(this);

                // Initialize the board with pieces
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

        public void paintComponent(Graphics g) {
                super.paintComponent(g);


                //shiftX = (getWidth() - (ROW_COUNT * square_size)) / 2;
                //System.out.println(shiftX);
                Graphics2D g2 = (Graphics2D) g;
                g2.translate(shiftX, 0);



                // Draw the board
                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                if ((row + col) % 2 != 0) {
                                        g.setColor(Color.WHITE);
                                        g.fillRect(col * square_size, row * square_size, square_size, square_size);
                                        drawHetch(g, row, col);
                                }
                        }
                }

                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                if ((row + col) % 2 == 0) {
                                        g.setColor(Color.WHITE);
                                        g.fillRect(col * square_size, row * square_size, square_size, square_size);
                                }
                        }
                }

                // Draw the pieces
                for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                                Figure piece = board[row][col];
                                if (piece != null) {
                                        piece.paintComponent(g);
                                }
                        }
                }
        }

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

        public void mousePressed(MouseEvent e) {
                int x = e.getX() - shiftX;
                int y = e.getY();
                int row = y / square_size;
                int col = x / square_size;

                selectedFigure = board[row][col];

                if (selectedFigure != null) {
                        selectedFigureX = x - col * square_size;
                        selectedFigureY = y - row * square_size;
                }
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
                if (selectedFigure != null) {
                        int x = e.getX() - shiftX;
                        int y = e.getY();
                        int row = y / square_size;
                        int col = x / square_size;

                        board[selectedFigure.getRow()][selectedFigure.getCol()] = null;
                        board[row][col] = selectedFigure;
                        selectedFigure.setRow(row);
                        selectedFigure.setCol(col);

                        selectedFigure = null;
                        repaint();
                }
        }

        public void mouseMoved(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
}
