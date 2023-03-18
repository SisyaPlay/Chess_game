import Figures.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;


//import org.apache.batik.swing.svg.SVGIcon;
import javax.swing.*;

public class Pole extends JPanel {
	private final double ROW_COUNT = 8;
	private double square_size;
	private double shiftX;
	private double shiftY;
	private JFrame frame;


	public Pole(JFrame frame) {
		this.frame = frame;

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		calculateSize();

		shiftX = (frame.getWidth() - (ROW_COUNT * square_size)) / 2;
		shiftY = (frame.getHeight() - (ROW_COUNT * square_size)) / 2;

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
		//JPanel panel = new JPanel(new FlowLayout());
		/*
		Pawns bpawn1 = new Pawns(ESide.BLACK, square_size);
		Pawns bpawn2 = new Pawns(ESide.BLACK, square_size);
		Pawns bpawn3 = new Pawns(ESide.BLACK, square_size);
		Pawns bpawn4 = new Pawns(ESide.BLACK, square_size);
		Pawns bpawn5 = new Pawns(ESide.BLACK, square_size);
		Pawns bpawn6 = new Pawns(ESide.BLACK, square_size);
		Pawns bpawn7 = new Pawns(ESide.BLACK, square_size);
		Pawns bpawn8 = new Pawns(ESide.BLACK, square_size);
		Pawns wpawn1 = new Pawns(ESide.WHITE, square_size);
		Pawns wpawn2 = new Pawns(ESide.WHITE, square_size);
		Pawns wpawn3 = new Pawns(ESide.WHITE, square_size);
		Pawns wpawn4 = new Pawns(ESide.WHITE, square_size);
		Pawns wpawn5 = new Pawns(ESide.WHITE, square_size);
		Pawns wpawn6 = new Pawns(ESide.WHITE, square_size);
		Pawns wpawn7 = new Pawns(ESide.WHITE, square_size);
		Pawns wpawn8 = new Pawns(ESide.WHITE, square_size);
		Knight bknight1 = new Knight((int) (shiftX + square_size), (int) shiftY, ESide.BLACK, square_size);
		Knight bknight2 = new Knight((int) (shiftX + square_size * 6), (int) shiftY, ESide.BLACK, square_size);
		Knight wknight1 = new Knight((int) (shiftX + square_size), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		Knight wknight2 = new Knight((int) (shiftX + square_size * 6), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
	    Rook brook1 = new Rook((int) (shiftX), (int)(shiftY), ESide.BLACK, square_size);
		Rook brook2 = new Rook((int) (shiftX + square_size * 7), (int)(shiftY), ESide.BLACK, square_size);
		Rook wrook1 = new Rook((int) (shiftX), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		Rook wrook2 = new Rook((int) (shiftX + square_size * 7), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
	    Queen bqueen = new Queen((int) (shiftX + square_size * 3), (int)(shiftY), ESide.BLACK, square_size);
		Queen wqueen = new Queen((int) (shiftX + square_size * 3), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
	    Bishop bbishop1 = new Bishop((int) (shiftX + square_size * 2), (int)(shiftY), ESide.BLACK, square_size);
		Bishop bbishop2 = new Bishop((int) (shiftX + square_size * 5), (int)(shiftY), ESide.BLACK, square_size);
		Bishop wbishop1 = new Bishop((int) (shiftX + square_size * 2), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
		Bishop wbishop2 = new Bishop((int) (shiftX + square_size * 5), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);
	    King bking = new King((int) (shiftX + square_size * 4), (int)(shiftY), ESide.BLACK, square_size);
		King wking = new King((int) (shiftX + square_size * 4), (int)(shiftY + square_size * 7), ESide.WHITE, square_size);

		 */

		/*
		bpawn1.paint(g);
		bpawn2.paint(g);
		bpawn3.paint(g);
		bpawn4.paint(g);
		bpawn5.paint(g);
		bpawn6.paint(g);
		bpawn7.paint(g);
		bpawn8.paint(g);
		wpawn1.paint(g);
		wpawn2.paint(g);
		wpawn3.paint(g);
		wpawn4.paint(g);
		wpawn5.paint(g);
		wpawn6.paint(g);
		wpawn7.paint(g);
		wpawn8.paint(g);
		bknight1.paint(g);
		bknight2.paint(g);
		wknight1.paint(g);
		wknight2.paint(g);
		brook1.paint(g);
		brook2.paint(g);
		wrook1.paint(g);
		wrook2.paint(g);
		bqueen.paint(g);
		wqueen.paint(g);
		bbishop1.paint(g);
		bbishop2.paint(g);
		wbishop1.paint(g);
		wbishop2.paint(g);
		bking.paint(g);
		wking.paint(g);

		bpawn1.setOpaque(false);
		this.add(bpawn1);
		*/

	}
}
