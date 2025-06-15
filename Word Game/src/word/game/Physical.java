/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;
import java.util.Random;
/**
 *
 * @author Jonathan Moreno
 */
public class Physical implements Award {
    private static final String[] PRIZES = {
        "Apple Watch", "MacBook Pro", "65-inch TV", "100 dollar Gift Card", "250 dollar best guy card"
    };
    private final Random rand = new Random();

    private int getRandomPrizeIndex() {
        return rand.nextInt(PRIZES.length);
    }

    @Override
    public int displayWinnings(Players player, boolean correct) {
        String prize = PRIZES[getRandomPrizeIndex()];

        if (correct) {
            System.out.println(player.getName() + " won a " + prize + "!");
        } else {
            System.out.println(player.getName() + " lost. They could have won a " + prize + ".");
        }
        return 0;
    }
}
