package Figures;

import java.awt.*;
import java.util.ArrayList;

/**
 * Trida Queen, vykresli damu
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Queen extends Figure {
	public Queen(double x, double y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.QUEEN;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double x = square_size * this.getFigureX();
		double y = square_size * this.getFigureY();

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
	public boolean moveTo(double sX, double sY, double dX, double dY, Figure[][] board) {
		int deltaX = (int)Math.abs(getCol() - dX);
		int deltaY = (int)Math.abs(getRow() - dY);

		if (getCol() == dX || getRow() == dY) {
			int start, end;
			if (getCol() == dX) { // Tahy po vertikale
				start = (int)Math.min(getRow(), dY);
				end = (int)Math.max(getRow(), dY);
			} else { // Tahy po horizontale
				start = (int)Math.min(getCol(), dX);
				end = (int)Math.max(getCol(), dX);
			}

			for (int i = start + 1; i < end; i++) {
				if (getCol() == dX) { // Tahy po diagonale
					if (board[i][getCol()] != null) { // Kontroluje jestli je figura na ceste
						return false;
					}
				} else { // движение по горизонтали
					if (board[getRow()][i] != null) { // Kontroluje jestli je figura na ceste
						return false;
					}
				}
			}
			// Kontroluje jestli barva figury na konecne pozice neni stejna
			if (board[(int) dY][(int) dX] == null || board[(int) dY][(int) dX].getColor() != getColor()) {
				addCountOfMove();
				return true;
			}
		}

		if (deltaX != deltaY) {
			return false;
		}

		int stepX = (dX - getCol()) > 0 ? 1 : -1;
		int stepY = (dY - getRow()) > 0 ? 1 : -1;

		for (int i = 1; i < deltaX; i++) {
			int checkX = getCol() + i * stepX;
			int checkY = getRow() + i * stepY;

			if (board[checkY][checkX] != null) {
				return false;
			}
		}

		if (board[(int) dY][(int) dX] == null || board[(int) dY][(int) dX].getColor() != getColor()) {
			addCountOfMove();
			return true;
		}

		return false;
	}


	public boolean canEatKing(double cX, double cY, double x, double y, Figure[][] board) {
		int deltaX = (int)Math.abs(getCol() - x);
		int deltaY = (int)Math.abs(getRow() - y);

		if (getCol() == (int)x || getRow() == (int)y) {
			int start, end;
			if (getCol() == x) {
				start = (int)Math.min(getRow(), y);
				end = (int)Math.max(getRow(), y);
			} else {
				start = (int)Math.min(getCol(), x);
				end = (int)Math.max(getCol(), x);
			}

			for (int i = start + 1; i < end; i++) {
				if (getCol() == (int)x) {
					if (board[i][getCol()] != null) {
						return false;
					}
				} else {
					if (board[getRow()][i] != null) {
						return false;
					}
				}
			}
			if (board[(int)y][(int)x] != null && (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
				return true;
			}
		}

		if (deltaX != deltaY) {
			return false;
		}

		int stepX = ((int)x - getCol()) > 0 ? 1 : -1;
		int stepY = ((int)y - getRow()) > 0 ? 1 : -1;

		for (int i = 1; i < deltaX; i++) {
			int checkX = getCol() + i * stepX;
			int checkY = getRow() + i * stepY;

			if (board[checkY][checkX] != null && !(board[checkY][checkX]instanceof King)) {
				return false;
			}
		}

		return board[(int) y][(int) x] != null && (board[(int) y][(int) x].getColor() != getColor() && board[(int) y][(int) x] instanceof King);
	}

	public boolean hasMoves(double x, double y, Figure[][] board) {
		ArrayList<Integer> xOffset = new ArrayList<>();
		ArrayList<Integer> yOffset = new ArrayList<>();
		int startX = (int) x;
		int startY = (int) y;

		while (startX < 8 && startY >= 0) {
			xOffset.add(startX);
			yOffset.add(startY);
			startX++;
			startY--;
		}
		startX = (int) x;
		startY = (int) y;

		while (startX < 8 && startY < 8) {
			xOffset.add(startX);
			yOffset.add(startY);
			startX++;
			startY++;
		}
		startX = (int) x;
		startY = (int) y;

		while (startX >= 0 && startY < 8) { // Исправлено условие
			xOffset.add(startX);
			yOffset.add(startY);
			startX--;
			startY++;
		}
		startX = (int) x;
		startY = (int) y;

		while (startX >= 0 && startY >= 0) { // Исправлено условие
			xOffset.add(startX);
			yOffset.add(startY);
			startX--;
			startY--;
		}

		for (int i = 0; i < xOffset.size(); i++) {
			if (moveTo((int)x, (int)y, xOffset.get(i), yOffset.get(i), board)) {
				return true;
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i == x || j == y) {
					if (moveTo(x, y, i, j, board)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
