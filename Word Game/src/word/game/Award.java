/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package word.game;

/**
 *
 * @author Jonathan Moreno
 */
public interface Award {
   /**
     * Displays the winnings (or loss) message.
     * @param player the player who is guessing at that time
     * @param correct true if guess was correctly guessed
     * @return integer amount change (positive for money-win, negative for money-loss, 0 for physical) 
     */ 
    int displayWinnings (Players player, boolean correct);
    
} 
