package asteroids;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.function.Consumer;

import asteroids.renderer.AbstractRenderer;
import asteroids.state.InputStateManager;

public class AsteroidsRenderer extends AbstractRenderer {

    // List of supported actions for the game.
    public enum ActionType {
        THRUST, ROTATE_LEFT, ROTATE_RIGHT, SHOOT, HYPERSPACE
    }

    // Keyboard mappings for the game inputs.
    public Map<ActionType, Integer> actionToKey = Map.of(
            ActionType.THRUST, KeyEvent.VK_UP,
            ActionType.ROTATE_LEFT, KeyEvent.VK_LEFT,
            ActionType.ROTATE_RIGHT, KeyEvent.VK_RIGHT,
            ActionType.SHOOT, KeyEvent.VK_SPACE,
            ActionType.HYPERSPACE, KeyEvent.VK_SHIFT);

    // The current game to render.
    private AsteroidsGame game;

    public AsteroidsRenderer(AsteroidsGame game) {
        super(60);

        this.game = game;

        // Allow the game to receive keyboard input
        setFocusable(true);

        // Handlers for actions.
        Map<ActionType, Consumer<Void>> actionHandler = Map.of(
                ActionType.THRUST, Void -> game.ship.enableThrust(),
                ActionType.ROTATE_LEFT, Void -> game.ship.rotateLeft(framesPerSecond),
                ActionType.ROTATE_RIGHT, Void -> game.ship.rotateRight(framesPerSecond),
                ActionType.SHOOT, Void -> game.shootBullet(),
                ActionType.HYPERSPACE, Void -> game.ship.enterHyperspace());

        /*
         * Here we initialize a input state manager and add
         * input states according to the game actions.
         */
        InputStateManager.init();
        InputStateManager.addInputState(
            actionToKey.get(ActionType.THRUST), 
            actionHandler.get(ActionType.THRUST), 
            true
        );
        InputStateManager.addInputState(
            actionToKey.get(ActionType.ROTATE_LEFT), 
            actionHandler.get(ActionType.ROTATE_LEFT), 
            true
        );
        InputStateManager.addInputState(
            actionToKey.get(ActionType.ROTATE_RIGHT), 
            actionHandler.get(ActionType.ROTATE_RIGHT), 
            true
        );
        InputStateManager.addInputState(
            actionToKey.get(ActionType.HYPERSPACE), 
            actionHandler.get(ActionType.HYPERSPACE), 
            false
        );
        InputStateManager.addInputState(
            actionToKey.get(ActionType.SHOOT), 
            actionHandler.get(ActionType.SHOOT), 
            false
        );
        InputStateManager.bindInputListener(this);
    }

    @Override
    protected void nextFrame() {
        InputStateManager.update();
        // Update the game state
        game.nextFrame(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Paint all the objects in the game
        game.paint((Graphics2D) g);
    }
}
