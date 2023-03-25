package Figures;

import javax.swing.*;

import java.awt.*;



public abstract class Figure extends JPanel{

	protected int x;
	protected int y;
	protected Color color;
	protected double square_size;


	public Figure(int x, int y, Color color, double square_size) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.square_size = square_size;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int)square_size, (int)square_size));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setSize(double square_size) {
		this.square_size = square_size;
	}

	public int getX() { return this.x; }
	public int getY() { return this.y; }

	public int getCol() { return this.x; }
	public int getRow() { return this.y; }

	public void setCol(int x) { this.x = x; }
	public void setRow(int y) { this.y = y; }
}

