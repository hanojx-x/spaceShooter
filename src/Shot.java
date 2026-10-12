import java.awt.*;

//blueprint for shot object
public class Shot {
    //initializes x and y coordinates
    private int x, y;
    //radius used for collision
    private int radius = 5;

    //constructor
    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //move shot forward towards enemy
    public void moveY(int directiony) {
        y += directiony;
    }

    //draw shot
    public void drawShot(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, radius, radius);
    }

    //call on rectangle variable to get radius
    public Rectangle getBounds() {
        return new Rectangle(x, y, radius, radius);
    }

    //returns y
    public int getY() {
        return y;
    }
}