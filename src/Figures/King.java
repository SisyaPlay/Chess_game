package Figures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Trida Rook, vykresli krala
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class King extends Figure {
	private Timer animationTimer; // Casovac pro animace
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
	public boolean moveTo(double sX, double sY, double dX, double dY, Figure[][] board) {
		int deltaX = (int)Math.abs(getCol() - dX);
		int deltaY = (int)Math.abs(getRow() - dY);

		// Rosada
		if(doCastling(sX, sY, dX, dY, board)) {
			if(dX == 6) {
				animation(5, sY, 7, board);
				addCountOfMove();
				return true;
			} else if(dX == 2) {
				animation(3, sY, 0, board);
				addCountOfMove();
				return true;
			}
		}

		// Tah
		if (deltaX > 1 || deltaY > 1) {
			return false;
		}

		if (board[(int) dY][(int) dX] == null || board[(int) dY][(int) dX].getColor() != getColor()) {
			addCountOfMove();
			addHistory((int) sX, (int) sY, (int) dX, (int) dY);
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
	 *
	 * @param sX    pocatecni pozice na ose x
	 * @param sY    pocatecni pozice na ose y
	 * @param dX    konecni pozice na ose x
	 * @param dY    konecni pozice na ose y
	 * @param board pole figur
	 * @return
	 */
	public boolean doCastling(double sX, double sY, double dX, double dY, Figure[][] board) {
		if(this.countOfMove == 0) {
			if (board[(int) sY][(int) sX + 1] == null && board[(int) sY][(int) sX + 2] == null &&
					board[(int) sY][(int) sX + 3] instanceof Rook &&
					board[(int) sY][(int) sX + 3].getCountOfMove() == 0 &&
					!this.isUnderAttack(sX, sY, board)) {
				return true;
			}
			if (board[(int) sY][(int) sX - 1] == null && board[(int) sY][(int) sX - 2] == null &&
					board[(int) sY][(int) sX - 3] == null &&
					board[(int) sY][(int) sX - 4] instanceof Rook &&
					board[(int) sY][(int) sX - 4].getCountOfMove() == 0 &&
					!this.isUnderAttack(sX, sY, board)) {
				return true;
			}
		}
		return false;
	}

	public boolean isUnderAttack(double sX, double sY, Figure[][] board) {
		Figure king = board[(int) sY][(int) sX];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Figure figure = board[j][i];
				if (figure != null && figure.getColor() != getColor()) {
					if (king != null && figure.canEatKing(i, j, sX, sY, board)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isThisPlaceIsSafe(int dX, int dY, Figure[][] board, Figure king) {
		if (board[dY][dX] == null) {
			Figure prevFigure = board[king.getRow()][king.getCol()]; // сохраняем предыдущую фигуру на месте короля
			board[king.getRow()][king.getCol()] = null; // удаляем короля из предыдущего места
			board[dY][dX] = king; // перемещаем короля на новое место
			boolean isSafe = !king.isUnderAttack(dX, dY, board); // проверяем, является ли новое место безопасным
			board[dY][dX] = null; // удаляем короля с нового места
			board[king.getRow()][king.getCol()] = prevFigure; // восстанавливаем предыдущую фигуру на место короля
			return isSafe;
		}
		if(board[dY][dX] != null && !board[dY][dX].getColor().equals(getColor())) {
			Figure prevFigure = board[dY][dX];
			Figure prevKing = board[king.getRow()][king.getCol()];
			board[king.getRow()][king.getCol()] = null;
			board[dY][dX] = king;
			boolean isSafe = !king.isUnderAttack(dX, dY, board);
			board[dY][dX] = prevFigure;
			board[king.getRow()][king.getCol()] = prevKing;
			return isSafe;
		}
		return false;
	}

	public boolean hasMoves(double x, double y, Figure[][] board) {
		int[] xOffset = {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] yOffset = {-1, -1, -1, 0, 0, 1, 1, 1};

		for (int i = 0; i < xOffset.length; i++) {
			int dx = (int)x + xOffset[i];
			int dy = (int)y + yOffset[i];
			//Figure figure = board[row][col];
			if (dx >= 0 && dx < 8 && dy >= 0 && dy < 8) {
				if (isThisPlaceIsSafe(dx, dy, board, this)) {
					return true;
				}
			}
		}

		return false;
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
		animationTimer = new Timer(30, new ActionListener() {
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
		animationTimer.start();
	}
}
