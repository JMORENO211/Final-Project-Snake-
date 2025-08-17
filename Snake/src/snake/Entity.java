/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

import java.awt.Graphics2D;
/**
 *
 * @author Jonathan Moreno
 */
public abstract class Entity {
    public abstract void update(long nowMs);
    public void draw(Graphics2D g2) { }
}
