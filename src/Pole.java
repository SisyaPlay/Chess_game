import Figures.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;


//import org.apache.batik.swing.svg.SVGIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pole extends JPanel {

	private final long serialVersionUID = 1L;
	private final int ROW_COUNT = 8;
	private double square_size;
	private double shiftX;
	private double shiftY;
	private JFrame frame;

	private JPanel panel;

	private Figure[] figures;

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
		Graphics2D g2 = (Graphics2D) g;

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


	public void drawFigures(Graphics g) {
		figures = new Figure[]{new Pawns((int)((shiftX)), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Pawns((int)((shiftX) + square_size), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Pawns((int)((shiftX) + square_size * 2), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Pawns((int)((shiftX) + square_size * 3), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Pawns((int)((shiftX) + square_size * 4), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Pawns((int)((shiftX) + square_size * 5), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Pawns((int)((shiftX) + square_size * 6), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Pawns((int)((shiftX) + square_size * 7), (int)(shiftY + square_size), ESide.BLACK, square_size),
							   new Knight((int) (shiftX + square_size), (int) shiftY, ESide.BLACK, square_size),
							   new Knight((int) (shiftX + square_size * 6), (int) shiftY, ESide.BLACK, square_size),
				               new Pawns((int)((shiftX)), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Pawns((int)((shiftX) + square_size), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Pawns((int)((shiftX) + square_size * 2), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Pawns((int)((shiftX) + square_size * 3), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Pawns((int)((shiftX) + square_size * 4), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Pawns((int)((shiftX) + square_size * 5), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Pawns((int)((shiftX) + square_size * 6), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Pawns((int)((shiftX) + square_size * 7), (int)(shiftY + square_size * 6), ESide.WHITE, square_size),
							   new Knight((int) (shiftX + square_size), (int)(shiftY + square_size * 7), ESide.WHITE, square_size),
							   new Knight((int) (shiftX + square_size * 6), (int)(shiftY + square_size * 7), ESide.WHITE, square_size),
							   new Rook((int) (shiftX), (int)(shiftY), ESide.BLACK, square_size),
					           new Rook((int) (shiftX + square_size * 7), (int)(shiftY), ESide.BLACK, square_size),
							   new Rook((int) (shiftX), (int)(shiftY + square_size * 7), ESide.WHITE, square_size),
							   new Rook((int) (shiftX + square_size * 7), (int)(shiftY + square_size * 7), ESide.WHITE, square_size),
							   new Queen((int) (shiftX + square_size * 3), (int)(shiftY), ESide.BLACK, square_size),
							   new Queen((int) (shiftX + square_size * 3), (int)(shiftY + square_size * 7), ESide.WHITE, square_size),
							   new Bishop((int) (shiftX + square_size * 2), (int)(shiftY), ESide.BLACK, square_size),
							   new Bishop((int) (shiftX + square_size * 5), (int)(shiftY), ESide.BLACK, square_size),
					  		   new Bishop((int) (shiftX + square_size * 2), (int)(shiftY + square_size * 7), ESide.WHITE, square_size),
							   new Bishop((int) (shiftX + square_size * 5), (int)(shiftY + square_size * 7), ESide.WHITE, square_size),
							   new King((int) (shiftX + square_size * 4), (int)(shiftY), ESide.BLACK, square_size),
							   new King((int) (shiftX + square_size * 4), (int)(shiftY + square_size * 7), ESide.WHITE, square_size)
		};
		for(Figure f: figures) {
			f.paint(g);
		}
	}
}