package src;

import java.awt.*;

public class Item {

    // Attributes
    private int x;
    private int y;
    // Type "0" => Food
    private int type;


    // Constructor
    public Item(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFood() {
        return (type == 0);
    }

    public boolean isEnemy() {
        return (type == 1);
    }

    public void draw(Field field) {
        Graphics g = field.getGraphics();

        int xPos = x * field.getCellWidth() + 2;
        int yPos = y * field.getCellWidth() + 2;
        int size = field.getCellWidth() - 3;

        if (type == 0) {
            g.setColor(Color.blue);
        }
        else if (type == 1)    {
            g.setColor(Color.gray);
        } else {
            g.setColor(Color.black);
        }
        g.fillRect(xPos, yPos, size, size);
    }
}
