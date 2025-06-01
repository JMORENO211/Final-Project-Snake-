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
    private static final int WIN_REWARD = 250;
    private static final int LOSS_PENALTY = 100;
    
    public boolean takeTurn(Players player, Hosts host) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(host.getFirstName() + " says: " + player.getFirstName() + ", enter your guess (0-100): ");
        int guess = scanner.nextInt();
        
        boolean correct = host.getNumbers().compareNumber(guess); // Numbers must be imported or in same package
        if(correct) {
            player.setMoney(player.getMoney() + WIN_REWARD);
            System.out.println("Congratulations!" + player + " is the winner!");
            return true;
        }else {
            player.setMoney(player.getMoney() - LOSS_PENALTY);
            System.out.println(player);
                    return false;
        }
        
    }
}
