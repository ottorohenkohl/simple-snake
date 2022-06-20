package src;

import java.awt.*;

public class SnakeCell {

    private int x;
    private int y;
    // type "1" => Head
    // type "0" => Body
    private int type;
    private boolean alive;


    public SnakeCell(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.alive = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public boolean isAlive()   {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void draw(Field field) {
        Graphics g = field.getGraphics();

        int xPos = x * field.getCellWidth() + 2;
        int yPos = y * field.getCellWidth() + 2;
        int size = field.getCellWidth() - 3;

        if (alive) {
            if (type == 1) {
                g.setColor(Color.decode("#32a852"));
            }
            else  {
                g.setColor(Color.decode("#28d156"));
            }
        }
        else    {
            if (type == 1) {
                g.setColor(Color.decode("#a84032"));
            }
            else  {
                g.setColor(Color.decode("#d12828"));
            }
        }
        g.fillRect(xPos, yPos, size, size);
    }
}
