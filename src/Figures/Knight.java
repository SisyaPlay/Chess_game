package Figures;

import java.awt.*;


/**
 * Trida Rook, vykresli jezdce
 * Dedi od spolecni a abtraktni tridy Figure
 */
public class Knight extends Figure{

	public Knight(double x, double y, Color color, double square_size) {
		super(x, y, color, square_size);
		this.type = EFigure.KNIGHT;
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double R = square_size;
		double x = square_size * this.getFigureX();
		double y = square_size * this.getFigureY();


		Graphics2D g2 = (Graphics2D)g;

		//USI
		g.setColor(color);
		g.fillOval((int)(x + square_size / 3), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.fillOval((int)((x + square_size / 3)  + 2 * (R / 10)), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));
		g.drawOval((int)((x + square_size / 3)  + 2 * (R / 10)), (int)(y + (R - square_size / 1.5) / 3), (int)R / 8, (int)(R - square_size / 1.5));

		//TELO
		g.setColor(color);
		g.fillRect((int)(x + square_size / 3), (int)(y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));
		g.setColor(Color.BLACK);
		g.drawRect((int)(x + square_size / 3), (int)(y + square_size / 4), (int)R / 3, (int)(R - square_size / 3));

		//HLAVA
		g2.rotate(Math.toRadians(45), (int)(x + square_size / 2), (int)(y + square_size / 3));
		g.setColor(color);
		g.fillOval((int)(x + square_size / 3) , (int)(y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g.setColor(Color.BLACK);
		g.drawOval((int)(x + square_size / 3) , (int)(y + square_size / 4), (int)R / 4, (int)(R - square_size / 2));
		g2.rotate(Math.toRadians(-45), (int)(x + square_size / 2), (int)(y + square_size / 3));
	}
	@Override
	public boolean moveTo(double cX, double cY, double x, double y, Figure[][] board) {
		int deltaX = (int)Math.abs(getCol() - x);
		int deltaY = (int)Math.abs(getRow() - y);

		// Проверяем, что конь двигается в форме буквы "L"
		if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {

			// Проверяем, что на клетке, на которую собирается перейти конь, нет фигур своего цвета
			if (board[(int)y][(int)x] != null && board[(int)y][(int)x].getColor().equals(getColor())) {
				return false;
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean canEatKing(double cX, double cY, double x, double y, Figure[][] board) {
		int deltaX = (int)Math.abs(getCol() - x);
		int deltaY = (int)Math.abs(getRow() - y);

		// Проверяем, что конь двигается в форме буквы "L"
		if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {

			// Проверяем, что на клетке, на которую собирается перейти конь, нет фигур своего цвета
			if (board[(int)y][(int)x] != null && (board[(int)y][(int)x].getColor() != getColor() && board[(int)y][(int)x] instanceof King)) {
				return true;
			}

			return false;
		}

		return false;
	}

	public boolean hasMoves(double x, double y, Figure[][] board) {
		// Check all possible knight moves
		int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
		int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
		for (int i = 0; i < 8; i++) {
			int nx = (int)x + dx[i];
			int ny = (int)y + dy[i];
			if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
				Figure figure = board[ny][nx];
				if (figure == null || figure.getColor() != getColor()) {
					return true;
				}
			}
		}
		return false;
	}
}
