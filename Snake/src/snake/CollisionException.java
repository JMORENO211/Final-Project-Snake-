/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

/**
 *
 * @author Jonathan Moreno
 */
public class CollisionException extends Exception { // designed to prevent the snake from crashing into itself
    public CollisionException(String msg) { super(msg); }
}
