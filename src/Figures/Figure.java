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
	 *
	 * @param sX    pocatecni pozice na ose x
	 * @param sY    pocatecni pozice na ose y
	 * @param dX    konecni pozice na ose x
	 * @param dY    konecni pozice na ose y
	 * @param board pole figur
	 * @return
	 */
	public boolean moveTo(double sX, double sY, double dX, double dY, Figure[][] board) {
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
	 *
	 * @param sX    pocatecni pozice na ose x
	 * @param sY    pocatecni pozice na ose y
	 * @param dX    konecni pozice na ose x
	 * @param dY    konecni pozice na ose y
	 * @param board pole figur
	 */
	protected boolean canEatKing(double sX, double sY, double dX, double dY, Figure[][] board) {
		return false;
	}

	/**
	 * Metoda kontroluje jestli kral je pod utokem
	 *
	 * @param sX    pozice krala na ose X
	 * @param sY    pozice krala na ose Y
	 * @param board pole figur
	 * @return
	 */
	public boolean isUnderAttack(double sX, double sY, Figure[][] board) {
		return false;
	}

	/**
	 * Metoda kontroluje jestli kral muze pretahovat na bezpecne misto
	 *
	 * @param dX    konecni pozice na ose x
	 * @param dY    konecni pozice na ose y
	 * @param board pole figur
	 * @param king  kral
	 * @return
	 */
	public boolean isThisPlaceIsSafe(int dX, int dY, Figure[][] board, Figure king) {
		return false;
	}

	/**
	 * Metoda kontroluje jestli nejaka figura ma tahy
	 *
	 * @param sX    pozice libovolne figury na ose X
	 * @param sY    pozice libovolne figury na ose Y
	 * @param board pole figur
	 * @return
	 */
	public boolean hasMoves(double sX, double sY, Figure[][] board) {
		return false;
	}

	/**
	 * Metoda vrati historie tahu pro pocitani figury kolik tahu uz udelala
	 * @return
	 */
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
	 *
	 * @param sX
	 * @param sY
	 * @param dX
	 * @param dY
	 */
	public void addHistory(int sX, int sY, int dX, int dY) {
		Point2D[] points = new Point2D[2];
		points[0] = new Point2D.Double(sX, sY);
		points[1] = new Point2D.Double(dX, dY);

		this.history.add(points);
	}

	/**
	 * Metoda Zjisti jestli nejaka figura muze zachranit krale
	 * @param sX pozice libovolne figury na ose X
	 * @param sY pozice libovolne figury na ose Y
	 * @param dX konecni pozice na ose x
	 * @param dY konecni pozice na ose y
	 * @param board pole figur
	 * @return
	 */
	public boolean canSafeKing(double sX, double sY, double dX, double dY, Figure[][] board) {
		Figure defender = board[(int)sY][(int)sX];
		Figure king = null;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Figure figure = board[i][j];
				if(figure instanceof King && figure.getColor() == getColor()) {
					king = figure;
					break;
				}
			}
		}

		if(defender.moveTo(sX, sY, dX, dY, board)) {
			if (board[(int)dY][(int)dX] == null || (board[(int)dY][(int)dX] != null && board[(int)dY][(int)dX].getColor() != getColor())) {
				Figure prevFigure = board[defender.getRow()][defender.getCol()];
				Figure prevEatableFigure = board[(int)dY][(int)dX];
				board[defender.getRow()][defender.getCol()] = null;
				board[(int)dY][(int)dX] = defender;
				boolean isSave = king != null && !king.isUnderAttack(king.getCol(), king.getRow(), board);
				board[(int)dY][(int)dX] = prevEatableFigure;
				board[defender.getRow()][defender.getCol()] = prevFigure;
				return isSave;
			}
		}
		return false;
	}

	/**
	 * Metoda projde po vsem cetvercu a zjisti jestli figura-defender muze zachranit krale
	 * @param sX pozice libovolne figury na ose X
	 * @param sY pozice libovolne figury na ose Y
	 * @param board pole figur
	 * @return
	 */
	public boolean safeKing(double sX, double sY, Figure[][] board) {
		Figure defender = board[(int)sY][(int)sX];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(defender.canSafeKing(sX, sY, j, i, board)) {
					return true;
				}
			}
		}
		return false;
	}
}

