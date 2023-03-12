import Figures.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class Main extends JFrame{

	private static final int WIDTH = 1280; 
	private static final int HEIGTH = 720;


	
	public static void main(String[] args) {
		/*
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setTitle("Sachy");

        frame.setSize(new Dimension(WIDTH, HEIGTH));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGTH));
        //frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        Pole pole = new Pole(frame);
        Knight knight =  new Knight((int) (100), (int) 100, ESide.BLACK, 50);
        frame.add(pole);
       // frame.add(knight);

        frame.setVisible(true);
        */
        new Main();
    }

    public Main() {
        JButton btn1 = new JButton("Кнопка 1");
        JButton btn2 = new JButton("Кнопка 2");
        JButton btn3 = new JButton("Кнопка 3");

        JPanel panel = new JPanel();
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);

        this.add(panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setVisible(true);
    }
}
