import Figures.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class Pole extends JPanel {
	private final double ROW_COUNT = 8;
	private double square_size;
	private double shiftX;
	private double shiftY;
	private JFrame frame;


	public Pole(JFrame frame) {
		this.frame = frame;
		frame.setSize(new Dimension(1280, 720));
		drawFigures();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		square_size = calculateSize();

		shiftX = (frame.getWidth() - (ROW_COUNT * square_size)) / 2;
		shiftY = (frame.getHeight() - (ROW_COUNT * square_size)) / 2;

		drawSquere(g);
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

	public void drawFigures() {
		square_size = calculateSize();

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

		Knight bknight1 = new Knight(ESide.BLACK, square_size);
		Knight bknight2 = new Knight(ESide.BLACK, square_size);

		Knight wknight1 = new Knight(ESide.WHITE, square_size);
		Knight wknight2 = new Knight(ESide.WHITE, square_size);

	    Rook brook1 = new Rook(ESide.BLACK, square_size);
		Rook brook2 = new Rook(ESide.BLACK, square_size);

		Rook wrook1 = new Rook(ESide.WHITE, square_size);
		Rook wrook2 = new Rook(ESide.WHITE, square_size);

	    Queen bqueen = new Queen(ESide.BLACK, square_size);
		Queen wqueen = new Queen(ESide.WHITE, square_size);

	    Bishop bbishop1 = new Bishop(ESide.BLACK, square_size);
		Bishop bbishop2 = new Bishop(ESide.BLACK, square_size);

		Bishop wbishop1 = new Bishop(ESide.WHITE, square_size);
		Bishop wbishop2 = new Bishop(ESide.WHITE, square_size);

	    King bking = new King(ESide.BLACK, square_size);
		King wking = new King(ESide.WHITE, square_size);

		this.add(bpawn1);
		this.add(bpawn2);
		this.add(bpawn3);
		this.add(bpawn4);
		this.add(bpawn5);
		this.add(bpawn6);
		this.add(bpawn7);
		this.add(bpawn8);

		this.add(wpawn1);
		this.add(wpawn2);
		this.add(wpawn3);
		this.add(wpawn4);
		this.add(wpawn5);
		this.add(wpawn6);
		this.add(wpawn7);
		this.add(wpawn8);

		this.add(bknight1);
		this.add(bknight2);

		this.add(wknight1);
		this.add(wknight2);

		this.add(brook1);
		this.add(brook2);

		this.add(wrook1);
		this.add(wrook2);

		this.add(bqueen);
		this.add(wqueen);

		this.add(bbishop1);
		this.add(bbishop2);

		this.add(wbishop1);
		this.add(wbishop2);

		this.add(bking);
		this.add(wking);



	}
}
