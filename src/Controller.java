package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    // Attributes
    private int x;
    private int y;


    // Constructor
    public Controller() {
        this.x = 1;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDirection(int keycode) {
        // x = -1 => LEFT
        // x = +1 => RIGHT
        // y = -1 => UP
        // y = +1 => DOWN
        switch (keycode) {
            case 37:
                x = -1;
                y = 0;
                break;
            case 38:
                x = 0;
                y = -1;
                break;
            case 39:
                x = 1;
                y = 0;
                break;
            case 40:
                x = 0;
                y = 1;
                break;
            default:
                break;
        }
    }

    // Only the keyPressed Event should be necessary for this kind of game
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        setDirection(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
