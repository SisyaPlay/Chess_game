import View.ChessBoardView;
import View.GameView;

import javax.swing.*;
import java.awt.*;


/**
 * Tento projekt je hra sachy, zatim bez pravidl, ale s drag and drop funkci.
 * Trida Main spusti program a vytvori okno c sachovnice.
 * @author Alinzhan Mukanov
 * @version 26.03.2023
 */
public class Main {

    public static void main(String[] args) {
        GameView gameView = new GameView();
        gameView.createWindow(gameView);

    }
}
