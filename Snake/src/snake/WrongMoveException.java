/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

/**
 *
 * @author Jonathan Moreno
 */
/** Is thrown  when trying to move the snake in reverse  */
public class WrongMoveException extends Exception {
    public WrongMoveException(String msg) { super(msg); }
}