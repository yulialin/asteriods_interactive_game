package asteroids.state;
import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JComponent;


import asteroids.utils.InputHandler;
import asteroids.utils.MyKeyListener.KeyOp;


public class InputStateManager {
    
    public static boolean pause = false;

    private static InputHandler inputHandler;

    public static void init() {
        inputHandler = new InputHandler();
        actions = new ArrayList<>();
    }

    /**
     * Update every input states that are managed here.
     * <p>Should be invoked from the nextFrame method. 
     */
    public static void update() {
        for (InputState action: actions) {
            action.update();
        }
    }

    /**
     * In case that you want to reuse some of the states 
     * here and define a customized behavior to be
     * triggered by this input state.
     * <p>Most likely not be used, but left here for 
     * future possible extensibility. 
     */
    public static void registerUpdate() {}

    /**
     * An inside, static 
     * <p>(which means it can be used by static
     * method of outside class. If this confuses you, 
     * don't worry and it's not the important part.)
     * <p>class the represent an input state.
     * <p>Input state means a state machine that is 
     * associated with a key input and its action
     * <code>public Consumer<Void> func</code>.
     * <p>To put it in a simple way, it keeps record,
     * such that if a key is pressed down for a while, 
     * it can decide whether to trigger the action
     * or not. 
     * <p>Because some actions are repeatable and some
     * are not. 
     */
    private static class InputState {
        public boolean repeatable;
        public boolean isKeyPressed;
        public Consumer<Void> func;

        public boolean start;

        public InputState(
            boolean repeatable,
            Consumer<Void> func
        ) {
            this.repeatable = repeatable;
            this.isKeyPressed = false;
            this.func = func;
            this.start = false;
        }

        public void trigger() {
            if (!pause) {
                this.func.accept(null);
            }
        }

        public void update() {
            if (isKeyPressed) {
                if (repeatable) {
                    trigger();
                    return;
                }
                /* If not repeatable: check if the action has been started */
                if (!start) {
                    trigger();
                    start = true;
                    return;
                }
            } else {
                start = false;
            }
        }
    }

    private static List<InputState> actions;

    /**
     * @param key  Key code for the game action.
     * @param func    The function (that takes void param and return nothing) to be bind to the key.
     * @param repeatable   If the function will be triggered according to the frame rate when holding pressing down.
     * <p>Add a new Input State to be listened by key listener,
     * and managed by the InputStateManager. 
     */
    public static void addInputState(
        int key,
        Consumer<Void> func,
        boolean repeatable
    ) {

        InputState action = new InputState(repeatable, func);
        actions.add(action);

        inputHandler.setKeyMapping(
            key,
            Void -> {
                action.isKeyPressed = true;
            },
            KeyOp.Press
        );

        inputHandler.setKeyMapping(
            key,
            Void -> {
                action.isKeyPressed = false;
            },
            KeyOp.Release
        );

    }


    /**
     * @param cmp The component (high level main component of the app) be bind this manager to.
     */
    public static void bindInputListener(JComponent cmp) {
        inputHandler.registerToComponent(cmp);
    }

}
