package asteroids.renderer;

import javax.swing.JComponent;

/**
 * An abstract class for rendering the game. This class provides the basic
 * functionality to run an animation. Subclasses should define the nextFrame
 * method. This method is called 30 times per second (or another specified)
 * updating and drawing the latest state of the game.
 *
 */
public abstract class AbstractRenderer extends JComponent implements Runnable {

    // Default amount of target frames per second
    public final int framesPerSecond;

    // Amount of milliseconds to pause between frames
    private final int pauseTime;

    // The thread in which the rendering is done
    private Thread rendererThread;

    /**
     * Creates an animation with the default frame rate of 30 frames per second.
     */
    public AbstractRenderer() {
        this(30);
    }

    /**
     * Creates an animation with the specified frame rate.
     * 
     * @param framesPerSecond the target frames per second. Must be greater than 0.
     */
    public AbstractRenderer(int framesPerSecond) {
        assert framesPerSecond > 0 && framesPerSecond < 1000 : "Invalid frames per second";
        this.framesPerSecond = framesPerSecond;
        this.pauseTime = 1000 / framesPerSecond;
    }

    /**
     * Update the state of the renderer to represent the next frame.
     */
    protected abstract void nextFrame();

    /**
     * Starts the renderer thread.
     */
    public void start() {
        if (rendererThread == null) {
            rendererThread = new Thread(this);
            rendererThread.start();
        }
    }

    /**
     * Stops the renderer thread.
     */
    public void stop() {
        if (rendererThread != null) {
            rendererThread = null;
        }
    }

    /**
     * Runs the renderer. This method should not be called directly. Instead,
     * to start the renderer, call the start() method.
     */
    public void run() {
        // Update the display periodically.
        try {
            while (Thread.currentThread() == rendererThread) {
                nextFrame();
                repaint();

                Thread.sleep(pauseTime);
            }
        } catch (InterruptedException e) {
            // Stop the animation if interrupted.
        }
    }

}
