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
        // Should ask for the user's first name
        try (Scanner scanner = new Scanner(System.in)) {
            
            // Creates the host
            Hosts host = new Hosts("Bob Barker");
            Turn turn = new Turn();
            
            // Adds the new players
           for (int i = 0; i < currentPlayers.length; i++) {
               System.out.print("Enter player " + (i + 1) + " first name: ");
               String firstName = scanner.nextLine().trim();
               
               System.out.print("Would you like to enter player " + (i + 1) + " last name? (yes/no): ");
               String response = scanner.nextLine().trim().toLowerCase();
               
               if (response.equals("yes")) {
                   System.out.print("Enter last name: ");
                   String lastName = scanner.nextLine().trim();
                   currentPlayers[i] = new Players (firstName, lastName);
               } else {
                   currentPlayers[i] = new Players(firstName);
               }
           }
                    
            boolean keepPlaying = true;
            while (keepPlaying) {
                host.randomizeNum();
                boolean guessCorrectly = false;
                int playerIndex = 0;
                
                // Loops until the correct guess is provided by the player 
                while (!guessCorrectly) {
                    Players current = currentPlayers[playerIndex];
                    guessCorrectly = turn.takeTurn(current, host);
                    playerIndex = (playerIndex + 1) % currentPlayers.length;
        }
                System.out.print("Would you like to play again? (yes/no): ");
                String again = scanner.next().trim().toLowerCase();
                keepPlaying = again.equals("yes");
            }
            System.out.println("Thanks for playing contestant!");
        }
    }
}
