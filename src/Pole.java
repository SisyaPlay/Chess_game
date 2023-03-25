import Figures.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Pole extends JPanel{
	private final int ROW_COUNT = 8;
	private double x1, y1;
	private double square_size;
	private int shiftX;

	private Pawns bpawn1;

	private Cell cell;

	private double[] chessBoardX = new double[ROW_COUNT];
	private double[] chessBoardY = new double[ROW_COUNT];

	public Pole() {
		this.setSize(new Dimension(1280, 720));
		this.setPreferredSize(new Dimension(1280, 720));
		square_size = calculateSize();

		cellResize();
		cell = new Cell(x1, y1, square_size, shiftX);
		chessBoardX = cell.chessBoardX;
		chessBoardY = cell.chessBoardY;

		figureResize();
		bpawn1 = new Pawns((int)chessBoardX[0], (int)chessBoardY[0], ESide.BLACK, square_size);
	}

	private void figureResize() {
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				square_size = calculateSize();
				chessBoardX = cell.chessBoardX;
				chessBoardY = cell.chessBoardY;
				bpawn1 = new Pawns((int)chessBoardX[0], (int)chessBoardY[0], ESide.BLACK, square_size);
				bpawn1.setSize(square_size);
				bpawn1.revalidate();
				bpawn1.repaint();
			}
		});
	}

	private void cellResize() {
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				square_size = calculateSize();
				shiftX = (getWidth() - (int)(ROW_COUNT * square_size)) / 2;
				cell = new Cell(x1, y1, square_size, shiftX);
				chessBoardX = cell.chessBoardX;
				chessBoardY = cell.chessBoardY;
				cell.setSize(square_size);
				cell.revalidate();
				cell.repaint();
			}
		});
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		cell.paintComponent(g);

		bpawn1.paintComponent(g);
	}


	public double calculateSize() {
		square_size = this.getHeight() / 8;
		return square_size;
	}

}

/*
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.BLACK, square_size),
		new Pawns(ESide.WHITE, square_size),
		new Pawns(ESide.WHITE, square_size),
		new Pawns(ESide.WHITE, square_size),
		new Pawns(ESide.WHITE, square_size),
		new Pawns(ESide.WHITE, square_size),
		new Pawns(ESide.WHITE, square_size),
		new Pawns(ESide.WHITE, square_size),
		new Pawns(ESide.WHITE, square_size),

		new Knight(ESide.BLACK, square_size),
		new Knight(ESide.BLACK, square_size),
		new Knight(ESide.WHITE, square_size),
		new Knight(ESide.WHITE, square_size),

		new Rook(ESide.BLACK, square_size),
		new Rook(ESide.BLACK, square_size),

		new Rook(ESide.WHITE, square_size),
		new Rook(ESide.WHITE, square_size),

		new Queen(ESide.BLACK, square_size),
		new Queen(ESide.WHITE, square_size),

		new Bishop(ESide.BLACK, square_size),
		new Bishop(ESide.BLACK, square_size),

		new Bishop(ESide.WHITE, square_size),
		new Bishop(ESide.WHITE, square_size),

		new King(ESide.BLACK, square_size),
		new King(ESide.WHITE, square_size)


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

		 */
