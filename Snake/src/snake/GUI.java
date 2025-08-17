/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Jonathan Moreno
 */

public class GUI extends JFrame {
    private final ArrayList<String> players = new ArrayList<>();
    private final ScoreManager scores = new ScoreManager();

    private final JLabel playersLabel = new JLabel("Players: ");
    private final JLabel modeLabel    = new JLabel("Mode: Snake");
    private final JLabel scoreLabel   = new JLabel("Score: 0");

    private final JTextArea log = new JTextArea(12, 30);
    private final JCheckBox saveLog = new JCheckBox("Append log");
    private GamePanel panel;

    public GUI() {
        super("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        JMenuBar bar = new JMenuBar();
        JMenu game = new JMenu("Game"); game.setMnemonic(KeyEvent.VK_G);
        JMenuItem addPlayer = new JMenuItem("Add Player");
        addPlayer.addActionListener(e -> addPlayer());
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                """
                Snake rules:
                • Arrow keys move the snake after staring the game.
                • Eat the apples to grow while avoid the walls or yourself.
                • P pauses the game and  R restarts.""",
                "About the Snake game", JOptionPane.INFORMATION_MESSAGE));
        game.add(addPlayer); game.add(about); bar.add(game);
        setJMenuBar(bar);

        JPanel top = new JPanel(new GridLayout(3,1));
        top.add(playersLabel); top.add(modeLabel); top.add(scoreLabel);

        log.setEditable(false);
        JScrollPane sp = new JScrollPane(log);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton start = new JButton("Start");
        JButton reset = new JButton("Reset");
        start.setToolTipText("Starts the game");
        reset.setToolTipText("Resets the board");
        saveLog.setToolTipText("Append log lines"); // just i case its not working right 
        start.addActionListener(e -> { panel.start(); addMsg("Game has started."); });
        reset.addActionListener(e -> { panel.reset(); addMsg("Reset."); scoreLabel.setText("Score: 0"); });
        bottom.add(start); bottom.add(reset); bottom.add(saveLog);

        panel = new GamePanel(this::onScore, this::onGameOver, scores);
        add(top, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(sp, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        panel.requestFocusInWindow();
    }

    private void onScore(int s) { scoreLabel.setText("Score: " + s); }
    private void onGameOver(int finalScore) {
        addMsg("Game Over. Score: " + finalScore);
        String name = players.isEmpty()? "Player" : players.get(0);
        try { scores.saveScore(name, finalScore); } catch (IOException ex) { addMsg("Score save failed: " + ex.getMessage()); }
        int again = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Snake", JOptionPane.YES_NO_OPTION);
        if (again == JOptionPane.YES_OPTION) { panel.reset(); onScore(0); panel.start(); }
    }

    private void addPlayer() {
        String name = JOptionPane.showInputDialog(this, "Player name:");
        if (name == null || name.isBlank()) return;
        players.add(name.trim());
        StringBuilder sb = new StringBuilder("Players: ");
        for (String p: players) sb.append(p).append("  ");
        playersLabel.setText(sb.toString());
        addMsg("Added player: " + name.trim());
    }

    public void addMsg(String msg) {
        if (saveLog.isSelected()) log.append(msg + "\n");
        else log.setText(msg + "\n");
    }
}

