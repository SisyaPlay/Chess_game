package Figures;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Trida Rook, vykresli pesce
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Pawns extends Figure {

	public Pawns(int x, int y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.PAWNS;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double R = square_size / 2;
		double R2 = square_size / 4;
		double x = square_size * this.getX();
		double y = square_size * this.getY();

		// Telo
		g.setColor(color);
		g.fillArc((int)(x + square_size / 4), (int)(y + square_size / 2), (int)R, (int)R, 0, 180);
		g.setColor(Color.BLACK);
		g.drawArc((int)(x + square_size / 4), (int)(y + square_size / 2), (int)R, (int)R, 0, 180);
		g.drawLine((int)(x + square_size / 4), (int)((y + square_size / 2) + R / 2), (int)((x + square_size / 4) + R), (int)((y + square_size / 2) + R / 2));

		// Hlava
		g.setColor(color);
		g.fillOval((int)((x + square_size / 4) + R / 4), (int)((y + square_size / 2) - R2 + 2), (int)R2, (int)R2);
		g.setColor(Color.BLACK);
		g.drawOval((int)((x + square_size / 4) + R / 4), (int)((y + square_size / 2) - R2 + 2), (int)R2, (int)R2);
	}

	@Override
	public boolean moveTo(int cX, int cY, int x, int y, Figure[][] board) {
		if (enPassant(cX, cY, x, y, board)) {
			return true;
		} else if (getColor().equals(Color.WHITE)) {
			if (cY == 6) {
				if (x != getCol()) {
					if ((getRow() - y == 1) && ((cX - x == 1) || (x - cX == 1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor())) {
						addCountOfMove();
						addHistory(cX, cY, x, y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - y == 1 || getRow() - y == 2) {
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			} else {
				if (x != getCol()) {
					if ((getRow() - y == 1) && ((cX - x == 1) || (x - cX == 1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor())) {
						addCountOfMove();
						addHistory(cX, cY, x, y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - y == 1 && board[cY - 1][x] == null) {
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			}
			return false;
		} else if (getColor().equals(Color.BLACK)) {
			if (cY == 1) {
				if (x != getCol()) {
					if ((getRow() - y == -1) && ((cX - x == -1) || (x - cX == -1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor())) {
						addCountOfMove();
						addHistory(cX, cY, x, y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - y == -1 || getRow() - y == -2) {
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			} else {
				if (x != getCol()) {
					if ((getRow() - y == -1) && ((cX - x == -1) || (x - cX == -1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor())) {
						addCountOfMove();
						addHistory(cX, cY, x, y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - y == -1 && board[cY + 1][x] == null) {
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private boolean enPassant(int cX, int cY, int x, int y, Figure[][] board) {
		if (Math.abs(cY - y) != 1 || Math.abs(cX - x) != 1) {
			return false;
		}else if (getColor().equals(Color.WHITE)) {
			if (y >= cY) {
				return false;
			}else if ((cX - 1 >= 0 && board[cY][cX - 1] != null && board[cY][cX - 1].countOfMove == 1) &&
					board[cY][cX - 1] instanceof Pawns && ((Pawns)board[cY][cX - 1]).firstStep(board[cY][cX - 1].history) &&
					board[cY][cX - 1].getColor() != getColor()) {
				if (board[y][x] == null && board[y + 1][x] != null) {
					board[y + 1][x] = null;
					repaint();
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			}
			else if((cX + 1 < 8 && board[cY][cX + 1] != null && board[cY][cX + 1].countOfMove == 1) &&
					board[cY][cX + 1] instanceof Pawns && ((Pawns)board[cY][cX + 1]).firstStep(board[cY][cX + 1].history) &&
					board[cY][cX + 1].getColor() != getColor()) {
				if (board[y][x] == null && board[y + 1][x] != null) {
					board[y + 1][x] = null;
					repaint();
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			}
		} else if (getColor().equals(Color.BLACK)) {
			if (y <= cY) {
				return false;
			}else if ((cX - 1 >= 0 && board[cY][cX - 1] != null && board[cY][cX - 1].countOfMove == 1) &&
					board[cY][cX - 1] instanceof Pawns && ((Pawns)board[cY][cX - 1]).firstStep(board[cY][cX - 1].history) &&
					board[cY][cX - 1].getColor() != getColor()) {
				if (board[y][x] == null && board[y - 1][x] != null) {
					board[y - 1][x] = null;
					repaint();
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			}
			else if((cX + 1 < 8 && board[cY][cX + 1] != null && board[cY][cX + 1].countOfMove == 1) &&
					board[cY][cX + 1] instanceof Pawns && ((Pawns)board[cY][cX + 1]).firstStep(board[cY][cX + 1].history) &&
					board[cY][cX + 1].getColor() != getColor()) {
				if (board[y][x] == null && board[y - 1][x] != null) {
					board[y - 1][x] = null;
					repaint();
					addCountOfMove();
					addHistory(cX, cY, x, y);
					return true;
				}
			}
		}
		return false;
	}

	private void addHistory(int cX, int cY, int x, int y) {
		Point2D[] points = new Point2D[2];
		points[0] = new Point2D.Double(cX, cY);
		points[1] = new Point2D.Double(x, y);

		this.history.add(points);
	}

	private boolean firstStep(ArrayList<Point2D[]> pawnHistory) {
		Point2D[] firstPoint = pawnHistory.get(0);
		int cY = (int)firstPoint[0].getY();
		int y = (int)firstPoint[1].getY();
		if(Math.abs(cY - y) == 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canEatKing(int cX, int cY, int x, int y, Figure[][] board) {
		if (getColor().equals(Color.WHITE)) {
			if (cY == 6) {
				if (x != getCol()) {
					if ((getRow() - y == 1) && ((cX - x == 1) || (x - cX == 1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor() && board[y][x] instanceof King)) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				if (x != getCol()) {
					if ((getRow() - y == 1) && ((cX - x == 1) || (x - cX == 1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor() && board[y][x] instanceof King)) {
						return true;
					} else {
						return false;
					}
				}
			}
			return false;
		} else if (getColor().equals(Color.BLACK)) {
			if (cY == 1) {
				if (x != getCol()) {
					if ((getRow() - y == -1) && ((cX - x == -1) || (x - cX == -1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor() && board[y][x] instanceof King)) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				if (x != getCol()) {
					if ((getRow() - y == -1) && ((cX - x == -1) || (x - cX == -1))
							&& (board[y][x] != null) && (board[y][x].getColor() != getColor() && board[y][x] instanceof King)) {
						return true;
					} else {
						return false;
					}
				}
			}
			return false;
		}
		return false;
	}

	public boolean hasMoves(int x, int y, Figure[][] board) {
		if (getColor() == Color.WHITE) {
			// Check if there is a pawn that can capture to the top-right or top-left
			if (x < 7 && y > 0 && board[y - 1][x + 1] != null && board[y - 1][x + 1].getColor() == Color.BLACK) {
				return true;
			}
			if (x > 0 && y > 0 && board[y - 1][x - 1] != null && board[y - 1][x - 1].getColor() == Color.BLACK) {
				return true;
			}
			// Check if there is an empty square directly in front of the pawn
			if (y > 0 && board[y - 1][x] == null) {
				return true;
			}
		} else {
			// Check if there is a pawn that can capture to the bottom-right or bottom-left
			if (x < 7 && y < 7 && board[y + 1][x + 1] != null && board[y + 1][x + 1].getColor() == Color.WHITE) {
				return true;
			}
			if (x > 0 && y < 7 && board[y + 1][x - 1] != null && board[y + 1][x - 1].getColor() == Color.WHITE) {
				return true;
			}
			// Check if there is an empty square directly in front of the pawn
			if (y < 7 && board[y + 1][x] == null) {
				return true;
			}
		}
		return false;
	}
}
