package Figures;

import java.awt.*;

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
	public boolean moveTo(double sX, double sY, double dX, double dY, Figure[][] board) {
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
				if (getCol() == dX) {
					if (board[i][getCol()] != null) {
						return false;
					}
				} else {
					if (board[getRow()][i] != null) {
						return false;
					}
				}
			}
			if (board[(int) dY][(int) dX] == null || board[(int) dY][(int) dX].getColor() != getColor()) {
				addCountOfMove();
				addHistory((int) sX, (int) sY, (int) dX, (int) dY);
				return true;
			}
		}
		return false;
	}

	public boolean canEatKing(double cX, double cY, double x, double y, Figure[][] board) {
		if (getCol() == x || getRow() == y) {
			int start, end;
			if (getCol() == x) {
				start = (int)Math.min(getRow(), y);
				end = (int)Math.max(getRow(), y);
			} else {
				start = (int)Math.min(getCol(), x);
				end = (int)Math.max(getCol(), x);
			}

			for (int i = start + 1; i < end; i++) {
				if (getCol() == x) {
					if (board[i][getCol()] != null) {
						return false;
					}
				} else {
					if (board[getRow()][i] != null) {
						return false;
					}
				}
			}
			return board[(int) y][(int) x] != null && (board[(int) y][(int) x].getColor() != getColor() && board[(int) y][(int) x] instanceof King);
		}
		return false;
	}

	public boolean hasMoves(double x, double y, Figure[][] board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(i == x || j == y) {
					if(moveTo(x, y, i, j, board)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
