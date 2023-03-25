package Figures;

import javax.swing.*;

import java.awt.*;



public abstract class Figure extends JPanel{

	protected int x;
	protected int y;
	protected int rel_x;
	protected int rel_y;
	protected ESide side;
	protected double square_size;


	public Figure(int x, int y, ESide side, double square_size) {
		this.rel_x = x;
		this.rel_y = y;
		this.side = side;
		this.square_size = square_size;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int)square_size, (int)square_size));
		DragHandler dh = new DragHandler(this);
		this.addMouseListener(dh);
		this.addMouseMotionListener(dh);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setSize(double square_size) {
		this.square_size = square_size;
	}
	public boolean isFigureHit(Point point) {
		setBounds(x, y, (int)square_size, (int)square_size);
		return this.contains(point);
	}

}


/*
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBounds(getX(), getY(), (int)square_size, (int)square_size);
				mousePressedLocation = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBounds(getX(), getY(), (int)square_size, (int)square_size);
				mouseReleasedLocation = e.getPoint();
				int deltaX = mouseReleasedLocation.x - mousePressedLocation.x;
				int deltaY = mouseReleasedLocation.y - mousePressedLocation.y;
				setLocation(getX() + deltaX, getY() + deltaY);
				currX = getX() + deltaX;
				currY = getY() + deltaY;
				System.out.println(currX);
			}

		});
 */
