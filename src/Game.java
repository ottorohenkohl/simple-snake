package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Game extends JFrame {

    // Attributes
    private Controller controller;
    private Container container;
    private Field field;
    private Snake snake;
    private Timer timer;
    private Config config;
    private int score;

    // AWT-Elements
    private JButton btnStart;
    private JButton btnPlayPause;
    private JButton btnReset;
    private JButton btnSaveCfg;
    private JTextArea txtSpeed;
    private JTextArea txtCellNumber;
    private JTextArea txtEnemySpawnRate;
    private JLabel lblScore;


    // Constructor
    public Game() {
        super();

        config = new Config();
        controller = new Controller();
        score = 0;

        initUI();

        super.setTitle("Snake");
        super.setSize(1300, 900);
        super.setResizable(false);
        super.setLayout(null);
        super.setVisible(true);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.addKeyListener(controller);
    }

    public static void main(String[] args) {
        new Game();
    }

    public void initUI() {
        field = new Field(this);
        field.setBounds(450, 35, 800, 800);

        JLabel title = new JLabel();
        title.setText("Snake - by Otto Rohenkohl");
        title.setBounds(50, 35, 400, 50);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblSpeed = new JLabel();
        lblSpeed.setText("Geschwindigkeit (1-10):");
        lblSpeed.setBounds(50, 150, 250, 20);

        JLabel lblNumberCells = new JLabel();
        lblNumberCells.setText("Anzahl an Zellen pro Reihe: (5-30):");
        lblNumberCells.setBounds(50, 215, 250, 20);

        JLabel lblEnemySpawnRate = new JLabel();
        lblEnemySpawnRate.setText("Spawnrate der Gegner: (0-100):");
        lblEnemySpawnRate.setBounds(50, 280, 250, 20);

        txtSpeed = new JTextArea();
        txtSpeed.setBounds(50, 175, 200, 20);
        txtSpeed.setText("" + config.getSpeed());

        txtCellNumber = new JTextArea();
        txtCellNumber.setBounds(50, 240, 200, 20);
        txtCellNumber.setText("" + config.getCellNumber());

        txtEnemySpawnRate = new JTextArea();
        txtEnemySpawnRate.setBounds(50, 305, 200, 20);
        txtEnemySpawnRate.setText("" + config.getEnemySpawnRate());

        btnSaveCfg = new JButton();
        btnSaveCfg.setBounds(50, 360, 200, 30);
        btnSaveCfg.setText("Konfiguration speichern");
        btnSaveCfg.addActionListener((ActionEvent evt) -> {
            int speed = Integer.parseInt(txtSpeed.getText());
            if (speed >= 1 && speed <= 10) {
                config.setSpeed(speed);
            }
            int cellNumber = Integer.parseInt(txtCellNumber.getText());
            if (cellNumber >= 5 && cellNumber <= 30) {
                config.setCellNumber(cellNumber);
            }
            int enemySpawnRate = Integer.parseInt(txtEnemySpawnRate.getText());
            if (enemySpawnRate >= 0 && cellNumber <= 100) {
                config.setEnemySpawnRate(enemySpawnRate);
            }
        });

        lblScore = new JLabel();
        lblScore.setText("Aktueller Score: " + score);
        lblScore.setBounds(50, 450, 250, 20);

        btnStart = new JButton();
        btnStart.setBounds(50, 500, 200, 30);
        btnStart.setText("start");
        btnStart.addActionListener((ActionEvent evt) -> {
            reset();
            create();
        });

        btnPlayPause = new JButton();
        btnPlayPause.setBounds(50, 540, 200, 30);
        btnPlayPause.setText("pausieren");
        btnPlayPause.addActionListener((ActionEvent evt) -> {
            if (btnPlayPause.getText() == "pausieren") {
                timer.stop();
                btnPlayPause.setText("weiter");
            }
            else {
                timer.start();
                btnPlayPause.setText("pausieren");
            }
        });

        btnReset = new JButton();
        btnReset.setBounds(50, 580, 200, 30);
        btnReset.setText("zurücksetzen");
        btnReset.addActionListener((ActionEvent evt) -> {
            reset();
        });

        container = super.getContentPane();
        container.add(field);
        container.add(title);
        container.add(lblSpeed);
        container.add(lblNumberCells);
        container.add(lblEnemySpawnRate);
        container.add(txtSpeed);
        container.add(txtCellNumber);
        container.add(txtEnemySpawnRate);
        container.add(lblScore);
        container.add(btnSaveCfg);
        container.add(btnStart);
        container.add(btnPlayPause);
        container.add(btnReset);
    }

    private void create() {
        createSnake();
        field.addItem(0);
        play();
    }

    private void end() {
        if (timer != null && snake != null && field != null) {
            timer.stop();
            snake.die();
            field.repaint();
        }
    }

    private void play()    {
        timer = new Timer((11 - config.getSpeed()) * 50, (ActionEvent evt) -> {
            super.requestFocus();

            if ((int) (Math.random() * 100) < config.getEnemySpawnRate())  {
                field.addItem(1);
            }

            if (isValidMove(controller.getX(), controller.getX())) {
                Item item = field.getItemAt(snake.head().getX() + controller.getX(), snake.head().getY() + controller.getY());
                if (item != null && item.isFood()) {
                    SnakeCell cell = new SnakeCell(snake.tail().getX() - 1, snake.tail().getY(), 0);
                    snake.enqueue(cell);
                    field.removeItemAt(item.getX(), item.getY());
                    field.addItem(0);
                    score++;
                    lblScore.setText("Aktueller Score: " + score);
                }
                snake.move(controller.getX(), controller.getY());
                // TODO: Think about Buffering or stuff like that. The performance is rather mehh!
                field.repaint();
            } else {
                end();
            }
        });
        timer.start();
    }

    private void reset() {
        end();
        snake = null;
        for (int i = 0; i < field.getCellNumber(); i++) {
            for (int j = 0; j < field.getCellNumber(); j++) {
                field.removeItemAt(i, j);
            }
        }
        score = 0;
        controller.setDirection(39);
    }

    public Snake getSnake() {
        return snake;
    }

    public Config getConfig() {
        return config;
    }

    public void createSnake() {
        int xPos = 2 + (int) (Math.random() * (field.getCellNumber() - 4));
        int yPos = 2 + (int) (Math.random() * (field.getCellNumber() - 4));

        // TODO: Don't know what to think about that...
        SnakeCell head = new SnakeCell(xPos, yPos, 1);
        SnakeCell body = new SnakeCell(xPos - 1, yPos, 0);
        SnakeCell tail = new SnakeCell(xPos - 2, yPos, 0);

        snake = new Snake();
        snake.enqueue(head);
        snake.enqueue(body);
        snake.enqueue(tail);
    }

    public boolean isValidMove(int x, int y) {
        int newX = snake.head().getX() + controller.getX();
        int newY = snake.head().getY() + controller.getY();
        if (field.isOutside(newX, newY)) {
            return false;
        }

        Snake clone = snake.clone();
        clone.move(x, y);
        clone.dequeue();
        SnakeCell currCell = clone.dequeue();
        while (currCell != null) {
            if (newX == currCell.getX() && newY == currCell.getY()) {
                return false;
            }
            currCell = clone.dequeue();
        }

        Item item = field.getItemAt(newX, newY);
        if (item != null && item.isEnemy()) {
            return false;
        }

        return true;
    }
}

