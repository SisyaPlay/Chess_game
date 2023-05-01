package Figures;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Trida Queen, vykresli damu
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Queen extends Figure {
	public Queen(int x, int y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.QUEEN;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double x = square_size * this.getX();
		double y = square_size * this.getY();

		g.setColor(color);
		g.fillOval((int)(x + square_size / 8), (int)(y + square_size / 2), (int)(square_size - square_size / 4), (int)(square_size / 4));
		g.fillRect((int)(x + square_size / 4), (int)(y + (square_size - square_size / 3.5)), (int)(square_size - square_size / 2), (int)(square_size / 6));

		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 4), (int)(y + (square_size - square_size / 3.5)), (int)(square_size - square_size / 2), (int)(square_size / 6));
		g.drawOval((int)(x + square_size / 8), (int)(y + square_size / 2), (int)(square_size - square_size / 4), (int)(square_size / 4));

		g.setColor(color);
		int[] xPointsOfLeft = {(int)(x + square_size / 8), (int)(x + square_size / 3), (int)(x + square_size / 8)};
		int[] yPointsOfLeft = {(int)(y + ((square_size / 2) + (square_size / 4) / 2)), (int)(y + square_size / 2), (int)(y + square_size / 4)};
		g.fillPolygon(xPointsOfLeft, yPointsOfLeft, 3);
		g.setColor(Color.BLACK);
		g.drawLine((int)(x + square_size / 8), (int)(y + ((square_size / 2) + (square_size / 4) / 2)),
				(int)(x + square_size / 8), (int)(y + square_size / 4));
		g.drawLine((int)(x + square_size / 8), (int)(y + square_size / 4), (int)(x + square_size / 3), (int)(y + square_size / 2));
		g.drawLine((int)(x + square_size / 8), (int)(y + ((square_size / 2) + (square_size / 4) / 2)), (int)(x + square_size / 3), (int)(y + square_size / 2));

		g.setColor(color);
		int[] xPointsOfRight = {(int)(x + square_size - square_size / 8), (int)(x + square_size - square_size / 3), (int)(x + square_size - square_size / 8)};
		int[] yPointsOfRight = {(int)(y + square_size / 4), (int)(y + square_size / 2), (int)(y + ((square_size / 2) + (square_size / 4) / 2))};
		g.fillPolygon(xPointsOfRight, yPointsOfRight, 3);
		g.setColor(Color.BLACK);
		g.drawLine((int)(x + square_size - square_size / 8), (int)(y + square_size / 4), (int)(x + square_size - square_size / 3), (int)(y + square_size / 2));
		g.drawLine((int)(x + square_size - square_size / 8), (int)(y + ((square_size / 2) + (square_size / 4) / 2)),
				(int)(x + square_size - square_size / 8), (int)(y + square_size / 4));
		g.drawLine((int)(x + square_size - square_size / 3), (int)(y + square_size / 2),
				(int)(x + square_size - square_size / 8), (int)(y + ((square_size / 2) + (square_size / 4) / 2)));

		g.setColor(color);
		int[] xPointsOfCeneter = {(int)(x + square_size - square_size / 3), (int)(x + square_size - square_size /2), (int)(x + square_size / 3)};
		int[] yPointsOfCenter = {(int)(y + square_size / 2), (int)(y + square_size / 4), (int)(y + square_size / 2)};
		g.fillPolygon(xPointsOfCeneter, yPointsOfCenter, 3);

		g.setColor(Color.black);
		g.drawLine((int)(x + square_size - square_size / 3), (int)(y + square_size / 2), (int)(x + square_size - square_size /2) , (int)(y + square_size / 4));
		g.drawLine((int)(x + square_size - square_size /2) , (int)(y + square_size / 4), (int)(x + square_size / 3), (int)(y + square_size / 2));
		g.drawLine((int)(x + square_size - square_size / 3), (int)(y + square_size / 2), (int)(x + square_size / 3), (int)(y + square_size / 2));
	}
	@Override
	public boolean moveTo(int cX, int cY, int x, int y, Figure[][] board) {
		int deltaX = Math.abs(getCol() - x);
		int deltaY = Math.abs(getRow() - y);

		if (getCol() == x || getRow() == y) {
			int start, end;
			if (getCol() == x) { // движение по вертикали
				start = Math.min(getRow(), y);
				end = Math.max(getRow(), y);
			} else { // движение по горизонтали
				start = Math.min(getCol(), x);
				end = Math.max(getCol(), x);
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
			if (board[y][x] == null || board[y][x].getColor() != getColor()) {
				return true;
			}
		}

		if (deltaX != deltaY) {
			return false;
		}

		// определяем направление движения слона
		int stepX = (x - getCol()) > 0 ? 1 : -1;
		int stepY = (y - getRow()) > 0 ? 1 : -1;

		// проверяем клетки на пути движения слона
		for (int i = 1; i < deltaX; i++) {
			int checkX = getCol() + i * stepX;
			int checkY = getRow() + i * stepY;

			if (board[checkY][checkX] != null) {
				// на пути стоит фигура
				return false;
			}
		}

		// проверяем, что конечная клетка пуста или занята фигурой другого цвета
		if (board[y][x] == null || board[y][x].getColor() != getColor()) {
			return true;
		}

		return false;
	}


	public boolean canEatKing(int cX, int cY, int x, int y, Figure[][] board) {
		int deltaX = Math.abs(getCol() - x);
		int deltaY = Math.abs(getRow() - y);

		if (getCol() == x || getRow() == y) {
			int start, end;
			if (getCol() == x) { // движение по вертикали
				start = Math.min(getRow(), y);
				end = Math.max(getRow(), y);
			} else { // движение по горизонтали
				start = Math.min(getCol(), x);
				end = Math.max(getCol(), x);
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
			if (board[y][x] == null || (board[y][x].getColor() != getColor() && board[y][x] instanceof King)) {
				return true;
			}
		}

		if (deltaX != deltaY) {
			return false;
		}

		// определяем направление движения слона
		int stepX = (x - getCol()) > 0 ? 1 : -1;
		int stepY = (y - getRow()) > 0 ? 1 : -1;

		// проверяем клетки на пути движения слона
		for (int i = 1; i < deltaX; i++) {
			int checkX = getCol() + i * stepX;
			int checkY = getRow() + i * stepY;

			if (board[checkY][checkX] != null) {
				// на пути стоит фигура
				return false;
			}
		}

		// проверяем, что конечная клетка пуста или занята фигурой другого цвета
		if (board[y][x] == null || (board[y][x].getColor() != getColor() && board[y][x] instanceof King)) {
			return true;
		}

		return false;
	}

	public boolean hasMoves(int x, int y, Figure[][] board) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) continue; // skip checking the same position as the king

				int row = y + i;
				int col = x + j;

				while (row >= 0 && row < 8 && col >= 0 && col < 8) {
					Figure figure = board[row][col];
					if (figure == null) {
						if (moveTo(col, row, x, y, board)) return true;
					} else if (figure.getColor() != getColor() && figure.moveTo(col, row, x, y, board)) {
						return true;
					} else {
						break;
					}
					row += i;
					col += j;
				}
			}
		}
		return false;
	}
}
