/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

/**
 *
 * @author Jonathan Moreno
 */
public class Players extends Person {
    private int money;
    
    // The constructor for the first name
    public Players(String firstName){
        super(firstName);
        this.money = 1000;
    }
    // The constructor for the Last name
    public Players(String firstName, String lastName){
        super(firstName, lastName);
        this.money = 1000;
    }
    
    // Getter and Setter
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " has $" + money;
    }
}
