package asteroids.utils;

import java.util.function.Consumer;
import javax.swing.JComponent;

import asteroids.utils.MyKeyListener.KeyOp;

/**
 * The reason while I am having a InputHandler
 * and keyListener is a field of it, is because
 * I'm expecting other listeners, like mouseListener
 * to be added in the future. 
 * <p>For now there is only a key listener field.
 */
public class InputHandler {

    private MyKeyListener myKeyListener;

    public InputHandler() {
        myKeyListener = new MyKeyListener();
    }

    public void setKeyMapping(char key, Consumer<Void> f) {
        this.setKeyMapping(key, f, KeyOp.Press);
    }

    public void setKeyMapping(char key, Consumer<Void> f, KeyOp keyOp) {
        this.myKeyListener.setMapping(key, f, keyOp);
    }

    public void setKeyMapping(int key, Consumer<Void> f, KeyOp keyOp) {
        this.myKeyListener.setMapping(key, f, keyOp);
    }

    public void registerToComponent(JComponent component) {
        component.addKeyListener(this.myKeyListener.trueListener);
        System.out.println("listener added");
    }
}
