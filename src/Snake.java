package src;

public class Snake {

    private SnakeNode head = null;
    private SnakeNode tail = null;


    public Snake() {
    }

    public void enqueue(SnakeCell item) {
        if (tail == null) {
            tail = new SnakeNode(item);
            head = tail;
        } else {
            SnakeNode newNode = new SnakeNode(item);
            tail.setNextNode(newNode);
            tail = newNode;
        }
    }

    public SnakeCell dequeue() {
        if (head == null) {
            return null;
        }
        SnakeNode temp = head;
        head = head.getNextNode();
        return temp.getCell();
    }

    public SnakeCell head() {
        return head.getCell();
    }

    public SnakeCell tail() {
        return tail.getCell();
    }

    public void move(int x, int y) {
        moveRecursive(head);
        head.getCell().setX(head.getCell().getX() + x);
        head.getCell().setY(head.getCell().getY() + y);
    }

    private void moveRecursive(SnakeNode node) {
        if (node.getNextNode().getNextNode() != null) {
            moveRecursive(node.getNextNode());
        }
        node.getNextNode().getCell().setX(node.getCell().getX());
        node.getNextNode().getCell().setY(node.getCell().getY());
    }

    public Snake clone() {
        Snake snake = new Snake();
        SnakeNode currNode = head;
        while (currNode != null) {
            int x = currNode.getCell().getX();
            int y = currNode.getCell().getY();
            int type = currNode.getCell().getType();
            boolean alive = currNode.getCell().isAlive();
            SnakeCell cell = new SnakeCell(x, y, type);
            cell.setAlive(alive);
            snake.enqueue(cell);
            currNode = currNode.getNextNode();
        }
        return snake;
    }

    public void die()   {
        SnakeNode currNode = head;
        while (currNode != null) {
            currNode.getCell().setAlive(false);
            currNode = currNode.getNextNode();
        }
    }

    public boolean isEmpty() {
        return (head == null);
    }
}

