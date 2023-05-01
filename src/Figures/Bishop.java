package Figures;

import java.awt.*;

/**
 * Trida Rook, vykresli strelce
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Bishop extends Figure {

	public Bishop(int x, int y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.BISHOP;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double x = square_size * this.getX();
		double y = square_size * this.getY();


		g.setColor(color);
		int[] xPoints = {(int) (x + square_size / 4), (int) (x + square_size / 2), (int) (x + square_size - square_size / 4)};
		int[] yPoints = {(int) (y + square_size / 2), (int) (y + square_size / 6), (int) (y + square_size / 2)};
		g.fillPolygon(xPoints, yPoints, 3);
		g.setColor(Color.BLACK);
		g.drawLine((int) (x + square_size / 4), (int) (y + square_size / 2), (int) (x + square_size / 2), (int) (y + square_size / 6));
		g.drawLine((int) (x + square_size - square_size / 4), (int) (y + square_size / 2), (int) (x + square_size / 2), (int) (y + square_size / 6));
		g.drawLine((int) (x + square_size / 4), (int) (y + square_size / 2), (int) (x + square_size - square_size / 4), (int) (y + square_size / 2));

		g.setColor(color);
		g.fillRect((int) (x + square_size / 3), (int) (y + (square_size - square_size / 3.5)), (int) (square_size / 3), (int) (square_size / 6));
		g.setColor(Color.BLACK);
		g.drawRect((int) (x + square_size / 3), (int) (y + (square_size - square_size / 3.5)), (int) (square_size / 3), (int) (square_size / 6));

		g.setColor(color);
		g.fillOval((int) (x + square_size / 4), (int) (y + square_size / 3), (int) (square_size / 2), (int) (square_size / 2));
		g.setColor(Color.BLACK);
		g.drawOval((int) (x + square_size / 4), (int) (y + square_size / 3), (int) (square_size / 2), (int) (square_size / 2));
	}

	@Override
	public boolean moveTo(int cX, int cY, int x, int y, Figure[][] board) {
		// проверяем, что слон двигается по диагонали
		int deltaX = Math.abs(getCol() - x);
		int deltaY = Math.abs(getRow() - y);

		if (deltaX != deltaY) {
			// слон не двигается по диагонали
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

	@Override
	public boolean canEatKing(int cX, int cY, int x, int y, Figure[][] board) {
		// проверяем, что слон двигается по диагонали
		int deltaX = Math.abs(getCol() - x);
		int deltaY = Math.abs(getRow() - y);

		if (deltaX != deltaY) {
			// слон не двигается по диагонали
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
		for (int i = -1; i <= 1; i += 2) {
			for (int j = -1; j <= 1; j += 2) {
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
