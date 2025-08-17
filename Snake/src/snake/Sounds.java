/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

/**
 *
 * @author Jonathan Moreno
 */
import java.io.IOException;
import javax.sound.sampled.*;
import java.net.URL;

public class Sounds {
    public void play(String resourcePath) throws UnsupportedAudioFileException {
        try {
            URL url = getClass().getClassLoader().getResource(resourcePath);
            if (url == null) return;
            try (AudioInputStream in = AudioSystem.getAudioInputStream(url)) {
                Clip c = AudioSystem.getClip();
                c.open(in);
                c.start();
            }
        } catch (IOException | LineUnavailableException ignored) {}
    }
}
