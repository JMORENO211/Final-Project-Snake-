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
public class Numbers {
    private int randomNum;
    
    // Getter for the random number, randomNum
    public int getRandomNum() {
        return randomNum;
    }
    
    // Setter for the randomNum
    public void setRandomNum(int randomNum) {
        this.randomNum = randomNum;
    }
    
    // The method to generate the random Number between 0 and 100
    public void generateNumber() {
        Random rand = new Random();
        this.randomNum = rand.nextInt(101); // This is the 0 to 100 
    }
    
    // Next Method to compare a guess with the generated number
    public boolean compareNumber(int guess) {
        if (guess == randomNum) {
            System.out.println("Congratulations, you guess the number!");
            return true;
        } else if (guess > randomNum) {
            System.out.println("I'm sorry. That guess was too high.");
        } else {
            System.out.println("I'm sorry. That guess was too low.");
        }
        return false;
    }
    
}
