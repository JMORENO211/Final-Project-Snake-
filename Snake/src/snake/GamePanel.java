/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
 /*
 * @author Jonathan Moreno
 */

/** Game loop + input. Uses a Thread (multithreading) and LinkedList inside Snake. */
public final class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int TILE = 24;
    public static final int COLS = 24;
    public static final int ROWS = 20;
    private static final int W = COLS * TILE, H = ROWS * TILE;

    private volatile boolean running = false, paused = false;
    private Thread loop;

    private final Random rng = new Random();
    private Snake snake;              // uses LinkedList
    private Food food;
    private int score = 0;

    private Directions queuedDir = Directions.RIGHT;

    private final java.util.function.IntConsumer scoreCb;
    private final java.util.function.IntConsumer gameOverCb;

    private final Sounds sound = new Sounds();
    private final Image appleImg = Images.load("resources/apple.png"); // IMAGES

    public GamePanel(java.util.function.IntConsumer scoreCb,
                     java.util.function.IntConsumer gameOverCb,
                     ScoreManager scoreMgr) {
        this.scoreCb = scoreCb; this.gameOverCb = gameOverCb;         setPreferredSize(new Dimension(W, H));
        setBackground(new Color(14, 16, 20));
        setFocusable(true);
        addKeyListener(this);
        reset();
    }

    public void start() {
        if (running) return;
        running = true; paused = false;
        loop = new Thread(this, "SnakeLoop"); // Multithreading
        loop.start();
        requestFocusInWindow();
    }

    public void reset() {
        running = false; paused = false;
        snake = new Snake(COLS/2, ROWS/2, 4); // overloaded constructor
        queuedDir = Directions.RIGHT;
        spawnFood();
        score = 0;
        repaint();
    }

    private void spawnFood() {
        int x, y;
        do {
            x = rng.nextInt(COLS);
            y = rng.nextInt(ROWS);
        } while (snake.occupies(x, y));
        food = new Food(x, y);
    }

    @Override public void run() {
        final long frameMs = 1000L / 10; // speed
        long last = System.currentTimeMillis();
        while (running) {
            long now = System.currentTimeMillis();
            if (!paused && now - last >= frameMs) {
                try {
                    update();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint();
                last = now;
            }
            try { Thread.sleep(2); } catch (InterruptedException ignored) {}
        }
    }

    private void update() throws UnsupportedAudioFileException {
        try {
            snake.setDirection(queuedDir);
            boolean grew = snake.step(COLS, ROWS, food); // may throw SelfCollisionException
            if (grew) {
                score += 10;
                scoreCb.accept(score);
                sound.play("resources/eat.wav"); // AUDIO
                spawnFood();
            }
        } catch (CollisionException e) {
            sound.play("resources/gameover.wav");
            running = false;
            gameOverCb.accept(score);
        } catch (WrongMoveException e) {
            // wont allow reserve to occur; ignore
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // grid background
        g2.setColor(new Color(24, 28, 36));
        for (int r = 0; r < ROWS; r++) for (int c = 0; c < COLS; c++)
            g2.drawRect(c*TILE, r*TILE, TILE, TILE);

        // food
        if (appleImg != null) {
            g2.drawImage(appleImg, food.x * TILE + 2, food.y * TILE + 2, TILE - 4, TILE - 4, null);
        } else {
            food.draw(g2); // fallback
        }

        // snake
        snake.draw(g2);

        // paused overlay
        if (paused) {
            g2.setColor(new Color(0,0,0,120));
            g2.fillRect(0,0,W,H);
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
            g2.drawString("PAUSED (P)", W/2 - 70, H/2);
        }

        g2.dispose();
    }

    // input
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> queuedDir = Directions.LEFT;
            case KeyEvent.VK_RIGHT -> queuedDir = Directions.RIGHT;
            case KeyEvent.VK_UP    -> queuedDir = Directions.UP;
            case KeyEvent.VK_DOWN  -> queuedDir = Directions.DOWN;
            case KeyEvent.VK_P     -> paused = !paused;
            case KeyEvent.VK_R     -> reset();
        }
    }
    @Override public void keyReleased(KeyEvent e) {}
}