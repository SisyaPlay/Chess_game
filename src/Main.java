

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	private static final int WIDTH = 1280; 
	private static final int HEIGTH = 720; 
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
        
        // Titulek okna
        frame.setTitle("Sachy");
        
        // Vychozi velikost okna v pixelech
        // (v budoucnu nebudeme potrebovat)
        frame.setSize(new Dimension(WIDTH, HEIGTH));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGTH));
        //frame.setResizable(false);
        
        // Se zavrenim okna se ukonci aplikace; standardne se
        // pouze okno skryje
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Umisti okno doprostred obrazovky
        frame.setLocationRelativeTo(null);    
        
        Pole pole = new Pole(frame);
        
        frame.add(pole);
        // Zobrazi okno
        frame.setVisible(true);  

	}
}
