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

	private Point mouseOffset;

	public Figure(ESide side, double square_size) {
		this.side = side;
		this.square_size = square_size;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int)square_size, (int)square_size));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBounds(getX(), getY(), (int)square_size, (int)square_size);
				mouseOffset = e.getPoint();
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				x = e.getX() - mouseOffset.x;
				y = e.getY() - mouseOffset.y;
				System.out.println(getX() + " " + getY());
				setLocation(getX() + x, getY() + y);

			}
		});
	}

}


