package Figures;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;


/**
 * Abstraktni trida Figure. Dedi od JPanel
 */
public abstract class Figure extends JPanel{

	protected double x; // Osa x
	protected double y; // Osa Y
	protected Color color; // Barva figury
	protected double square_size; // Velikost ctverce
	protected EFigure type;
	protected ArrayList<Point2D[]> history = new ArrayList<>();
	protected int countOfMove;

	/**
	 * Konstruktor, odstrani pozadi, nastavi preferovanou velikost figury
	 * @param x
	 * @param y
	 * @param color
	 * @param square_size
	 */
	public Figure(double x, double y, Color color, double square_size) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.square_size = square_size;
		this.setOpaque(false); // Odstaneni pozadi
		this.setPreferredSize(new Dimension((int)square_size, (int)square_size));
		this.type = null;
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
	public double getFigureX() { return this.x; }
	public double getFigureY() { return this.y; }

	/**
	 * Getter radu a sloupcu. Vraty x a y.
	 * @return x
	 * @return x
	 */
	public int getCol() { return (int)this.x; }
	public int getRow() { return (int)this.y; }

	/**
	 * Setter radu a sloupcu. Nastavi x a y.
	 */
	public void setCol(int x) { this.x = x; }
	public void setRow(int y) { this.y = y; }

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public EFigure getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public boolean moveTo(double cX, double cY, double x, double y, Figure[][] board) {
		return true;
	}
	public void addCountOfMove() {
		countOfMove++;
	}

	public int getCountOfMove() {
		return countOfMove;
	}


	protected boolean canEatKing(double i, double j, double col, double row, Figure[][] board) {
		return false;
	}

	public boolean isUnderAttack(double cX, double cY, Figure[][] board) {
		return false;
	}
	public boolean hasMoves(double cX, double cY, Figure[][] board) {
		return false;
	}
}

