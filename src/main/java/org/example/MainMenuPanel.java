package org.example;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private Image backgroundImg;

    public MainMenuPanel(GameFrame frame) {

     backgroundImg = new ImageIcon(getClass().getClassLoader().getResource("assets/bg.png")).getImage();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // same as game bg


        JLabel title = new JLabel(" Fruit Catcher Game");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        title.setForeground(new Color(255, 255, 255)); // MediumSlateBlue
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startBtn = createButton("Start Game");
        JButton quitBtn = createButton("Quit");

        startBtn.addActionListener(e -> frame.showGamePanel());
        quitBtn.addActionListener(e -> System.exit(0));

        add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(50));
        add(startBtn);
        add(Box.createVerticalStrut(20));
        add(quitBtn);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(173, 216, 230)); // LightBlue
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2)); // CornflowerBlue
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }
}
