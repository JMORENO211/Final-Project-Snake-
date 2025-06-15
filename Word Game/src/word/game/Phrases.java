/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.game;

/**
 *
 * @author Jonathan Moreno
 */
public class Phrases {
     private static String gamePhrase;     
    private static String playingPhrase;  
    
    public static void setGamePhrase(String phrase) {
        gamePhrase = phrase;
        StringBuilder mask = new StringBuilder();
        for (char c : phrase.toCharArray()) {
            mask.append(c == ' ' ? ' ' : '_');
        }
        playingPhrase = mask.toString();
    }

    /**  
     * @return the current hidden phrase (underscores + revealed letters)
     */
    public static String getPlayingPhrase() {
        return playingPhrase;
    }

    /**
     * Reveals all the occurrences of the guessed letter in playingPhrase.
     *
     * @param letter a single-character String
     * @throws MultipleLettersException if input length != 1
     */
    public static void findLetters(String letter) throws MultipleLettersException {
        if (letter.length() != 1) {
            throw new MultipleLettersException();
        }
        char guess = Character.toLowerCase(letter.charAt(0));

        StringBuilder updated = new StringBuilder(playingPhrase);
        boolean foundAny = false;

        for (int i = 0; i < gamePhrase.length(); i++) {
            if (Character.toLowerCase(gamePhrase.charAt(i)) == guess) {
                updated.setCharAt(i, gamePhrase.charAt(i));
                foundAny = true;
            }
        }
        playingPhrase = updated.toString();

        if (!playingPhrase.contains("_")) {
            System.out.println("Congratulations! You’ve guessed the phrase correctly!:");
            System.out.println("   \"" + gamePhrase + "\"");
        } else if (!foundAny) {
            System.out.println("No \"" + letter + "\" in the phrase. Try again.");
        }
    }
}
