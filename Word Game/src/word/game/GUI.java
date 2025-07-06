/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author Jonathan Moreno
 */

public class GUI extends JFrame {
    // Step 1: Setup for the data storage
    private final ArrayList<Players> playersList = new ArrayList<>();
    private Hosts host;

    // Step 2: GUI Components - implements the jframe added the Save Message option as per instructed
    private final JLabel playersLabel = new JLabel("Players: ");
    private final JLabel hostLabel = new JLabel("Host: ");
    private final JLabel phraseLabel = new JLabel("Phrase: ");
    private final JTextArea messageArea = new JTextArea(10, 40);
    private final JCheckBox saveMessagesCheckBox = new JCheckBox("Save Messages"); // added section

    // Step 3: Frame setup for the gui
    public GUI() {
        setTitle("Word Game");
        setSize(450, 350); // I opted to make it smaller the last one was too big
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Step 4: Creates the Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // Step 5: Game Menu (Alt+G)
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G); // Alt+G

        JMenuItem addPlayerItem = new JMenuItem("Add Player");
        addPlayerItem.addActionListener(e -> addPlayer());

        JMenuItem setHostItem = new JMenuItem("Set Host & Phrase");
        setHostItem.addActionListener(e -> setHostAndPhrase());

        gameMenu.add(addPlayerItem);
        gameMenu.add(setHostItem);
        menuBar.add(gameMenu);

        // Step 6: Adds the about Menu (Alt+A)
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic(KeyEvent.VK_A);

        JMenuItem layoutInfoItem = new JMenuItem("Layout");
        layoutInfoItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Designed for the ALT-G access",
            "Layout Information",
            JOptionPane.INFORMATION_MESSAGE));

        aboutMenu.add(layoutInfoItem);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);

        // Step 7: Sets Top Panel for info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.add(playersLabel);
        infoPanel.add(phraseLabel);

        // Step 8: Message display area with scroll bar
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Step 9: Bottom Panel with Start button + Save Messages checkbox
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> startGame());
        saveMessagesCheckBox.setToolTipText("If checked, new messages will be added to the list. If unchecked, each new message will replace the last.");
        bottomPanel.add(startGameButton);
        bottomPanel.add(saveMessagesCheckBox);

        // Step 10: Add panels to frame
        add(infoPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Add Player logic (was previously a button, now in Game menu)
    private void addPlayer() {
        String first = JOptionPane.showInputDialog("Please enter the player's first name:");
        if (first == null || first.isBlank()) return;
        int addLast = JOptionPane.showConfirmDialog(this, "Do you want to enter a last name?");
        Players player;
        if (addLast == JOptionPane.YES_OPTION) {
            String last = JOptionPane.showInputDialog("Enter player's last name:");
            player = new Players(first, last);
        } else {
            player = new Players(first);
        }
        playersList.add(player);
        updatePlayersLabel();
        addMessage("Player added: " + player.getName());
    }

    // Host & Phrase setup logic (was original a button option, now in Game menu)
    private void setHostAndPhrase() {
        String hostName = JOptionPane.showInputDialog("Enter the host name:");
        if (hostName == null || hostName.isBlank()) return;
        host = new Hosts(hostName);
        String phrase = JOptionPane.showInputDialog("Enter the game phrase:");
        if (phrase == null || phrase.isBlank()) return;
        Phrases.setGamePhrase(phrase);
        hostLabel.setText("Host: " + host.getName());
        updatePhraseLabel();
        addMessage("Set the host and phrase set.");
    }

    private void updatePlayersLabel() {
        StringBuilder sb = new StringBuilder("Players: ");
        for (Players p : playersList) {
            sb.append(p.getName()).append("  ");
        }
        playersLabel.setText(sb.toString());
    }

    private void updatePhraseLabel() {
        StringBuilder display = new StringBuilder("Set The Secret Phrase: ");
        for (char c : Phrases.getPlayingPhrase().toCharArray()) {
            display.append(c).append(" ");
        }
        phraseLabel.setText(display.toString());
    }

    // Message handling logic
    private void addMessage(String message) {
        if (saveMessagesCheckBox.isSelected()) {
            messageArea.append(message + "\n");
        } else {
            messageArea.setText(message + "\n");
        }
    }

    // Starts the Game logic
    private void startGame() {
        if (host == null || playersList.isEmpty()) {
            addMessage("Please set the host and add players first.");
            return;
        }
        Award award = new Money(450, 350);
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
                    addMessage((correct ? "Correct!" : "Wrong!") + "\n" + p + "\nPhrase: " + Phrases.getPlayingPhrase());
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
                    addMessage(e.getMessage());
                } catch (HeadlessException e) {
                    addMessage("Please enter only one letter at a time.");
                }
            }
        }
    }

    // Reset state for new game
    private void resetGame() {
        playersList.clear();
        host = null;
        Phrases.setGamePhrase("");
        playersLabel.setText("Players: ");
        hostLabel.setText("Host: ");
        phraseLabel.setText("Phrase: ");
        messageArea.setText(""); // clears any old messages
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}