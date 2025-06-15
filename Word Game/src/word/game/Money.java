/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

/**
 *
 * @author Jonathan Moreno
 */
public class Money implements Award {
    private final int winAmount;
    private final int loseAmount;

    public Money(int winAmount, int loseAmount) {
        this.winAmount = winAmount;
        this.loseAmount = loseAmount;
    }

    @Override
    public int displayWinnings(Players player, boolean correct) {
        if (correct) {
            System.out.println(player.getName() + " won $" + winAmount + "!");
            return winAmount;
        } else {
            System.out.println(player.getName() + " lost $" + loseAmount + ".");
            return -loseAmount;
        }
    }
}