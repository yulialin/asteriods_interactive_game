package asteroids.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import asteroids.renderer.AbstractRenderer;

/**
 * A class representing a game object that can be drawn on the screen.
 */
public abstract class GameObject {

    private static int objectCount = 0;

    private int objectId = objectCount++;

    // The x and y coordinates of the object on the renderer.
    private float x;
    private float y;

    // The visual rotation degree of the object.
    // This only affects visual appearance of the object.
    private float rotation;

    // The shape of the object.
    private Shape shape;

    // The color of the object.
    private Color color;

    // The direction degree of the object.
    // This affects the movement of the object.
    private float direction = 0;

    // The speed of the object.
    private float speed = 0;

    /**
     * Creates a new game object.
     * 
     * @param x        the x coordinate of the object
     * @param y        the y coordinate of the object
     * @param rotation the rotation angle of the object
     * @param shape    the shape of the object
     * @param color    the color of the object
     */
    public GameObject(float x, float y, float rotation, Shape shape, Color color) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.shape = shape;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameObject) {
            GameObject other = (GameObject) obj;
            return this.objectId == other.objectId;
        }
        return false;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Rotates the object by a specified angle.
     * 
     * @param da the degree to rotate the object. Positive values rotate the object
     *           clockwise, and negative values rotate the object counterclockwise.
     */
    public void rotate(float da) {
        // Update the visual rotation of the object
        this.rotation += da;
        this.rotation %= 360;
    }

    /**
     * Sets the velocity of the object.
     * 
     * @param newSpeed     the speed of the object
     * @param newDirection the direction of the object in degrees. The angle is in
     *                     degrees between 0 and 360.
     */
    public void setVelocity(float newSpeed, float newDirection) {
        this.speed = newSpeed;
        this.direction = newDirection % 360;
    }

    public float getRotation() {
        return rotation;
    }

    public float getSpeed() {
        return speed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    protected void move(AbstractRenderer renderer) {
        // Move the object by its current velocity
        x += (speed / renderer.framesPerSecond) * Math.sin(Math.toRadians(direction));
        y -= (speed / renderer.framesPerSecond) * Math.cos(Math.toRadians(direction));
    }

    protected void wrap(AbstractRenderer renderer) {
        // Wrap the object around the screen if it goes off the right or left edge
        if (x < 0) {
            // Object went off the left edge, move to the right
            x += renderer.getWidth();
        } else if (x > renderer.getWidth()) {
            // Object went off the right edge, move to the left
            x -= renderer.getWidth();
        }

        // Wrap the object around the screen if it goes off the top or bottom edge
        if (y < 0) {
            // Object went off the top edge, move to the bottom
            y += renderer.getHeight();
        } else if (y > renderer.getHeight()) {
            // Object went off the bottom edge, move to the top
            y -= renderer.getHeight();
        }
    }

    /**
     * Updates the object's state as you want it to appear on
     * the next frame of the animation.
     */
    public void nextFrame(AbstractRenderer renderer) {
        move(renderer);
        wrap(renderer);
    }

    /**
     * Check whether two objects collide. This tests whether their shapes intersect.
     * 
     * @param object the object to test
     * @return true if the shapes intersect
     */
    public boolean collidesWith(GameObject object) {
        if (object == null) {
            return false;
        }
        // TODO: confirm whether this works correctly
        // System.out.println(this.shape.getBounds2D() + " " +
        // object.shape.getBounds2D());
        return getShape().intersects(object.getShape().getBounds2D());
    }

    /**
     * Returns the shape after applying the current translation
     * and rotation
     * 
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {
        // AffineTransform captures the movement and rotation we
        // want the shape to have
        AffineTransform at = new AffineTransform();

        // x, y are where the origin of the shape will be. In this
        // case, this is the center of the triangle. See the constructor
        // to see where the points are.
        at.translate(x, y);

        // Rotate the shape 45 degrees to the left
        at.rotate(Math.toRadians(rotation));

        // Create a shape that looks like our triangle, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(shape);
    }

    /**
     * Draws the object on the screen.
     * 
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(color);
        // g.fill(getShape());
        g.draw(getShape());
    }
}
