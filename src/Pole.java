import Figures.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class Pole extends JPanel {
	private final int ROW_COUNT = 8;
	private double x1, y1;
	private double square_size;

	public Pole() {
		this.setSize(new Dimension(1280, 720));
		this.setPreferredSize(new Dimension(1280, 720));
		square_size = calculateSize();

		drawFigures();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		square_size = calculateSize();

		int shiftX = (this.getWidth() - (int)(ROW_COUNT * square_size)) / 2;
		//shiftY = (this.getHeight() - (ROW_COUNT * square_size)) / 2;

		Cell cell = new Cell(x1, y1, square_size, shiftX);
		cell.paintComponent(g);
	}

	public double calculateSize() {
		square_size = this.getHeight() / 8;
		return square_size;
	}

	public void drawFigures() {

		JPanel panel = new JPanel(new GridLayout(8, 8));
		panel.setOpaque(false);

		Figure bpawn1 = new Pawns(ESide.BLACK, square_size);
		Figure bpawn2 = new Pawns(ESide.BLACK, square_size);
		Figure bpawn3 = new Pawns(ESide.BLACK, square_size);
		Figure bpawn4 = new Pawns(ESide.BLACK, square_size);
		Figure bpawn5 = new Pawns(ESide.BLACK, square_size);
		Figure bpawn6 = new Pawns(ESide.BLACK, square_size);
		Figure bpawn7 = new Pawns(ESide.BLACK, square_size);
		Figure bpawn8 = new Pawns(ESide.BLACK, square_size);
		Figure wpawn1 = new Pawns(ESide.WHITE, square_size);
		Figure wpawn2 = new Pawns(ESide.WHITE, square_size);
		Figure wpawn3 = new Pawns(ESide.WHITE, square_size);
		Figure wpawn4 = new Pawns(ESide.WHITE, square_size);
		Figure wpawn5 = new Pawns(ESide.WHITE, square_size);
		Figure wpawn6 = new Pawns(ESide.WHITE, square_size);
		Figure wpawn7 = new Pawns(ESide.WHITE, square_size);
		Figure wpawn8 = new Pawns(ESide.WHITE, square_size);

		Figure bknight1 = new Knight(ESide.BLACK, square_size);
		Figure bknight2 = new Knight(ESide.BLACK, square_size);

		Figure wknight1 = new Knight(ESide.WHITE, square_size);
		Figure wknight2 = new Knight(ESide.WHITE, square_size);

		Figure brook1 = new Rook(ESide.BLACK, square_size);
		//brook1.setPosition(300, 30);
		Figure brook2 = new Rook(ESide.BLACK, square_size);

		Figure wrook1 = new Rook(ESide.WHITE, square_size);
		Figure wrook2 = new Rook(ESide.WHITE, square_size);

		Figure bqueen = new Queen(ESide.BLACK, square_size);
		Figure wqueen = new Queen(ESide.WHITE, square_size);

		Figure bbishop1 = new Bishop(ESide.BLACK, square_size);
		Figure bbishop2 = new Bishop(ESide.BLACK, square_size);

		Figure wbishop1 = new Bishop(ESide.WHITE, square_size);
		Figure wbishop2 = new Bishop(ESide.WHITE, square_size);

		Figure bking = new King(ESide.BLACK, square_size);
		Figure wking = new King(ESide.WHITE, square_size);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				square_size = calculateSize();

				Component[] components = panel.getComponents();
				for (Component component : components) {
					if (component instanceof Figure) {
						Figure fgr = (Figure) component;
						fgr.setSize(square_size);
						fgr.setPreferredSize(new Dimension((int)square_size, (int)square_size));
					}
				}

				// Repaint the panel to update the changes
				panel.revalidate();
				panel.repaint();
			}
		});

		panel.add(bpawn1);
		panel.add(bpawn2);
		panel.add(bpawn3);
		panel.add(bpawn4);
		panel.add(bpawn5);
		panel.add(bpawn6);
		panel.add(bpawn7);
		panel.add(bpawn8);

		panel.add(wpawn1);
		panel.add(wpawn2);
		panel.add(wpawn3);
		panel.add(wpawn4);
		panel.add(wpawn5);
		panel.add(wpawn6);
		panel.add(wpawn7);
		panel.add(wpawn8);

		panel.add(bknight1);
		panel.add(bknight2);

		panel.add(wknight1);
		panel.add(wknight2);

		panel.add(brook1);
		panel.add(brook2);

		panel.add(wrook1);
		panel.add(wrook2);

		panel.add(bqueen);
		panel.add(wqueen);

		panel.add(bbishop1);
		panel.add(bbishop2);

		panel.add(wbishop1);
		panel.add(wbishop2);

		panel.add(bking);
		panel.add(wking);

		this.add(panel);


	}
}

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

		Knight bknight1 = new Knight(ESide.BLACK, square_size);
		Knight bknight2 = new Knight(ESide.BLACK, square_size);

		Knight wknight1 = new Knight(ESide.WHITE, square_size);
		Knight wknight2 = new Knight(ESide.WHITE, square_size);

		Rook brook1 = new Rook(ESide.BLACK, square_size);
		//brook1.setPosition(300, 30);
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

		 */
