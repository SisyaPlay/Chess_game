package Figures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class DragHandler implements MouseListener, MouseMotionListener {
    private JPanel panel;
    private Point offset;

    public DragHandler(JPanel panel) {
        this.panel = panel;
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }

    public void mousePressed(MouseEvent e) {
        offset = e.getPoint();
    }

    public void mouseDragged(MouseEvent e) {
        Point current = e.getPoint();
        Point location = panel.getLocation();
        int x = location.x + current.x - offset.x;
        int y = location.y + current.y - offset.y;
        panel.setLocation(x, y);
    }

    public void mouseReleased(MouseEvent e) {
        // do nothing
    }

    public void mouseClicked(MouseEvent e) {
        // do nothing
    }

    public void mouseEntered(MouseEvent e) {
        // do nothing
    }

    public void mouseExited(MouseEvent e) {
        // do nothing
    }

    public void mouseMoved(MouseEvent e) {
        // do nothing
    }
}
