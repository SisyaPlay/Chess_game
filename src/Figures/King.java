package Figures;

import View.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

/**
 * Trida Rook, vykresli krala
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class King extends Figure {
	private Timer timer;
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
				animation(5, cY, 7, board);
				addCountOfMove();
				return true;
			} else if(x == 2) {
				animation(3, cY, 0, board);
				addCountOfMove();
				return true;
			}
		}
		if (deltaX > 1 || deltaY > 1) {
			return false;
		}

		if (board[(int)y][(int)x] == null || board[(int)y][(int)x].getColor() != getColor()) {
			addCountOfMove();
			addHistory((int)cX, (int)cY, (int)x, (int)y);
			return true;

		}
		return false;
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

	/**
	 * Metoda kontroluje pokud je moznost udelat rosadu
	 * @param cX pocatecni pozice na ose x
	 * @param cY pocatecni pozice na ose y
	 * @param x konecni pozice na ose x
	 * @param y konecni pozice na ose y
	 * @param board pole figur
	 * @return
	 */
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

	public boolean isThisPlaceIsSave(int col, int row, Figure[][] board, Figure king) {
		if(board[row][col] == null) {
			board[row][col] = king;
			if(board[row][col].isUnderAttack(col, row, board)) {
				return false;
			}
		}
		return true;
	}

	public boolean hasMoves(double x, double y, Figure[][] board) {
		int[] xOffset = {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] yOffset = {-1, -1, -1, 0, 0, 1, 1, 1};

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				for (int i = 0; i < xOffset.length; i++) {
					int dx = (int)x + xOffset[i];
					int dy = (int)y + yOffset[i];
					Figure figure = board[row][col];
					if (dx >= 0 && dx < 8 && dy >= 0 && dy < 8) {
						if (figure != null && figure.getColor() != getColor() && figure.moveTo(figure.getCol(), figure.getRow(), dx, dy, board)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Animace pro vez
	 * @param endX
	 * @param endY
	 * @param cX
	 * @param board
	 */
	private void animation(double endX, double endY, double cX, Figure[][] board) {
		final double startX = board[(int)endY][(int)cX].getFigureX();
		final double distanceX = Math.abs(endX - startX);
		final double totalDistance = distanceX;
		final double stepSize = totalDistance / 10;
		final double xStep = distanceX / totalDistance * stepSize;
		final int directionX = endX > startX ? 1 : -1;
		timer = new Timer(15, new ActionListener() {
			double x = startX;
			double distanceCovered = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				x += xStep * directionX;
				board[(int)endY][(int)cX].setX(x);
				repaint();
				distanceCovered += stepSize;
				if (distanceCovered >= totalDistance) {
					board[(int)endY][(int)cX].setCol((int)endX);
					board[(int)endY][(int)endX] = board[(int)endY][(int)cX];
					board[(int)endY][(int)cX] = null;
					((Timer)e.getSource()).stop();
				}
			}
		});
		timer.start();
	}
}
