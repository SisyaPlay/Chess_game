import Figures.*;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class Main {

	private static final int WIDTH = 1280; 
	private static final int HEIGTH = 720;


	
	public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setTitle("Sachy");

        frame.setSize(new Dimension(WIDTH, HEIGTH));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGTH));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        Pole pole = new Pole(frame);
        pole.setBackground(SystemColor.BLUE);
        frame.getContentPane().add(pole);

        Knight knight =  new Knight((int)(100), (int)(100), ESide.BLACK, 80);
        //knight.setBackground(SystemColor.inactiveCaption);
        knight.setOpaque(false);
        pole.setLayout(new BorderLayout());
        pole.add(knight);



        frame.setVisible(true);
    }

}
