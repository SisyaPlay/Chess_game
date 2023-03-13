package Figures;

import javafx.embed.swing.JFXPanel;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class Figure extends JPanel  implements MouseListener, MouseMotionListener{

	protected int x;
	protected int y;
	protected ESide side;
	protected EFigure figure;

	protected double square_size;

	private int dragStartX;
	private int dragStartY;

	private boolean isDragging = false;
	private int deltaX, deltaY;

	public Figure(int x, int y, ESide side, EFigure figure, double square_size) {
		this.x = x;
		this.y = y;
		this.side = side;
		this.figure = figure;
		this.square_size = square_size;

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void mousePressed(MouseEvent e) {
		int cx = (int) (x + square_size / 2);
		int cy = (int) (y + square_size / 2);

		int mouseX = e.getX();
		int mouseY = e.getY();

		if (Math.abs(mouseX - cx) < square_size / 2 && Math.abs(mouseY - cy) < square_size / 2) {
			isDragging = true;
			deltaX = mouseX - cx;
			deltaY = mouseY - cy;
		}
	}

	// метод, который будет вызван при перетаскивании мыши с зажатой кнопкой
	@Override
	public void mouseDragged(MouseEvent e) {
		// вычисляем на сколько пикселей пользователь переместил мышь
		if (isDragging) {
			int mouseX = e.getX();
			int mouseY = e.getY();

			x = mouseX - deltaX - (int) (square_size / 2);
			y = mouseY - deltaY - (int) (square_size / 2);

			repaint();
		}
	}

	// остальные методы интерфейса MouseListener и MouseMotionListener не используются,
	// но их нужно реализовать, чтобы класс мог быть слушателем мыши

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		isDragging = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}


