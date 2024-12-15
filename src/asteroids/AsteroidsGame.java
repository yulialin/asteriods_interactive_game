package asteroids;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.Random;

import asteroids.object.Asteroid;
import asteroids.object.Bullet;
import asteroids.object.Ship;
import asteroids.renderer.AbstractRenderer;

/**
 * The main game class for the Asteroids game. This class is responsible for
 * managing the game state and updating the game objects.
 */
public class AsteroidsGame {

    // The current score of the game
    private int score = 0;

    // The lives remaining in the game
    private int lives = 3;

    // Game objects in the game
    public Ship ship;
    private List<Asteroid> asteroids = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();

    private int initialShipX;
    private int initialShipY;

    private int gameWidth; 
    private int gameHeight; 

    private final int buffer = 50; //buffer distance beyond the area to spaw asteroids 
    private final Random rand = new Random(); 

    // Handlers for score updates
    private List<Consumer<Integer>> scoreUpdateHandlers = new ArrayList<>(1);

    // Handlers for lives remaining updates
    private List<Consumer<Integer>> livesUpdateHandlers = new ArrayList<>(1);

    /**
     * Constructs an animation and initializes it to be able to accept
     * key input.
     */
    public AsteroidsGame(Dimension window) {
        this.gameWidth = window.width;
        this.gameHeight = window.height; 
    
        this.initialShipX = window.width / 2;
        this.initialShipY = window.height / 2;

        reset();
    }

    private void reset() {
        ship = new Ship(initialShipX, initialShipY, 0);

        asteroids.clear();
        bullets.clear();

        addAsteroid(2);
    }

    public void shootBullet() {
        bullets.add(ship.shootBullet());
    }

    private final int totalMaxAsteroids = 10; //uplimit of active asteroids allowed at once

    /**
     * Adds a specified number of asteroids to the game at random locations outside the visible gameplay area.
     * This method randomly selects a side (top, right, bottom, or left) for each asteroid to appear from
     * and places the asteroid outside the game boundaries with a buffer. Each asteroid is given a
     * random direction of movement.
     *
     * @param count The number of asteroids to add. This is determined by other factors such as the score.
     */
    private void addAsteroid(int count) {
        //calculate the actual number of asteroids that can be added 
        int availableSpace = totalMaxAsteroids - asteroids.size();
        int asteroidsToAdd = Math.min(count, availableSpace); 

        if (asteroidsToAdd <= 0){
            return; //if there's no space left, then we exit this method 
        }

        for (int i = 0; i < asteroidsToAdd; i++) {
            float x, y;
            int side = rand.nextInt(4); // randomly pick a side (0=top, 1=right, 2=bottom, 3=left)
    
            switch (side) {
                case 0: // top
                    x = rand.nextFloat() * (gameWidth + 2 * buffer) - buffer;
                    y = -buffer;
                    break;
                case 1: // right
                    x = gameWidth + buffer;
                    y = rand.nextFloat() * (gameHeight + 2 * buffer) - buffer;
                    break;
                case 2: // bottom
                    x = rand.nextFloat() * (gameWidth + 2 * buffer) - buffer;
                    y = gameHeight + buffer;
                    break;
                case 3: // left
                    x = -buffer;
                    y = rand.nextFloat() * (gameHeight + 2 * buffer) - buffer;
                    break;
                default:
                    x = y = 0; // safeguard against uninitialized values
            }
    
            float direction = rand.nextFloat() * 360; // random direction of movement
            asteroids.add(new Asteroid(x, y, direction));
        }
    }
    

    protected void nextFrame(AbstractRenderer r) {
        if (lives > 0) {
            // Move the ship
            ship.nextFrame(r);

            // Remove bullets that have gone off screen
            bullets.removeIf(b -> b.isVisible == false);

            // Move the bullets and check for collisions

            for (int ai = 0; ai < asteroids.size(); ai++) {
                Asteroid asteroid = asteroids.get(ai);

                for (int bi = 0; bi < bullets.size(); bi++) {
                    Bullet bullet = bullets.get(bi);

                    if (bullet.collidesWith(asteroid)) {
                        increaseScore(100);

                        // Remove the asteroid and bullet
                        if (ai >= 0 && ai < asteroids.size()) {
                            asteroids.remove(ai--);
                            asteroid = null;
                        }
                        if (bi >= 0 && bi < bullets.size()) {
                            bullets.remove(bi--);
                        }

                        break;
                    } else {
                        bullet.nextFrame(r);
                    }
                }

                if (ship.collidesWith(asteroid)) {
                    decreaseLives();

                    // Remove the asteroid
                    if (ai >= 0 && ai < asteroids.size()) {
                        asteroids.remove(ai--);
                        asteroid = null;
                    }
                }

                if (asteroid != null)
                    asteroid.nextFrame(r);
            }
        } else {
            // Do nothing if the game is over
        }
    }

    /**
     * Increases the game score by a given amount and spawns asteroids based on the new score.
     * The number of asteroids spawned is calculated using the square root of the score divided by 100,
     * with a maximum value predefined by the game settings. The method also ensures that the
     * total number of asteroids does not exceed the uplimit set for the game.
     *
     * @param amount The amount to increase the score by. This increment can affect number of asteroids spawned.
     */
    private void increaseScore(int amount) {
        score += amount;

        int asteroidsToAdd = (int) Math.sqrt(score / 100);

        // maximum number of asteroids that can be added at once
        int maxAsteroidsToAdd = 2;  // adjust this value 

        //uplimit of active asteriods allowed at once
        int totalMaxAsteroids = 10; 

        // ensure do not exceed the maximum number of asteroids to add at once
        if (asteroidsToAdd > maxAsteroidsToAdd) {
            asteroidsToAdd = maxAsteroidsToAdd;
        }

        // ensure not to exceed the total maximum number of active asteroids
        if (asteroids.size() + asteroidsToAdd > totalMaxAsteroids) {
            asteroidsToAdd = totalMaxAsteroids - asteroids.size();  // reduce to fit the limit
        }

        // check if there's still room to add new asteroids
        if (asteroidsToAdd > 0) {
            addAsteroid(asteroidsToAdd);
        }

        // Call the score update handlers with the new score
        for (Consumer<Integer> handler : scoreUpdateHandlers) {
            handler.accept(score);
        }
    }

    private void decreaseLives() {
        lives--;

        System.out.println("decreaseLives " + lives);

        reset();

        // Call the lives update handlers with the new lives
        for (Consumer<Integer> handler : livesUpdateHandlers) {
            handler.accept(lives);
        }
    }

    public void addScoreUpdateHandler(Consumer<Integer> handler) {
        scoreUpdateHandlers.add(handler);
    }

    public void addLivesUpdateHandler(Consumer<Integer> handler) {
        livesUpdateHandlers.add(handler);
    }

    public void paint(Graphics2D g) {
        ship.paint(g);

        for (Asteroid asteroid : asteroids) {
            asteroid.paint(g);
        }

        for (Bullet bullet : bullets) {
            bullet.paint(g);
        }
    }
}
