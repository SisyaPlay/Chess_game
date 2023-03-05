import Figures.EFigure;
import Figures.ESide;
import Figures.Knight;
import Figures.Pawns;

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
	//private final SVGI\[][] images = new SVGIcon[2][6];
	
	public Pole(JFrame frame) {
		this.frame = frame;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		calculateSize();
		
		shiftX = (frame.getWidth() - (ROW_COUNT * square_size)) / 2;
		shiftY = (frame.getHeight() - (ROW_COUNT * square_size)) / 2;
		
		drawSquere(g);
		for (double i = 0; i < square_size * 8; i += square_size) {
			Pawns pawnsB = new Pawns((int)((shiftX) + i), (int)shiftY, ESide.BLACK, square_size);
			pawnsB.paint(g);
		}

		Knight knight = new Knight((int)(shiftX), (int)shiftY, ESide.BLACK, square_size);
		knight.paint(g);
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
}
