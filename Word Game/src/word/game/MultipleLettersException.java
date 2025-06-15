/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

/**
 *
 * @author Jonathan Moreno
 */
public class MultipleLettersException extends Exception {
        public MultipleLettersException() {
            super();
        }
        
        @Override
        public String getMessage() {
            return "More than one letter was entered";
        }
    }
    
