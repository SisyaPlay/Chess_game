package Figures;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Trida Rook, vykresli pesce
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Pawns extends Figure {

	public Pawns(double x, double y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.PAWNS;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double R = square_size / 2;
		double R2 = square_size / 4;
		double x = square_size * this.getFigureX();
		double y = square_size * this.getFigureY();

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
	public boolean moveTo(double cX, double cY, double x, double y, Figure[][] board) {
		if (enPassant(cX, cY, x, y, board)) {
			return true;
		} else if (getColor().equals(Color.WHITE)) {
			if (cY == 6) {
				if (x != getCol()) {
					if ((getRow() - (int)y == 1) && (((int)cX - (int)x == 1) || ((int)x - (int)cX == 1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor())) {
						addCountOfMove();
						addHistory((int)cX, (int)cY, (int)x, (int)y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - (int)y == 1 || getRow() - (int)y == 2) {
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
					return true;
				}
			} else {
				if (x != getCol()) {
					if ((getRow() - (int)y == 1) && (((int)cX - (int)x == 1) || ((int)x - (int)cX == 1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor())) {
						addCountOfMove();
						addHistory((int)cX, (int)cY, (int)x, (int)y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - (int)y == 1 && board[(int)cY - 1][(int)x] == null) {
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
					return true;
				}
			}
			return false;
		} else if (getColor().equals(Color.BLACK)) {
			if (cY == 1) {
				if (x != getCol()) {
					if ((getRow() - (int)y == -1) && (((int)cX - (int)x == -1) || ((int)x - (int)cX == -1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor())) {
						addCountOfMove();
						addHistory((int)cX, (int)cY, (int)x, (int)y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - (int)y == -1 || getRow() - (int)y == -2) {
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
					return true;
				}
			} else {
				if (x != getCol()) {
					if ((getRow() - (int)y == -1) && ((cX - (int)x == -1) || ((int)x - (int)cX == -1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor())) {
						addCountOfMove();
						addHistory((int)cX, (int)cY, (int)x, (int)y);
						return true;
					} else {
						return false;
					}
				} else if (getRow() - (int)y == -1 && board[(int)cY + 1][(int)x] == null) {
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private boolean enPassant(double cX, double cY, double x, double y, Figure[][] board) {
		if (Math.abs((int)cY - (int)y) != 1 || Math.abs((int)cX - (int)x) != 1) {
			return false;
		}else if (getColor().equals(Color.WHITE)) {
			if ((int)y >= (int)cY) {
				return false;
			}else if (((int)cX - 1 >= 0 && board[(int)cY][(int)cX - 1] != null && board[(int)cY][(int)cX - 1].countOfMove == 1) &&
					board[(int)cY][(int)cX - 1] instanceof Pawns && ((Pawns)board[(int)cY][(int)cX - 1]).firstStep(board[(int)cY][(int)cX - 1].history) &&
					board[(int)cY][(int)cX - 1].getColor() != getColor()) {
				if (board[(int)y][(int)x] == null && board[(int)y + 1][(int)x] != null) {
					board[(int)y + 1][(int)x] = null;
					repaint();
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
					return true;
				}
			}
			else if(((int)cX + 1 < 8 && board[(int)cY][(int)cX + 1] != null && board[(int)cY][(int)cX + 1].countOfMove == 1) &&
					board[(int)cY][(int)cX + 1] instanceof Pawns && ((Pawns)board[(int)cY][(int)cX + 1]).firstStep(board[(int)cY][(int)cX + 1].history) &&
					board[(int)cY][(int)cX + 1].getColor() != getColor()) {
				if (board[(int)y][(int)x] == null && board[(int)y + 1][(int)x] != null) {
					board[(int)y + 1][(int)x] = null;
					repaint();
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
					return true;
				}
			}
		} else if (getColor().equals(Color.BLACK)) {
			if ((int)y <= (int)cY) {
				return false;
			}else if (((int)cX - 1 >= 0 && board[(int)cY][(int)cX - 1] != null && board[(int)cY][(int)cX - 1].countOfMove == 1) &&
					board[(int)cY][(int)cX - 1] instanceof Pawns && ((Pawns)board[(int)cY][(int)cX - 1]).firstStep(board[(int)cY][(int)cX - 1].history) &&
					board[(int)cY][(int)cX - 1].getColor() != getColor()) {
				if (board[(int)y][(int)x] == null && board[(int)y - 1][(int)x] != null) {
					board[(int)y - 1][(int)x] = null;
					repaint();
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
					return true;
				}
			}
			else if(((int)cX + 1 < 8 && board[(int)cY][(int)cX + 1] != null && board[(int)cY][(int)cX + 1].countOfMove == 1) &&
					board[(int)cY][(int)cX + 1] instanceof Pawns && ((Pawns)board[(int)cY][(int)cX + 1]).firstStep(board[(int)cY][(int)cX + 1].history) &&
					board[(int)cY][(int)cX + 1].getColor() != getColor()) {
				if (board[(int)y][(int)x] == null && board[(int)y - 1][(int)x] != null) {
					board[(int)y - 1][(int)x] = null;
					repaint();
					addCountOfMove();
					addHistory((int)cX, (int)cY, (int)x, (int)y);
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
	public boolean canEatKing(double cX, double cY, double x, double y, Figure[][] board) {
		if (getColor().equals(Color.WHITE)) {
			if ((int)cY == 6) {
				if ((int)x != getCol()) {
					if ((getRow() - (int)y == 1) && (((int)cX - (int)x == 1) || ((int)x - (int)cX == 1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				if ((int)x != getCol()) {
					if ((getRow() - (int)y == 1) && ((cX - (int)x == 1) || ((int)x - (int)cX == 1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
						return true;
					} else {
						return false;
					}
				}
			}
			return false;
		} else if (getColor().equals(Color.BLACK)) {
			if ((int)cY == 1) {
				if ((int)x != getCol()) {
					if ((getRow() - (int)y == -1) && (((int)cX - (int)x == -1) || ((int)x - (int)cX == -1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				if ((int)x != getCol()) {
					if ((getRow() - (int)y == -1) && (((int)cX - (int)x == -1) || ((int)x - (int)cX == -1))
							&& (board[(int)y][(int)x] != null) && (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
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

	public boolean hasMoves(double x, double y, Figure[][] board) {
		if (getColor() == Color.WHITE) {
			// Check if there is a pawn that can capture to the top-right or top-left
			if ((int)x < 7 && (int)y > 0 && board[(int)y - 1][(int)x + 1] != null && board[(int)y - 1][(int)x + 1].getColor() == Color.BLACK) {
				return true;
			}
			if ((int)x > 0 && (int)y > 0 && board[(int)y - 1][(int)x - 1] != null && board[(int)y - 1][(int)x - 1].getColor() == Color.BLACK) {
				return true;
			}
			// Check if there is an empty square directly in front of the pawn
			if (y > 0 && board[(int)y - 1][(int)x] == null) {
				return true;
			}
		} else {
			// Check if there is a pawn that can capture to the bottom-right or bottom-left
			if ((int)x < 7 && (int)y < 7 && board[(int)y + 1][(int)x + 1] != null && board[(int)y + 1][(int)x + 1].getColor() == Color.WHITE) {
				return true;
			}
			if ((int)x > 0 && (int)y < 7 && board[(int)y + 1][(int)x - 1] != null && board[(int)y + 1][(int)x - 1].getColor() == Color.WHITE) {
				return true;
			}
			// Check if there is an empty square directly in front of the pawn
			if (y < 7 && board[(int)y + 1][(int)x] == null) {
				return true;
			}
		}
		return false;
	}
}
