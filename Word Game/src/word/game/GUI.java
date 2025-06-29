/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Jonathan Moreno
 */
public class GUI extends JFrame {
    // Step 1: setup for the Data Storage
    private ArrayList<Players> playersList = new ArrayList<>();
    private Hosts host;
    // Step 2: GUI Components - implements the jframe
    private JLabel playersLabel = new JLabel("Players: ");
    private JLabel hostLabel = new JLabel("Host: ");
    private JLabel phraseLabel = new JLabel("Phrase: ");

    public GUI() {
        setTitle("Word Game");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        // Step 3: Adds the player Button via the jbutton 
        JButton addPlayerButton;
        addPlayerButton = new JButton("Add a Player");
        addPlayerButton.addActionListener((var e) -> {
            String first = JOptionPane.showInputDialog("Please enter the player's first name:");
            if (first == null || first.isBlank()) return;
            int addLast = JOptionPane.showConfirmDialog(GUI.this, "Do you want to enter a last name?");
            Players player;
            if (addLast == JOptionPane.YES_OPTION) {
                String last = JOptionPane.showInputDialog("Enter player's last name:");
                player = new Players(first, last);
            } else {
                player = new Players(first);
            }   playersList.add(player);
            updatePlayersLabel();
        });
        // Step 4: Sets the Host Button
        JButton setHostButton = new JButton("Set the Host & Phrase");
        setHostButton.addActionListener(e -> {
            String hostName = JOptionPane.showInputDialog("Enter the host name:");
            if (hostName == null || hostName.isBlank()) return;
            host = new Hosts(hostName);
            String phrase = JOptionPane.showInputDialog("Enter the game phrase:");
            if (phrase == null || phrase.isBlank()) return;

            Phrases.setGamePhrase(phrase);
            hostLabel.setText("Host: " + host.getName());
            updatePhraseLabel();
        });
        // Step 5: Start the Game Button
        JButton startGameButton = new JButton("Press to Start Game");
        startGameButton.addActionListener(e -> startGame());
        // Adds the components to the jFrame
        add(playersLabel);
        add(addPlayerButton);
        add(hostLabel);
        add(setHostButton);
        add(phraseLabel);
        add(startGameButton);
        setVisible(true);
    }
    // Updates the player label
    private void updatePlayersLabel() {
        StringBuilder sb = new StringBuilder("Players: ");
        for (Players p : playersList) {
            sb.append(p.getName()).append("  ");
        }
        playersLabel.setText(sb.toString());
    }
    // Updates the phrase label
    private void updatePhraseLabel() {
        String display = "Set The Secret Phrase: ";
        for (char c : Phrases.getPlayingPhrase().toCharArray()) {
            display += c + " ";
        }
        phraseLabel.setText(display);
    }
    // The game logic
    private void startGame() {
        if (host == null || playersList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please set the host and add players first.");
            return;
        }
        Award award = new Money(600, 450); // or new Physical()
        while (Phrases.getPlayingPhrase().contains("_")) {
            for (var p : playersList) {
                String input = JOptionPane.showInputDialog(p.getName() + ", enter a letter:");
                if (input == null) return;
                try {
                    String before = Phrases.getPlayingPhrase();
                    Phrases.findLetters(input);
                    boolean correct = !Phrases.getPlayingPhrase().equals(before);
                    int moneyChange = award.displayWinnings(p, correct);
                    p.setMoney(moneyChange);
                    JOptionPane.showMessageDialog(this,
                        (correct ? "Correct!" : "Wrong!") +
                        "\n" + p +
                        "\nPhrase: " + Phrases.getPlayingPhrase());
                    updatePhraseLabel();
                    if (!Phrases.getPlayingPhrase().contains("_")) {
                        int again = JOptionPane.showConfirmDialog(this, p.getName() + " won!\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION);
                        if (again == JOptionPane.YES_OPTION) {
                            resetGame();
                            return;
                        } else {
                            System.exit(0);
                        }
                    }
                } catch (MultipleLettersException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(this, "Please enter only one letter at a time.");
                }
            }
        }
    }
    // Resets the game for the next round
    private void resetGame() {
        playersList.clear();
        host = null;
        Phrases.setGamePhrase("");
        playersLabel.setText("Players: ");
        hostLabel.setText("Host: ");
        phraseLabel.setText("Phrase: ");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}