package asteroids.object;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Polygon;
import java.util.Random;

public class Asteroid extends GameObject {

    // The default color of the asteroid.
    private static final Color defaultColor = Color.white;

    // The speed of the asteroid.
    private float speed = 50;

    /**
     * Creates a new asteroid at the specified location with a random rotation.
     * 
     * @param x the x coordinate of the asteroid
     * @param y the y coordinate of the asteroid
     */
    public Asteroid(float x, float y) {
        this(x, y, (float) Math.random() * 360);
    }

    /**
     * Creates a new asteroid at the specified location with the specified rotation.
     * 
     * @param x        the x coordinate of the asteroid
     * @param y        the y coordinate of the asteroid
     * @param rotation the rotation of the asteroid
     */
    public Asteroid(float x, float y, float direction) {
        super(x, y, (float) Math.random() * 360, createAsteroidShape(), defaultColor);
        setVelocity(speed, direction);
    }

    /**
     * Creates a inconsistent irregular shape that is randomized for all asteriods 
     * 
     * @return randomized shapes that represent the asteroids
     */
    private static Shape createAsteroidShape() {
        //randomize different shapes 
        Random rand = new Random();

        int numPoints = 5 + rand.nextInt(6); //random pick between 5-10 pts

        int[] xPoints = new int[numPoints];
        int[] yPoints = new int[numPoints];

        double angleStep = Math.PI * 2 / numPoints; //angle between sides in radian

        for (int i = 0; i < numPoints; i++) {//coordiante

            double radius = 20 + rand.nextInt(20); //radius between 20-40
            xPoints[i] = (int) (radius * Math.cos(i * angleStep));
            yPoints[i] = (int) (radius * Math.sin(i * angleStep));
        }

        // Return a new polygon made from the arrays of the coordinate
        return new Polygon(xPoints, yPoints, numPoints);
    }
}
