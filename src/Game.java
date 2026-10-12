import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


//game class, game is an extension of the jpanel class and implements runnable and keylistener as objects
//INHERITANCE: Game is a subclass of the superclass JPanel
public class Game extends JPanel implements Runnable, KeyListener {
    //ENCAPSULATION: everything is private in order to protect variables
    //runs the game loop
    private Thread thread;
    //stores player object
    private SpaceCraft player;
    //stores shots in arraylist
    private ArrayList<Shot> shots;
    //stores enemies in arraylist
    private ArrayList<Enemy> enemies;

    //sets condition for moving left, right and getting a game over as false
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean gameOver = false;

    //sets score to 0
    private int score = 0;
    private int level = 1;

    //main game class
    public Game() {
        //set background colour to black
        setBackground(Color.BLACK);
        //lets main game be focusable, so you can actually input
        setFocusable(true);
        //Allows keyboard events
        addKeyListener(this);

        //creates player at (300, 500)
            player = new SpaceCraft(300, 500);
        //creates enemy and bullet storage
        shots = new ArrayList<>();
        enemies = new ArrayList<>();

        //starts the game loop
        thread = new Thread(this);
        thread.start();
    }

    //RUNTIME POLYMORPHISM: determines which method is called at which timea
    @Override
    //game loop
    public void run() {
        while (!gameOver) {
            // Move spacecraft
            if (moveLeft)
                player.move(-5, getWidth());
            if (moveRight)
                player.move(5, getWidth());

            // Move shots
            ArrayList<Shot> shotsToRemove = new ArrayList<>();
            //repeat for each shot instance in arrayList
            for (int i = 0; i < shots.size(); i++) {
                Shot s = shots.get(i);
                //move shot
                s.moveY(-5);
                //remove collided shot
                if (s.getY() < 0)
                    shotsToRemove.add(s);
            }
            //remove all collided shots
            shots.removeAll(shotsToRemove);

            // Move enemies
            //repeat for each enemy instance in arrayList
            ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);

                e.moveY(2 + score / 100);

                if (e.getY() > getHeight())
                    enemiesToRemove.add(e);
            }
            //remove all collided enemies
            enemies.removeAll(enemiesToRemove);

            // Collision detection
            //initializes arrayList for collided elements
            ArrayList<Shot> shotsToRemoveCollision = new ArrayList<>();
            ArrayList<Enemy> enemiesToRemoveCollision = new ArrayList<>();

            for (int i = 0; i < shots.size(); i++) {
                Shot s = shots.get(i);

                for (int j = 0; j < enemies.size(); j++) {
                    Enemy e = enemies.get(j);

                    if (s.getBounds().intersects(e.getBounds())) {

                        shotsToRemoveCollision.add(s);
                        enemiesToRemoveCollision.add(e);

                        score += 10;

                        if (score % 100 == 0)
                            level++;

                        SoundPlayer.playSound("sounds/scream.wav");

                        break; // prevents same shot hitting multiple enemies
                    }
                }
            }

            shots.removeAll(shotsToRemoveCollision);
            enemies.removeAll(enemiesToRemoveCollision);

            // Player collision with enemy
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (new Rectangle(player.getX() - 10, player.getY(), 20, 15).intersects(e.getBounds())) {
                    gameOver = true;
                }
            }

            // Spawn enemies randomly, 2% chance each frame
            if (((int)(Math.random() * 100) + 1) < 2 + score/50) {
                //More enemies are added as your score goes up
                enemies.add(new Enemy((int)(Math.random() * (getWidth() - 20)) + 11, 0));
            }

            //repaint everything
            repaint();
            //Pause, control game speed
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) { }
        }
        repaint();
    }

    @Override
    //draws everything
    public void paintComponent(Graphics g) {
        //clears old board
        super.paintComponent(g);

        // Draw spacecraft
        player.drawSpaceCraft(g);

        for (int i = 0; i < shots.size(); i++) {
            shots.get(i).drawShot(g);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).drawEnemy(g);
        }

        //Draw score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score + "  Level: " + level, 10, 20);

        // Game Over message
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 120, getHeight() / 2);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("You made it to level " + level + "!", getWidth() / 2 - 50, getHeight() / 2 + 50   );
        }
    }

    @Override
    //checks if key is held
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //checks left key
        if (key == KeyEvent.VK_LEFT)
            moveLeft = true;
        //checks right key
        else if (key == KeyEvent.VK_RIGHT)
            moveRight = true;
        //checks space key for shots
        else if (key == KeyEvent.VK_SPACE) {
            //prevents too many bullets from spawning
            if (shots.size() < 10) shots.add(player.generateShot());
        }
    }

    @Override
    //check if key is released
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        //checks left
        if (key == KeyEvent.VK_LEFT)
            moveLeft = false;
        //checks right
        else if (key == KeyEvent.VK_RIGHT)
            moveRight = false;
    }

    @Override
    //checks if key is typed. since we want to hold, it is blank
    public void keyTyped(KeyEvent e) {}

    //main method, finally!
    public static void main(String[] args) {
        //creates game panel
        JFrame frame = new JFrame("Space Shooter");
        Game gamePanel = new Game();
        frame.add(gamePanel);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}