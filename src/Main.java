import Figures.*;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main {

	private static final int WIDTH = 1280; 
	private static final int HEIGTH = 720;



	
public static void main(String[] args) {

        JFrame frame = new JFrame();
        Pole pole = new Pole();

        frame.setTitle("Sachy");

        frame.setSize(new Dimension(WIDTH, HEIGTH));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGTH));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setLayout(new BorderLayout());


        frame.getContentPane().add(pole, BorderLayout.CENTER);

        frame.setVisible(true);
        }


}
