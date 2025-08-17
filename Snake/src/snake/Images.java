/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Jonathan Moreno
 */
public class Images {
    public static Image load(String resourcePath) {
        try {
            URL url = Images.class.getClassLoader().getResource(resourcePath);
            if (url == null) return null;
            return ImageIO.read(url);
        } catch (IOException ignored) { return null; }
    }
}
