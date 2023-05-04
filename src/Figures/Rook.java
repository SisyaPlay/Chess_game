package Figures;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Trida Rook, vykresli vez
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Rook extends Figure {

	public Rook(double x, double y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.ROOK;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double x = square_size * this.getFigureX();
		double y = square_size * this.getFigureY();

		g.setColor(color);

		g.fillRect((int)(x + square_size / 6), (int)(y + square_size - square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 4));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 6), (int)(y + square_size - square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 4));

		g.setColor(color);
		g.fillRect((int)(x + (square_size / 4)), (int)(y + square_size / 2) , (int)(square_size / 2), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + (square_size / 4)), (int)(y + square_size / 2) , (int)(square_size / 2), (int)(square_size / 6));

		g.setColor(color);
		g.fillRect((int)(x + square_size / 6), (int)(y + square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 6), (int)(y + square_size / 3), (int)(square_size - square_size / 3), (int)(square_size / 6));

		g.setColor(color);
		g.fillRect((int)(x + square_size / 6), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 6), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(color);
		g.fillRect((int)(x + ((square_size - square_size / 2) + (square_size / 3)) / 2), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + ((square_size - square_size / 2) + (square_size / 3)) / 2), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(color);
		g.fillRect((int)(x + square_size  - square_size /3), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size  - square_size /3), (int)(y + square_size / 6), (int)(square_size / 6), (int)(square_size / 6));
	}
	@Override
	public boolean moveTo(double cX, double cY, double x, double y, Figure[][] board) {
		// проверяем, что башня двигается по вертикали или горизонтали
		if (getCol() == x || getRow() == y) {
			int start, end;
			if (getCol() == x) { // движение по вертикали
				start = (int)Math.min(getRow(), y);
				end = (int)Math.max(getRow(), y);
			} else { // движение по горизонтали
				start = (int)Math.min(getCol(), x);
				end = (int)Math.max(getCol(), x);
			}

			for (int i = start + 1; i < end; i++) { // проверяем все клетки на пути
				if (getCol() == x) { // движение по вертикали
					if (board[i][getCol()] != null) { // есть фигура на пути
						return false;
					}
				} else { // движение по горизонтали
					if (board[getRow()][i] != null) { // есть фигура на пути
						return false;
					}
				}
			}
			// проверяем цвет фигуры на конечной позиции
			if (board[(int)y][(int)x] == null || board[(int)y][(int)x].getColor() != getColor()) {
				addCountOfMove();
				addHistory((int)cX, (int)cY, (int)x, (int)y);
				return true;
			}
		}
		return false;
	}

	public void addHistory(int cX, int cY, int x, int y) {
		Point2D[] points = new Point2D[2];
		points[0] = new Point2D.Double(cX, cY);
		points[1] = new Point2D.Double(x, y);

		this.history.add(points);
	}

	public boolean canEatKing(double cX, double cY, double x, double y, Figure[][] board) {
// проверяем, что башня двигается по вертикали или горизонтали
		if (getCol() == x || getRow() == y) {
			int start, end;
			if (getCol() == x) { // движение по вертикали
				start = (int)Math.min(getRow(), y);
				end = (int)Math.max(getRow(), y);
			} else { // движение по горизонтали
				start = (int)Math.min(getCol(), x);
				end = (int)Math.max(getCol(), x);
			}

			for (int i = start + 1; i < end; i++) { // проверяем все клетки на пути
				if (getCol() == x) { // движение по вертикали
					if (board[i][getCol()] != null) { // есть фигура на пути
						return false;
					}
				} else { // движение по горизонтали
					if (board[getRow()][i] != null) { // есть фигура на пути
						return false;
					}
				}
			}
			// проверяем цвет фигуры на конечной позиции
			if (board[(int)y][(int)x] == null || (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
				addCountOfMove();
				addHistory((int)cX, (int)cY, (int)x, (int)y);
				return true;
			}
		}
		return false;
	}

	public boolean hasMoves(double x, double y, Figure[][] board) {
		// Check horizontal moves to the right
		for (int col = (int)x + 1; col < 8; col++) {
			Figure figure = board[(int)y][col];
			if (figure == null) {
				continue;
			}
			if (figure.getColor() != getColor()) {
				return true;
			}
			break;
		}

		// Check horizontal moves to the left
		for (int col = (int)x - 1; col >= 0; col--) {
			Figure figure = board[(int)y][col];
			if (figure == null) {
				continue;
			}
			if (figure.getColor() != getColor()) {
				return true;
			}
			break;
		}

		// Check vertical moves up
		for (int row = (int)y - 1; row >= 0; row--) {
			Figure figure = board[row][(int)x];
			if (figure == null) {
				continue;
			}
			if (figure.getColor() != getColor()) {
				return true;
			}
			break;
		}

		// Check vertical moves down
		for (int row = (int)y + 1; row < 8; row++) {
			Figure figure = board[row][(int)x];
			if (figure == null) {
				continue;
			}
			if (figure.getColor() != getColor()) {
				return true;
			}
			break;
		}

		return false;
	}
}
