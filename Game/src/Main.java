import View.ChessBoardView;
import View.GameView;

import javax.swing.*;
import java.awt.*;


/**
 * Tento projekt je hra sachy.
 * Trida Main spusti program a vytvori hru.
 * @author Alinzhan Mukanov
 * @version 07.05.2023
 */
public class Main {

    public static void main(String[] args) {
        GameView gameView = new GameView(); // Vytvoru hru, ktera vykresli okno a sachovnice
        gameView.createStartMenu(gameView); // Vytvori okno
    }
}
