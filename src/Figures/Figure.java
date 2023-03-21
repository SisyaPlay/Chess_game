package Figures;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public abstract class Figure extends JPanel{

	protected int x;
	protected int y;

	private int currX;
	private int currY;
	protected ESide side;
	protected double square_size;

	private Point mousePressedLocation; // сохраняет начальное положение мыши при нажатии
	private Point mouseReleasedLocation;

	public Figure(ESide side, double square_size) {
		this.side = side;
		this.square_size = square_size;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int)square_size, (int)square_size));
		this.setLocation(currX, currY);

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
	}

	public void setSize(double square_size) {
		this.square_size = square_size;
	}


}


