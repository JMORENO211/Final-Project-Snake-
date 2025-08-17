/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;
import java.awt.*;

/**
 *
 * @author Jonathan Moreno
 */
public class Food extends Entity implements Drawable {
    public int x, y;
    public Food(int x, int y) { this.x = x; this.y = y; }
    @Override public void update(long nowMs) { }
    @Override public void draw(Graphics2D g2) {
        final int TILE = GamePanel.TILE;
        g2.setColor(new Color(255, 100, 100));
        g2.fillOval(x*TILE+4, y*TILE+4, TILE-8, TILE-8);
    }
}
