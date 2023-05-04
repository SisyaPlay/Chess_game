package Figures;

import Controllers.BoardController;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Trida Rook, vykresli krala
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class King extends Figure {
	public King(int x, int y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.KING;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double x = square_size * this.getX();
		double y = square_size * this.getY();

		g.setColor(color);
		g.fillRect((int) (x + square_size / 4), (int) (y + (square_size - square_size / 3.5)), (int) (square_size - square_size / 2), (int) (square_size / 6));

		g.setColor(Color.BLACK);
		g.drawRect((int) (x + square_size / 4), (int) (y + (square_size - square_size / 3.5)), (int) (square_size - square_size / 2), (int) (square_size / 6));

		g.setColor(color);
		g.fillOval((int)(x + square_size / 7), (int)(y + square_size / 2.5), (int)square_size / 3, (int)square_size / 3);
		g.fillOval((int)(x + square_size -square_size / 2), (int)(y + square_size / 2.5), (int)square_size / 3, (int)square_size / 3);

		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 7), (int)(y + square_size / 2.5), (int)square_size / 3, (int)square_size / 3);
		g.drawOval((int)(x + square_size -square_size / 2), (int)(y + square_size / 2.5), (int)square_size / 3, (int)square_size / 3);

		g.setColor(color);
		g.fillOval((int)(x + square_size / 3), (int)(y + square_size /4), (int)(square_size / 3), (int)(square_size / 2));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3), (int)(y + square_size /4), (int)(square_size / 3), (int)(square_size / 2));
	}
	@Override
	public boolean moveTo(int cX, int cY, int x, int y, Figure[][] board) {
		int deltaX = Math.abs(getCol() - x);
		int deltaY = Math.abs(getRow() - y);


		if(doCastling(cX, cY, x, y, board)) {
			if(x == 6) {
				board[cY][7].setCol(5);
				return true;
			} else if(x == 2) {
				board[cY][0].setCol(3);
				return true;
			}
		}
		if (deltaX > 1 || deltaY > 1) {
			return false;
		}

// Проверяем, что на клетке, на которую собирается перейти король, нет фигур своего цвета
		if (board[y][x] == null || board[y][x].getColor() != getColor()) {
			addCountOfMove();
			addHistory(cX, cY, x, y);
			return true;

		}
		return false;
	}

	public void addHistory(int cX, int cY, int x, int y) {
		Point2D[] points = new Point2D[2];
		points[0] = new Point2D.Double(cX, cY);
		points[1] = new Point2D.Double(x, y);

		this.history.add(points);
	}

	@Override
	public boolean canEatKing(int cX, int cY, int x, int y, Figure[][] board) {
		int deltaX = Math.abs(getCol() - x);
		int deltaY = Math.abs(getRow() - y);

		if (deltaX > 1 || deltaY > 1) {
			return false;
		}

		if (board[y][x] == null || (board[y][x].getColor() != getColor() && board[y][x] instanceof King)) {
			return true;

		}
		return false;
	}


	public boolean doCastling(int cX, int cY, int x, int y, Figure[][] board) {
		if(this.countOfMove != 0) {
			return false;
		} else if(isUnderAttack(cX, cY, board)) {
			return false;
		} else if(!(board[cY][7] instanceof Rook) && board[cY][7].countOfMove != 0) {
			return false;
		} else if(!(board[cY][0] instanceof Rook) && board[cY][0].countOfMove != 0) {
			return false;
		} else if( x == 6) {
			if (board[cY][cX + 1] != null || board[cY][cX + 2] != null) {
				return false;
			}
		} else if(x == 2) {
			if(board[cY][cX - 1] != null || board[cY][cX - 2] != null || board[cY][cX - 3] != null) {
				return false;
			}
		}
		return true;
	}

	public boolean isUnderAttack(int cX, int cY, Figure[][] board) {
		Figure king = board[cY][cX];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Figure figure = board[j][i];
				if (figure != null && figure.getColor() != getColor()) {
					if (figure.canEatKing(i, j, king.getCol(), king.getRow(), board)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasMoves(int x, int y, Figure[][] board) {
		// Determine the surrounding cells around the king
		int[] xOffset = {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] yOffset = {-1, -1, -1, 0, 0, 1, 1, 1};

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				for (int i = 0; i < xOffset.length; i++) {
					int dx = x + xOffset[i];
					int dy = y + yOffset[i];
					Figure figure = board[row][col];
					if (dx >= 0 && dx < 8 && dy >= 0 && dy < 8) { // Check if the cell is inside the boar
						if (figure != null && figure.getColor() != getColor() && figure.moveTo(figure.getCol(), figure.getRow(), dx, dy, board)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
