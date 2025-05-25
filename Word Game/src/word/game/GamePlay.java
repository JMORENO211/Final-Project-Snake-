/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

import java.util.Scanner;

/**
 *
 * @author doom
 */
public class GamePlay {
    private static Person player;
    
    public static void main(String[] args) {
        // Should ask for the user's first name
        try (Scanner scanner = new Scanner(System.in)) {
            // Should ask for the user's first name
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();
            
            // Ask if the user would like to enter their last name
            System.out.print("Would you like to enter the last name? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("yes")) {
                System.out.print("Enter your last name: ");
                String lastName = scanner.nextLine();
                player = new Person(firstName, lastName);
            } else {
                player = new Person(firstName);
            }
            
            // Create Numbers object and generate random number
            Numbers gameNumbers = new Numbers();
            gameNumbers.generateNumber();
            
            // Start guessing loop
            boolean correct = false;
            while (!correct) {
                System.out.print(player.getFirstName() + ", enter your guess (0-100): ");
                int guess = scanner.nextInt();
                correct = gameNumbers.compareNumber(guess);
            }
        }
    }
}
