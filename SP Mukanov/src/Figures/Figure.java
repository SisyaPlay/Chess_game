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

	/**
	 * Setter X a Y. Nastavi x a y.
	 */
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Vrati typ figury
	 * @return
	 */
	public EFigure getType() {
		return type;
	}

	/**
	 * Vrati barvu figury
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Metoda, ktera kontroluje, pokud nahodni figura muze nekam se posunout
	 * @param cX pocatecni pozice na ose x
	 * @param cY pocatecni pozice na ose y
	 * @param x konecni pozice na ose x
	 * @param y konecni pozice na ose y
	 * @param board pole figur
	 * @return
	 */
	public boolean moveTo(double cX, double cY, double x, double y, Figure[][] board) {
		return true;
	}

	/**
	 * Pridava pocet tahu
	 */
	public void addCountOfMove() {
		countOfMove++;
	}

	/**
	 * Vrati pocet tahu
	 * @return
	 */
	public int getCountOfMove() {
		return countOfMove;
	}

	/**
	 * Metoda kontroluje jestli krala figura muze zabit
	 * @param i pocatecni pozice na ose x
	 * @param j pocatecni pozice na ose y
	 * @param col konecni pozice na ose x
	 * @param row konecni pozice na ose y
	 * @param board pole figur
	 */
	protected boolean canEatKing(double i, double j, double col, double row, Figure[][] board) {
		return false;
	}

	/**
	 * Metoda kontroluje jestli kral je pod utokem
	 * @param cX pozice krala na ose X
	 * @param cY pozice krala na ose Y
	 * @param board pole figur
	 * @return
	 */
	public boolean isUnderAttack(double cX, double cY, Figure[][] board) {
		return false;
	}

	/**
	 * Metoda kontroluje jestli nejaka figura ma tahy
	 * @param cX pozice libovolne figury na ose X
	 * @param cY pozice libovolne figury na ose Y
	 * @param board pole figur
	 * @return
	 */
	public boolean hasMoves(double cX, double cY, Figure[][] board) {
		return false;
	}

	public ArrayList<Point2D[]> getHistory() {
		return history;
	}

	/**
	 * Nastafi historie tahu
	 * @param history
	 */
	public void setHistory(ArrayList<Point2D[]> history) {
		this.history = history;
	}

	/**
	 * nastavi pocet tahu
	 * @param countOfMove
	 */
	public void setCountOfMove(int countOfMove) {
		this.countOfMove = countOfMove;
	}

	/**
	 * Pridava historie
	 * @param cX
	 * @param cY
	 * @param x
	 * @param y
	 */
	public void addHistory(int cX, int cY, int x, int y) {
		Point2D[] points = new Point2D[2];
		points[0] = new Point2D.Double(cX, cY);
		points[1] = new Point2D.Double(x, y);

		this.history.add(points);
	}

	public boolean canSafeKing(double cX, double cY, double x, double y, Figure[][] board) {
		Figure defender = board[(int)cY][(int)cX];
		Figure king = null;
		int[] xOffset = {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] yOffset = {-1, -1, -1, 0, 0, 1, 1, 1};

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Figure figure = board[i][j];
				if(figure instanceof King && figure.getColor() == getColor()) {
					king = figure;
				}
			}
		}

		for (int i = 0; i < yOffset.length; i++) {
			for (int j = 0; j < xOffset.length; j++) {
				if(king.getCol() + xOffset[j] == x && king.getRow() + yOffset[i] == y) {
					if (defender.moveTo(cX, cY, king.getCol() + xOffset[j], king.getRow() + yOffset[i], board)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

