package src;

public class Config {

    private int speed;
    private int cellNumber;
    private int enemySpawnRate;


    public Config() {
        this.speed = 6;
        this.cellNumber = 20;
        this.enemySpawnRate = 3;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(int cellNumber) {
        this.cellNumber = cellNumber;
    }

    public int getEnemySpawnRate() {
        return enemySpawnRate;
    }

    public void setEnemySpawnRate(int enemySpawnRate) {
        this.enemySpawnRate = enemySpawnRate;
    }
}
