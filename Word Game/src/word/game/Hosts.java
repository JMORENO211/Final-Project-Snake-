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
public class Hosts extends Person {
    private final Scanner scanner = new Scanner(System.in);
    public Hosts(String firstName) {
        super(firstName);
    }
    public void promptForPhrase() {
        System.out.print(getName() + ", What phrase do you want to add to start the game: ");
        String phrase = scanner.nextLine().trim();
        Phrases.setGamePhrase(phrase);
        System.out.println("Phrase set! Let the games begin.");
    }
}
    
