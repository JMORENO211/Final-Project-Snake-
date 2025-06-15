/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

/**
 *
 * @author Jonathan Moreno
 */
public class Person {
    
    private String firstName;
    private String lastName;
    
    // overload Constructor for first Name
    public Person(String firstName) {
        this.firstName = firstName;
        this.lastName = "";
    }
    
    // overload Constructor with first and last name
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    //Getter for the first name
    public String getFirstName() {
        return firstName;
    }
    
    // The setter for the first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
       
    }
    
    // Getter for the last name
    public String getLastName() {
        return lastName;
    }
    
    // Setter for the last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return full name (first + optional last)
     */
    public String getName() {
        if (lastName == null || lastName.isBlank()) {
            return firstName;
        }
        return firstName + " " + lastName;
    }
}
