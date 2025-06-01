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
    private Numbers numbers;
    
    public Hosts(String firstName) {
        super(firstName);
        numbers = new Numbers();
    }
    public void randomizeNum() {
        numbers.generateNumber();
    }
    
    public Numbers getNumbers() {
        return numbers;
    }
}
