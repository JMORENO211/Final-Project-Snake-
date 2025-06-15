/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Jonathan Moreno
 */
public class Turn {
    private final Random rand = new Random();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Player gets  one turn: prompts the player and checks against the host’s number that was randomly selected, then
     * The award prize is displayed, an update of the player’s balance is shown, and the print status is provided after the guess 
     *
     * @param player the current player
     * @param host   the host holding the target number
     * @return true if the player guessed correctly
     */
    public boolean takeTurn(Players player, Hosts host) {
        System.out.print(player.getName() + ", enter your guess: ");
        int guess = scanner.nextInt();

        boolean correct = host.checkGuess(guess);

        // chooses both the prize and money amount, not sure if I should do it differently 
        Award award = rand.nextBoolean()
            ? new Money(100, 250)   
            : new Physical();

        // displays the prize, updates balance, and shows the current status of the player's funds - this ties from earlier
        int delta = award.displayWinnings(player, correct);
        player.setMoney(player.getMoney() + delta);
        System.out.println(player);

        return correct;
    }
}