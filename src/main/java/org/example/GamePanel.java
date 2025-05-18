package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Image fruitImg;
    private Image bombImg;

    private int basketX = 200;
    private final int BASKET_WIDTH = 60, BASKET_HEIGHT = 30;
    private final int PANEL_WIDTH = 500, PANEL_HEIGHT = 600;
    private int score = 0;
    private int dropSpeed = 5;
    private int level = 1;private int highScore = 0;
    private final String HIGH_SCORE_FILE = "data/highscore.txt";

    private GameFrame parent;
    private final Color BACKGROUND_COLOR = new Color(240, 248, 255);  // AliceBlue
    private final Color BASKET_COLOR = new Color(255, 165, 0);         // Orange
    private final Color FRUIT_COLOR = new Color(255, 69, 0);           // Tomato
    private final Color BOMB_COLOR = new Color(30, 30, 30);            // Dark gray
    private final Color TEXT_COLOR = new Color(25, 25, 112);           // Midnight Blue





    private class Drop {
        int x, y;
        boolean isFruit;

        Drop(int x, boolean isFruit) {
            this.x = x;
            this.y = 0;
            this.isFruit = isFruit;
        }

        void fall() {
            y += dropSpeed;
        }

    }

    private ArrayList<Drop> drops;
    private Random rand = new Random();

    public GamePanel(GameFrame frame) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(this);
        drops = new ArrayList<>();
        this.parent = frame;
        fruitImg = new ImageIcon(getClass().getClassLoader().getResource("assets/apple.png")).getImage();
        bombImg = new ImageIcon(getClass().getClassLoader().getResource("assets/bomb.png")).getImage();

        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add new drop
        if (rand.nextInt(15) == 0) {
            boolean isFruit = rand.nextInt(5) != 0; // 80%  fruit
            drops.add(new Drop(rand.nextInt(PANEL_WIDTH - 20), isFruit));
        }

        // Update drops
        Iterator<Drop> it = drops.iterator();
        while (it.hasNext()) {
            Drop d = it.next();
            d.fall();
// Increase difficulty every 10 points
            if (score > 0 && score % 10 == 0) {
                level = score / 10 + 1;
                dropSpeed = 5 + level;  // increase speed
            }

            if (d.y > PANEL_HEIGHT) {
                it.remove();
            } else if (d.y + 20 > PANEL_HEIGHT - BASKET_HEIGHT &&
                    d.x > basketX && d.x < basketX + BASKET_WIDTH) {
                if (d.isFruit) {
                    score++;
                    SoundManager.playSound("catch.wav");

                } else {
                    SoundManager.playSound("explode.wav");
                    timer.stop();

                    int choice = JOptionPane.showOptionDialog(this,
                            "Game Over. Your score: " + score,
                            "Game Over",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[]{"Restart", "Menu"},
                            "Restart"
                    );
                    if (choice == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        parent.showMenu();
                    }
                    timer.stop();

                }
                it.remove();
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw basket
        Image bg = new ImageIcon(getClass().getClassLoader().getResource("assets/bg.png")).getImage();

        g.drawImage(bg, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);

        this.setBackground(BACKGROUND_COLOR);
        g.setColor(BASKET_COLOR);
        Image basketImg = new ImageIcon(getClass().getClassLoader().getResource("assets/basket.png")).getImage();
        g.drawImage(basketImg, basketX, PANEL_HEIGHT - BASKET_HEIGHT, BASKET_WIDTH, BASKET_HEIGHT, null);


        Image fruitImg = new ImageIcon(getClass().getClassLoader().getResource("assets/apple.png")).getImage();
        Image bombImg = new ImageIcon(getClass().getClassLoader().getResource("assets/bomb.png")).getImage();

        for (Drop d : drops) {
            if (d.isFruit) {
                g.drawImage(fruitImg, d.x, d.y, 20, 20, null);
            } else {
                g.drawImage(bombImg, d.x, d.y, 20, 20, null);
            }
        }


        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("High Score: " + highScore, 180, 30);
        g.drawString("Level: " + level, 400, 30);

    }
    public void resetGame() {
        score = 0;
        level = 1;
        dropSpeed = 5;
        drops.clear();
        timer.start();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int speed = 20;
        if (key == KeyEvent.VK_LEFT && basketX > 0) basketX -= speed;
        if (key == KeyEvent.VK_RIGHT && basketX < PANEL_WIDTH - BASKET_WIDTH) basketX += speed;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
