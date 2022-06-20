package src;

import java.awt.*;

public class Field extends Canvas {

    // Attributes
    private Game game;
    private Item[] items;


    // Constructor
    public Field(Game game) {
        super.setBackground(Color.black);

        this.game = game;
        this.items = new Item[getCellNumber() * getCellNumber()];
    }

    public int getCellNumber() {
        return game.getConfig().getCellNumber();
    }

    public int getCellWidth() {
        return super.getWidth() / getCellNumber();
    }

    public Item getItemAt(int x, int y) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getX() == x && items[i].getY() == y) {
                return items[i];
            }
        }
        return null;
    }

    public void removeItemAt(int x, int y) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && x == items[i].getX() && y == items[i].getY()) {
                items[i] = null;
            }
        }
    }

    public boolean isOutside(int x, int y) {
        boolean lowerBorder = (x > getCellNumber() - 1 || y > getCellNumber() - 1);
        boolean higherBorder = (x < 0 || y < 0);
        return (lowerBorder || higherBorder);
    }

    // TODO: Think about some new name. That one's stupid...
    public void addItem(int type) {
        // Generating some random x and y coordinates that aren't already used
        int x;
        int y;
        outer:
        while (true) {
            x = (int) (Math.random() * (getCellNumber()));
            y = (int) (Math.random() * (getCellNumber()));
            if (getItemAt(x, y) == null) {
                break;
            }
            Snake clone = game.getSnake().clone();
            SnakeCell currCell = clone.dequeue();
            while (currCell != null) {
                if (x != currCell.getX() && y != currCell.getY()) {
                    break outer;
                }
                currCell = clone.dequeue();
            }
        }

        // Creating a new food item and adding it to the "items" list
        Item item = new Item(x, y, type);
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        // Painting the field itself
        g.setColor(Color.white);
        for (int i = 0; i <= getCellNumber(); i++) {
            int x = i * getCellWidth();
            int y = super.getHeight();
            g.drawLine(x, 0, x, y);
            g.drawLine(0, x, y, x);
        }

        // Painting the snake
        if (game.getSnake() != null)   {
            Snake clone = game.getSnake().clone();
            while (!clone.isEmpty()) {
                clone.dequeue().draw(this);
            }
        }

        // Painting the items
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                items[i].draw(this);
            }
        }
    }
}

