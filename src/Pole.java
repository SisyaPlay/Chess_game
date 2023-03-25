import Figures.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Pole extends JPanel {
	private final int ROW_COUNT = 8;
	private double x1, y1;
	private double square_size;
	private int shiftX;


	private Cell cell = new Cell(0,0,0,0);

	private double[] chessX = cell.chessBoardX;
	private double[] chessY = cell.chessBoardY;
	private Point mousePressedLocation;
	private Point mouseReleasedLocation;

	private Figure[] figures;

	public Pole() {
		this.setLayout(null);
		cellResize();

		figureResize();
		figures = new Figure[]{
				new Pawns((int)chessX[0], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[1], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[2], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[3], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[4], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[5], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[6], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[7], (int)chessY[1], ESide.BLACK, square_size),
				new Pawns((int)chessX[0], (int)chessY[6], ESide.WHITE, square_size),
				new Pawns((int)chessX[1], (int)chessY[6], ESide.WHITE, square_size),
				new Pawns((int)chessX[2], (int)chessY[6], ESide.WHITE, square_size),
				new Pawns((int)chessX[3], (int)chessY[6], ESide.WHITE, square_size),
				new Pawns((int)chessX[4], (int)chessY[6], ESide.WHITE, square_size),
				new Pawns((int)chessX[5], (int)chessY[6], ESide.WHITE, square_size),
				new Pawns((int)chessX[6], (int)chessY[6], ESide.WHITE, square_size),
				new Pawns((int)chessX[7], (int)chessY[6], ESide.WHITE, square_size),

				new Knight((int)chessX[1], (int)chessY[0], ESide.BLACK, square_size),
				new Knight((int)chessX[6], (int)chessY[0], ESide.BLACK, square_size),
				new Knight((int)chessX[1], (int)chessY[7], ESide.WHITE, square_size),
				new Knight((int)chessX[6], (int)chessY[7], ESide.WHITE, square_size),

				new Rook((int)chessX[0], (int)chessY[0], ESide.BLACK, square_size),
				new Rook((int)chessX[7], (int)chessY[0], ESide.BLACK, square_size),

				new Rook((int)chessX[0], (int)chessY[7], ESide.WHITE, square_size),
				new Rook((int)chessX[7], (int)chessY[7], ESide.WHITE, square_size),

				new Queen((int)chessX[3], (int)chessY[0], ESide.BLACK, square_size),
				new Queen((int)chessX[3], (int)chessY[7], ESide.WHITE, square_size),

				new Bishop((int)chessX[2], (int)chessY[0], ESide.BLACK, square_size),
				new Bishop((int)chessX[5], (int)chessY[0], ESide.BLACK, square_size),

				new Bishop((int)chessX[2], (int)chessY[7], ESide.WHITE, square_size),
				new Bishop((int)chessX[5], (int)chessY[7], ESide.WHITE, square_size),

				new King((int)chessX[4], (int)chessY[0], ESide.BLACK, square_size),
				new King((int)chessX[4], (int)chessY[7], ESide.WHITE, square_size)
		};

		for(Figure figure: figures) {
			this.add(figure);
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				for (Figure fgr : figures){
					if (fgr.isFigureHit(e.getPoint())) {
						System.out.println("nazhal");

					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}
		});
	}

	private void figureResize() {
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				square_size = calculateSize();

				for(Figure figure: figures) {
					figure.setSize(square_size);
					figure.validate();
					figure.repaint();
				}
			}
		});
	}


	private void cellResize() {
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				square_size = calculateSize();
				shiftX = (getWidth() - (int)(ROW_COUNT * square_size)) / 2;
				cell = new Cell(x1, y1, square_size, shiftX);
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

		chessX = cell.getChessBoardX();
		chessY = cell.getChessBoardY();

	}


	public double calculateSize() {
		square_size = this.getHeight() / 8;
		return square_size;
	}
}

/*

figures = new Figures[] {
		new Pawns((int)chessX[0], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[1], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[2], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[3], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[4], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[5], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[6], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[7], (int)chessY[1], ESide.BLACK, square_size),
		new Pawns((int)chessX[0], (int)chessY[6], ESide.WHITE, square_size),
		new Pawns((int)chessX[1], (int)chessY[6], ESide.WHITE, square_size),
		new Pawns((int)chessX[2], (int)chessY[6], ESide.WHITE, square_size),
		new Pawns((int)chessX[3], (int)chessY[6], ESide.WHITE, square_size),
		new Pawns((int)chessX[4], (int)chessY[6], ESide.WHITE, square_size),
		new Pawns((int)chessX[5], (int)chessY[6], ESide.WHITE, square_size),
		new Pawns((int)chessX[6], (int)chessY[6], ESide.WHITE, square_size),
		new Pawns((int)chessX[7], (int)chessY[6], ESide.WHITE, square_size),

		new Knight((int)chessX[1], (int)chessY[0], ESide.BLACK, square_size),
		new Knight((int)chessX[6], (int)chessY[0], ESide.BLACK, square_size),
		new Knight((int)chessX[1], (int)chessY[7], ESide.WHITE, square_size),
		new Knight((int)chessX[6], (int)chessY[7], ESide.WHITE, square_size),

		new Rook((int)chessX[0], (int)chessY[0], ESide.BLACK, square_size),
		new Rook((int)chessX[7], (int)chessY[0], ESide.BLACK, square_size),

		new Rook((int)chessX[0], (int)chessY[7], ESide.WHITE, square_size),
		new Rook((int)chessX[7], (int)chessY[7], ESide.WHITE, square_size),

		new Queen((int)chessX[3], (int)chessY[0], ESide.BLACK, square_size),
		new Queen((int)chessX[3], (int)chessY[7], ESide.WHITE, square_size),

		new Bishop((int)chessX[2], (int)chessY[0], ESide.BLACK, square_size),
		new Bishop((int)chessX[5], (int)chessY[0], ESide.BLACK, square_size),

		new Bishop((int)chessX[2], (int)chessY[7], ESide.WHITE, square_size),
		new Bishop((int)chessX[5], (int)chessY[7], ESide.WHITE, square_size),

		new King((int)chessX[4], (int)chessY[0], ESide.BLACK, square_size),
		new King((int)chessX[4], (int)chessY[7], ESide.WHITE, square_size)
};

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
