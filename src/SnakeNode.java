package src;

public class SnakeNode {

    private SnakeNode nextNode;
    private SnakeCell item;


    public SnakeNode(SnakeCell item) {
        this.nextNode = null;
        this.item = item;
    }


    public SnakeCell getCell() {
        return item;
    }

    public SnakeNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(SnakeNode nextNode) {
        this.nextNode = nextNode;
    }
}

