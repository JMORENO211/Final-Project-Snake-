/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Random; 
import java.net.URL;
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
    private final Random random = new Random(); // for animation

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
        
        JMenuItem attributionItem = new JMenuItem("Attribution");
        attributionItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Images and sounds:\n" +
            "- Trophy Image by HUNGQUACH679PNG from https://pixabay.com/illustrations/winner-cup-glory-medal-prize-7176296/\n" +
            "- Winner Bell Game Show by oldegarfrom https://freesound.org/people/oldedgar/sounds/97980/" + // 
            "- Wrong Answer by Andreas from https://freesound.org/people/-Andreas/sounds/648462/", // I did have to change it to a wav file as the mp3 was not supported 
            "Attribution",
            JOptionPane.INFORMATION_MESSAGE));

        aboutMenu.add(layoutInfoItem);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);

        // Step 7: Sets Top Panel for info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.add(playersLabel);
        infoPanel.add(hostLabel);
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

 private void playSound(String resourcePath) { // this was a challenge as I had to look up in stack over flow how to add as a classpath so it works in netbeans correctly 
    try {
        URL soundURL = getClass().getClassLoader().getResource(resourcePath);
        if (soundURL == null) {
            addMessage("Sound not found: " + resourcePath);
            return;
        }
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
        addMessage("Error playing sound: " + e.getMessage());
    }
}

    
private void showPrizeImage(String resourcePath) {
    URL imageUrl = getClass().getClassLoader().getResource(resourcePath);
    if (imageUrl == null) {
        addMessage("Image not found: " + resourcePath);
        return;
    }
    ImageIcon prizeImage = new ImageIcon(imageUrl);
    JLabel imageLabel = new JLabel(prizeImage);
    JOptionPane.showMessageDialog(this, imageLabel, "You won a prize!", JOptionPane.INFORMATION_MESSAGE);
}
    
    private class FloatingAnimationPanel extends JPanel implements ActionListener { 
        private int x = 0;
        private final Timer timer;

        public FloatingAnimationPanel() {
            setPreferredSize(new Dimension(350, 200));
            timer = new Timer(10, this);
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillOval(x, 50, 50, 50); // easy going circle
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            x += 2;
            if (x > getWidth()) {
                timer.stop();
                SwingUtilities.getWindowAncestor(this).dispose();
            }
            repaint();
        }
    }

    private void showFloatingAnimation() { 
        JFrame animFrame = new JFrame("Whammy Animation!");
        animFrame.setUndecorated(true);
        animFrame.add(new FloatingAnimationPanel());
        animFrame.pack();
        animFrame.setLocationRelativeTo(this);
        animFrame.setVisible(true);
    }
    
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

                if (correct) {
                    playSound("resources/winner.aiff");// did not change format as aiff is supported // section where I had to find how to load files via classpath 
                } else {
                    playSound("resources/buzzer.wav");// section where I had to find how to load files via classpath 

                    if (!random.nextBoolean()) {
                    } else {
                        showFloatingAnimation();
                    }
                }

                addMessage((correct ? "Correct!" : "Wrong!") + "\n" + p + "\nPhrase: " + Phrases.getPlayingPhrase());
                updatePhraseLabel();

                if (!Phrases.getPlayingPhrase().contains("_")) {
                    showPrizeImage("resources/trophy.png"); // section where I had to find how to load files via classpath 

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