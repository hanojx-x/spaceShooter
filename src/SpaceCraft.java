import java.awt.*;

//blueprint for spacecraft object
public class SpaceCraft {
    //initialize position and size variables
    private int x, y;
    private int width = 20, height = 10;

    //constructor
    public SpaceCraft(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //ENCAPSULATION: get x and y values from game and set the x and y to those
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    //draws spacecraft
    //POLYMORPHISM: each draw class has their own drawing method
    public void drawSpaceCraft(Graphics g) {
        //4 points are drawn in the shape of an arrow
        int[] xshape = {x, x - 10, x, x + 10};
        int[] yshape = {y, y + 15, y + 10, y + 15};
        g.setColor(Color.RED);
        g.fillPolygon(xshape, yshape, 4);
    }

    // Moves horizontally with boundary check
    public void move(int directionx, int panelWidth) {
        //makes sure ship stays in the boundaries
        x += directionx;
        if (x < 0){
            x = 0;
        }
        if (x > panelWidth - width){
            x = panelWidth - width;
        }
    }

    //calls on shot variable in the Shot object, sets initial position to spacecraft position
    public Shot generateShot() {
        return new Shot(x, y);
    }



}
