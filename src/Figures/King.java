package Figures;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Trida Rook, vykresli krala
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class King extends Figure {
	public King(double x, double y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.KING;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double x = square_size * this.getFigureX();
		double y = square_size * this.getFigureY();

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
	public boolean moveTo(double cX, double cY, double x, double y, Figure[][] board) {
		int deltaX = (int)Math.abs(getCol() - x);
		int deltaY = (int)Math.abs(getRow() - y);


		if(doCastling(cX, cY, x, y, board)) {
			if(x == 6) {
				board[(int)cY][7].setCol(5);
				board[(int)cY][5] = board[(int)cY][7];
				board[(int)cY][7] = null;
				addCountOfMove();
				return true;
			} else if(x == 2) {
				board[(int)cY][0].setCol(3);
				board[(int)cY][3] = board[(int)cY][0];
				board[(int)cY][0] = null;
				addCountOfMove();
				return true;
			}
		}
		if (deltaX > 1 || deltaY > 1) {
			return false;
		}

// Проверяем, что на клетке, на которую собирается перейти король, нет фигур своего цвета
		if (board[(int)y][(int)x] == null || board[(int)y][(int)x].getColor() != getColor()) {
			addCountOfMove();
			addHistory((int)cX, (int)cY, (int)x, (int)y);
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
	public boolean canEatKing(double cX, double cY, double x, double y, Figure[][] board) {
		int deltaX = (int)Math.abs(getCol() - x);
		int deltaY = (int)Math.abs(getRow() - y);

		if (deltaX > 1 || deltaY > 1) {
			return false;
		}

		if (board[(int)y][(int)x] == null || (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
			return true;

		}
		return false;
	}


	public boolean doCastling(double cX, double cY, double x, double y, Figure[][] board) {
		if(this.countOfMove != 0) {
			return false;
		} else if(isUnderAttack(cX, cY, board)) {
			return false;
		} else if(!(board[(int)cY][7] instanceof Rook)) {
			return false;
		} else if(!(board[(int)cY][0] instanceof Rook)) {
			return false;
		} else if(board[(int)cY][7] != null && board[(int)cY][7].countOfMove != 0) {
			return false;
		} else if(board[(int)cY][0] != null && board[(int)cY][0].countOfMove != 0) {
			return false;
		} else if((int)x == 6) {
			if (board[(int)cY][(int)cX + 1] != null || board[(int)cY][(int)cX + 2] != null) {
				return false;
			}
		} else if((int)x == 2) {
			if(getCol() != 5) {
				if (board[(int) cY][(int) cX - 1] != null || board[(int) cY][(int) cX - 2] != null || board[(int) cY][(int) cX - 3] != null) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isUnderAttack(double cX, double cY, Figure[][] board) {
		Figure king = board[(int)cY][(int)cX];
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

	public boolean hasMoves(double x, double y, Figure[][] board) {
		// Determine the surrounding cells around the king
		int[] xOffset = {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] yOffset = {-1, -1, -1, 0, 0, 1, 1, 1};

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				for (int i = 0; i < xOffset.length; i++) {
					int dx = (int)x + xOffset[i];
					int dy = (int)y + yOffset[i];
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
