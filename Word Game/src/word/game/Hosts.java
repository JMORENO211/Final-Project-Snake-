/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;
/**
 *
 * @author Jonathan Moreno
 */
public class Hosts extends Person {
    private final Numbers numbers;

    public Hosts(String firstName) {
        super(firstName);
        this.numbers = new Numbers();
    }
    public void randomizeNum() {
        numbers.generateNumber();
    }
    public boolean checkGuess(int guess) {
        return numbers.compareNumber(guess);
    }
}
