package asteroids.object;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import asteroids.renderer.AbstractRenderer;

public class Bullet extends GameObject {

    // The default shape of the bullet.
    private static final Shape defaultShape = new Ellipse2D.Float(0, 0, 3, 3);

    // The default color of the bullet.
    private static final Color defaultColor = Color.RED;

    // The speed of the bullet.
    private float speed = 100;

    // Whether the bullet is visible on the screen.
    public boolean isVisible = true;

    public Bullet(float x, float y, float direction, float shipSpeed) {
        super(x, y, 0, defaultShape, defaultColor); // The bullet does not rotate
        setVelocity(Math.max(speed, speed + (shipSpeed / 2)), direction);
    }

    @Override
    public void nextFrame(AbstractRenderer renderer) {
        move(renderer);

        if (getX() < 0 || getX() > renderer.getWidth() || getY() < 0 || getY() > renderer.getHeight()) {
            isVisible = false;
        }
    }
}
