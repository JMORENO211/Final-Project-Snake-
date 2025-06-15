/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

import java.util.Scanner;

/**
 *
 * @author Jonathan Moreno
 */
public class GamePlay {
    private static final Players[] currentPlayers = new Players[3];

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 1) Creates host
            Hosts host = new Hosts("Bob Barker here and welcome to the word game!");

            // 2) Gather 3 players
            for (int i = 0; i < currentPlayers.length; i++) {
                System.out.print("Enter player " + (i + 1) + " first name: ");
                String firstName = scanner.nextLine().trim();

                System.out.print("Would you like to enter player " + (i + 1) + " last name? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("yes")) {
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine().trim();
                    currentPlayers[i] = new Players(firstName, lastName);
                } else {
                    currentPlayers[i] = new Players(firstName);
                }
            }

            // 3) Main game loop
             boolean keepPlaying = true;
            while (keepPlaying) {
                host.promptForPhrase();
                System.out.println("The Phrase to guess: " + Phrases.getPlayingPhrase() + "\n");

                Turn turn = new Turn();
                while (Phrases.getPlayingPhrase().contains("_")) {
                    for (Players current : currentPlayers) {
                        turn.takeTurn(current);
                        System.out.println("Current: " + Phrases.getPlayingPhrase() + "\n");
                        if (!Phrases.getPlayingPhrase().contains("_")) {
                            break;
                        }
                    }
                }

                System.out.println("Round complete!");
                System.out.print("Play again? (yes/no): ");
                String again = scanner.nextLine().trim().toLowerCase();
                keepPlaying = again.equals("yes");
                System.out.println();
            }

            System.out.println("Thanks for playing!");
        }
    }
}

            
            
          
