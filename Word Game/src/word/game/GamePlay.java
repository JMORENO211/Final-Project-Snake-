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
    
    public static void main(String[] args) {
        // Should ask for the user's first name
        try (Scanner scanner = new Scanner(System.in)) {
            
            // Creates the host
            Hosts host = new Hosts("Bob Barker");
            Turn turn = new Turn();
            
            // Get the Players information
            System.out.print("Enter your first name ");
            String firstName = scanner.nextLine();
            
            System.out.print("Would you like to enter your last name? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            Players player;
            if (response.equals("yes")) {
                System.out.print("Enter your last name: ");
                String lastName = scanner.nextLine();
                player = new Players(firstName, lastName);
            } else {
                player = new Players(firstName);
            }
            
            
            // The Main game Loop
            boolean keepPlaying = true;
            while (keepPlaying) {
                host.randomizeNum();
                boolean guessCorrectly = false;
                
                // Loop until the correct guess is provided
                while (!guessCorrectly) {
                guessCorrectly = turn.takeTurn(player, host);
        }
          
                System.out.print("Do you want to play again? (yes/no): ");
                String again = scanner.next().trim().toLowerCase();
                keepPlaying = again.equals("yes");
            }
            
            System.out.println("Thanks for playing!");
        }
    }
}
