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
public class Turn {
    private final Scanner scanner = new Scanner(System.in);
    /**
     * We'll need to Prompt the player for a single-letter guess and reveal letters in the phrase.
     * Catches and handles MultipleLettersException and invalid input.
     *
     * @param player the current player taking their turn
     */
    public void takeTurn(Players player) {
        System.out.print(player.getName() + ", enter a letter: ");
        String input = scanner.nextLine().trim();

        try {
            Phrases.findLetters(input);
        } catch (MultipleLettersException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Please enter exactly one alphabetic letter.");
        }
    }
}