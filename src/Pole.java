import Figures.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;


//import org.apache.batik.swing.svg.SVGIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pole extends JPanel{
	
	private final long serialVersionUID = 1L;
	private final int ROW_COUNT = 8;
	private double square_size;
	private double shiftX;
	private double shiftY;
	private JFrame frame;
	
	public Pole(JFrame frame) {
		this.frame = frame;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		shiftX = (frame.getWidth() - (ROW_COUNT * square_size)) / 2;
		shiftY = (frame.getHeight() - (ROW_COUNT * square_size)) / 2;
		
		calculateSize();
		
		drawSquere(g);

		//drawFigures(g);
	}
	
	public double calculateSize() {
		square_size = frame.getHeight() / 9;
		return square_size;
	}
	
	public void drawSquere(Graphics g) {
		double row, col, x1, y1, x2, y2;
		Graphics2D g2 = (Graphics2D)g;

		for (row = 0; row < ROW_COUNT; row++) {
			for (col = 0; col < ROW_COUNT; col++) {
				x1 = col * square_size;
				y1 = row * square_size;
				x2 = x1 + square_size;
				y2 = y1 + square_size;
				if ((row + col) % 2 != 0) {
                	Cell cell = new Cell(x1 + shiftX, y1 + shiftY, x2 + shiftX, y2 + shiftY, square_size, Color.lightGray);
					cell.paintComponent(g2);
                	
                }
			}
		}

		for (row = 0; row < ROW_COUNT; row++) {
			for (col = 0; col < ROW_COUNT; col++) {
				x1 = col * square_size;
				y1 = row * square_size;
				x2 = x1 + square_size;
				y2 = y1 + square_size;
				if ((row + col) % 2 == 0) {
					Cell cell = new Cell(x1 + shiftX, y1 + shiftY, x2 + shiftX, y2 + shiftY, square_size, Color.WHITE);
					cell.paintComponent(g2);
				}
			}
		}
	}

	/*
	public void drawFigures(Graphics g) {
		for (double i = 0; i < square_size * 8; i += square_size) {
			Pawns pawns = new Pawns((int)((shiftX) + i), (int)(shiftY + square_size), ESide.BLACK, square_size);
			pawns.paint(g);
		}

		Knight knightb1 = new Knight((int) (shiftX + square_size), (int) shiftY, ESide.BLACK, square_size);
		knightb1.paint(g);

		Knight knightb2 = new Knight((int) (shiftX + square_size * 6), (int) shiftY, ESide.BLACK, square_size);
		knightb2.paint(g);

		for (double i = 0; i < square_size * 8; i += square_size) {
			Pawns pawns = new Pawns((int)((shiftX) + i), (int)(shiftY + square_size * 6), ESide.WHITE, square_size);
			pawns.paint(g);
		}

		Knight knightw1 = new Knight((int) (shiftX + square_size), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		knightw1.paint(g);

		Knight knightw2 = new Knight((int) (shiftX + square_size * 6), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		knightw2.paint(g);

		Rook rookb1 = new Rook((int) (shiftX), (int)(shiftY), ESide.BLACK, square_size);
		rookb1.paint(g);
		Rook rookb2 = new Rook((int) (shiftX + square_size * 7), (int)(shiftY), ESide.BLACK, square_size);
		rookb2.paint(g);

		Rook rookw1 = new Rook((int) (shiftX), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		rookw1.paint(g);
		Rook rookw2 = new Rook((int) (shiftX + square_size * 7), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		rookw2.paint(g);

		Queen queenb = new Queen((int) (shiftX + square_size * 3), (int)(shiftY), ESide.BLACK, square_size);
		queenb.paint(g);
		Queen queenw = new Queen((int) (shiftX + square_size * 3), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		queenw.paint(g);

		Bishop bishopb1 = new Bishop((int) (shiftX + square_size * 2), (int)(shiftY), ESide.BLACK, square_size);
		bishopb1.paint(g);
		Bishop bishopb2 = new Bishop((int) (shiftX + square_size * 5), (int)(shiftY), ESide.BLACK, square_size);
		bishopb2.paint(g);

		Bishop bishopw1 = new Bishop((int) (shiftX + square_size * 2), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		bishopw1.paint(g);
		Bishop bishopw2 = new Bishop((int) (shiftX + square_size * 5), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		bishopw2.paint(g);

		King kingb = new King((int) (shiftX + square_size * 4), (int)(shiftY), ESide.BLACK, square_size);
		kingb.paint(g);
		King kingw = new King((int) (shiftX + square_size * 4), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		kingw.paint(g);
	}
	*/
}
