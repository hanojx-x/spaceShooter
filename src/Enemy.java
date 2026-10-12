import java.awt.*;

//blueprint for enemy object
public class Enemy {
    private int x, y;
    private int width = 20, height = 15;

    //constructor
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Moves enemies down, opposite of shot
    public void moveY(int directiony) {
        y += directiony;
    }

    //Draws enemy
    public void drawEnemy(Graphics g) {
        //Polygons in shape of a point, uses array for this
        int[] x_poly = {x, x - 10, x, x + 10};
        int[] y_poly = {y, y + 15, y + 10, y + 15};
        g.setColor(Color.GREEN);
        g.fillPolygon(x_poly, y_poly, 4);
    }

    //Used for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x - 10, y, 20, 15);
    }

    //returns y
    public int getY() {
        return y;
    }
}