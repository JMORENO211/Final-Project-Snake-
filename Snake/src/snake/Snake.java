/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;
import java.awt.*;
import java.util.LinkedList;

/**
 *
 * @author Jonathan Moreno
 */
public class Snake extends Entity implements Drawable {
    private final LinkedList<Point> body = new LinkedList<>();
    private Directions dir = Directions.RIGHT;
    private Directions lastDir = Directions.RIGHT; // used to block immediate reverse

    // Overloaded constructors
    public Snake(int startX, int startY) { this(startX, startY, 3); }

    public Snake(int startX, int startY, int length) {
        // Build from left to right so the head is the RIGHTMOST segment.
        // This prevents instant self-collision when initial direction is RIGHT.
        for (int i = length - 1; i >= 0; i--) {
            body.addLast(new Point(startX - i, startY));  // <-- key fix: addLast
        }
        // After this loop, body.getFirst() == (startX - (length-1), startY)
        // and body.getLast() == (startX, startY). We'll treat the LAST as head by the moving logic below.
        // To keep the rest of the code using "head = body.getFirst()", will nee dto rotate once so the real head is in the front first:
        // Moves the last to first so the rightmost cell is the head.
        body.addFirst(body.removeLast());
    }

    public void setDirection(Directions d) throws WrongMoveException {
        if (lastDir.opposite() == d) throw new WrongMoveException("Cannot reverse into yourself");
        this.dir = d;
    }

    /** Move one step; return true if food eaten (grow). May throw SelfCollisionException. */
    public boolean step(int cols, int rows, Food food) throws CollisionException {
        Point head = body.getFirst();            // head is always first
        Point next = new Point(head);

        switch (dir) {
            case LEFT  -> next.x--;
            case RIGHT -> next.x++;
            case UP    -> next.y--;
            case DOWN  -> next.y++;
        }

        // wall collision kills
        if (next.x < 0 || next.y < 0 || next.x >= cols || next.y >= rows) {
            throw new CollisionException("Wall hit");
        }
        // self collision kills
        if (body.contains(next)) throw new CollisionException("Bit yourself");

        // advance
        body.addFirst(next);
        lastDir = dir;

        // eat?
        if (next.equals(new Point(food.x, food.y))) {
            return true; // keep tail (grow)
        } else {
            body.removeLast();
            return false;
        }
    }

    public boolean occupies(int x, int y) { return body.contains(new Point(x, y)); }

    @Override public void update(long nowMs) { /* no-op for this game */ }

    @Override public void draw(Graphics2D g2) {
        final int TILE = GamePanel.TILE;
        int i = 0;
        for (Point p : body) {
            float alpha = (i==0) ? 1f : 0.85f;
            g2.setColor(new Color(90, 210, 120, (int)(alpha*255)));
            g2.fillRoundRect(p.x*TILE+2, p.y*TILE+2, TILE-4, TILE-4, 6, 6);
            i++;
        }
    }
}
