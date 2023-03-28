package Figures;

import javax.swing.*;

import java.awt.*;


/**
 * Abstraktni trida Figure. Dedi od JPanel
 */
public abstract class Figure extends JPanel{

	protected int x; // Osa x
	protected int y; // Osa Y
	protected Color color; // Barva figury
	protected double square_size; // Velikost ctverce

	/**
	 * Konstruktor, odstrani pozadi, nastavi preferovanou velikost figury
	 * @param x
	 * @param y
	 * @param color
	 * @param square_size
	 */
	public Figure(int x, int y, Color color, double square_size) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.square_size = square_size;
		this.setOpaque(false); // Odstaneni pozadi
		this.setPreferredSize(new Dimension((int)square_size, (int)square_size));
	}

	/**
	 * Typicka metoda JPanelu
	 * @param g the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * Setter, ktery nastavi velikost figury
	 * @param square_size
	 */
	public void setSize(double square_size) {
		this.square_size = square_size;
	}

	/**
	 * Getter X a Y. Vraty x a y.
	 * @return x
	 * @return x
	 */
	public int getX() { return this.x; }
	public int getY() { return this.y; }

	/**
	 * Getter radu a sloupcu. Vraty x a y.
	 * @return x
	 * @return x
	 */
	public int getCol() { return this.x; }
	public int getRow() { return this.y; }

	/**
	 * Setter radu a sloupcu. Nastavi x a y.
	 */
	public void setCol(int x) { this.x = x; }
	public void setRow(int y) { this.y = y; }
}

