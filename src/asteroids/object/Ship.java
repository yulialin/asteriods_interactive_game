package asteroids.object;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

import asteroids.renderer.AbstractRenderer;

public class Ship extends GameObject {

    // The default shape of the ship. (makes an A shaped ship)
    private static final Shape defaultShape = new Polygon(
            new int[] { -10, -10, 0, 10, 10, 10, -10 },
            new int[] { 27, 20, -15, 20, 27, 20, 20 },
            7);

    // the shape of the ship when the thruster is on. (adds a V shaped flame)
    private static final Shape thrustShape = new Polygon(
            new int[] { -10, -10, 0, 10, 10, 10, -5, 0, 5, -10 },
            new int[] { 27, 20, -15, 20, 27, 20, 20, 35, 20, 20 },
            10);

    // The default color of the ship.
    private static final Color defaultColor = Color.white;

    // The maximum speed of the ship when thrust is enabled.
    private final float thrustSpeed = 150;

    // The default rotation speed of the ship.
    private final float rotationSpeed = 150;

    // The rate of acceleration and deceleration of the ship.
    private final float accelerationRate = 2.0f;
    private final float decelerationRate = 0.5f;

    // Timer to reset the visual effect of the thrust action.
    // Also used to indicate if thrust is active. null if thrust is not active.
    private Timer thrustTimer = null;

    // Timer to limit the duration of the hyperspace action to 3 seconds.
    // Also used to indicate if hyperspace is active. null if hyperspace is not active.
    private Timer hyperspaceTimer = null;

    /**
     * Creates a new ship at specified location with specified rotation.
     *
     * @param x                the x-coordinate of the ship's location
     * @param y                the y-coordinate of the ship's location
     * @param rotation         the rotation of the ship
     */
    public Ship(float x, float y, float rotation) {
        super(x, y, rotation, defaultShape, defaultColor);
    }

    /**
     * Enable the thrust action. This accelerates the ship in the direction 
     * it is facing and shows the thrust visual effect.
     */
    public void enableThrust() {
        // If the thrust is already on, cancel the timer and restart it.
        if (thrustTimer != null)
            thrustTimer.stop();

        // Show the thrust visual effect for 50 milliseconds.
        thrustTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thrustTimer = null;
            }
        });

        try {
            thrustTimer.start();
        } catch (NullPointerException e) {
            // thrustTimer was set to null before the timer could start
            // Start the timer again
            enableThrust();
        }
    }

    public void moveBackward() {
        setVelocity(-thrustSpeed, getRotation());
    }

    public void rotateLeft(int framesPerSecond) {
        rotate(-rotationSpeed / framesPerSecond);
    }

    public void rotateRight(int framesPerSecond) {
        rotate(rotationSpeed / framesPerSecond);
    }

    /**
     * Overrides the getShape() function inherited from GameObject class.
     * Sets the shape to the defaultShape, unless the thrust/hyperspace
     * are enabled. If thrust/hyperspace are enabled, then the shape is
     * set to thrustShape.
     */
    @Override
    public Shape getShape() {
        AffineTransform at = new AffineTransform();

        // Move the shape to the current position
        at.translate(getX(), getY());

        // Rotate the shape to the current rotation
        at.rotate(Math.toRadians(getRotation()));

        // Set the shape to the default shape if the thrust and hyperspace is not active
        Shape shipShape = thrustTimer == null && hyperspaceTimer == null
                ? defaultShape
                : thrustShape;

        return at.createTransformedShape(shipShape);
    }

    /**
     * Enable the hyperspace action. This sets the ship to move at double
     * of thrust speed for 3 seconds.
     */
    public void enterHyperspace() {
        // Do nothing if hyperspace is already active
        if (hyperspaceTimer != null)
            return;

        // Double the speed for 3 seconds, then return to normal speed
        setVelocity(thrustSpeed * 2, getRotation());
        hyperspaceTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hyperspaceTimer.stop();
                hyperspaceTimer = null;
            }
        });

        try {
            hyperspaceTimer.start();
        } catch (NullPointerException e) {
            // hyperspaceTimer was set to null before the timer could start
            // Start the timer again
            enterHyperspace();
        }
    }

    /**
     * Function to shoot a bullet from the ship. The bullet is shot in the
     * direction the ship is facing.
     *
     * @return the bullet object
     */
    public Bullet shootBullet() {
        return new Bullet(getX(), getY(), getRotation(), getSpeed());
    }

    /**
     * Overrides the nextFrame() function inherited from GameObject class.
     * Adds support for acceleration and deceleration of the ship.
     * 
     * If the thrust is active, the ship accelerates in the direction with the
     * rate of `accelerationRate`. The maximum speed is limited to `thrustSpeed`.
     * If the thrust is not active, the ship decelerates with the rate of
     * `decelerationRate`. The minimum speed is limited to 0.
     * 
     * @param renderer the renderer object
     */
    @Override
    public void nextFrame(AbstractRenderer renderer) {
        super.nextFrame(renderer);

        if (thrustTimer != null) {
            // If the thrust is active, accelerate the speed

            // Limit the top speed to the thrust speed
            float newSpeed = Math.min(
                    (this.getSpeed() + 1) * (1 + accelerationRate / renderer.framesPerSecond),
                    thrustSpeed);

            // Accelerate the ship in the direction it is facing
            this.setVelocity(newSpeed, this.getRotation());
        } else {
            // If the thrust is not active, decelerate the speed

            // Limit the minimum speed to 0
            float newSpeed = Math.max(
                    this.getSpeed() * (1 - decelerationRate / renderer.framesPerSecond),
                    0);

            // Decelerate the ship in the direction of its current velocity
            this.setSpeed(newSpeed);
        }
    }
}
