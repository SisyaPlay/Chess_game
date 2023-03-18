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
        JPanel panel = new JPanel(new FlowLayout());

        frame.setTitle("Sachy");

        frame.setSize(new Dimension(WIDTH, HEIGTH));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGTH));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        Pole pole = new Pole(frame);
        pole.setBackground(SystemColor.BLUE);
        frame.getContentPane().add(pole);

        Knight knight = new Knight(ESide.BLACK, 160);
        Knight knight2 = new Knight(ESide.BLACK, 160);
        Pawns bpawn1 = new Pawns(ESide.BLACK, 160);
        Pawns bpawn2 = new Pawns(ESide.BLACK, 160);
        Pawns bpawn3 = new Pawns(ESide.BLACK, 160);
        Pawns bpawn4 = new Pawns(ESide.BLACK, 160);
        Pawns wpawn1 = new Pawns(ESide.WHITE, 160);
        Pawns wpawn2 = new Pawns(ESide.WHITE, 160);
        Pawns wpawn3 = new Pawns(ESide.WHITE, 160);
        Pawns wpawn4 = new Pawns(ESide.WHITE, 160);
        knight.setBackground(Color.RED);
        knight2.setBackground(Color.ORANGE);
        bpawn1.setBackground(Color.RED);
        wpawn1.setBackground(Color.ORANGE);
        bpawn2.setBackground(Color.RED);
        wpawn2.setBackground(Color.ORANGE);
        bpawn3.setBackground(Color.RED);
        wpawn3.setBackground(Color.ORANGE);
        wpawn4.setBackground(Color.RED);
        bpawn4.setBackground(Color.ORANGE);

        panel.add(bpawn1);
        panel.add(wpawn1);
        panel.add(bpawn2);
        panel.add(wpawn2);
        panel.add(bpawn3);
        panel.add(wpawn3);
        panel.add(bpawn4);
        panel.add(wpawn4);
        panel.add(knight);
        panel.add(knight2);


        frame.getContentPane().add(panel, BorderLayout.NORTH);


        frame.setVisible(true);
}

}
