package Figures;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public abstract class Figure extends JPanel{

	protected int x;
	protected int y;

	protected ESide side;
	protected double square_size;


	public Figure(ESide side, double square_size) {
		this.side = side;
		this.square_size = square_size;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int)square_size, (int)square_size));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setSize(double square_size) {
		this.square_size = square_size;
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
