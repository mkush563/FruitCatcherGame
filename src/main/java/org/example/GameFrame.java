package org.example;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private GamePanel gamePanel;

    public GameFrame() {
        gamePanel = new GamePanel(this);
        mainPanel.add(new MainMenuPanel(this), "menu");
        mainPanel.add(gamePanel, "game");

        this.add(mainPanel);
        this.setTitle("Fruit Catcher");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void showGamePanel() {
        gamePanel.resetGame();
        cardLayout.show(mainPanel, "game");
        gamePanel.requestFocusInWindow();
    }

    public void showMenu() {
        cardLayout.show(mainPanel, "menu");
    }
}
