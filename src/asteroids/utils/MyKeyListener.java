package asteroids.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A wrapper class for Java Key Listener
 */
public class MyKeyListener {

    KeyListener trueListener;

    /**
     * Notice this hashmap is nested hashmap. 
     * <p>Outside layer: KeyCode to hashmap.
     * <p>Inside layer: KeyOp to function.
     * <p>To understand it, lets assume we want
     * to bind the (1) key release (2) key 'a'
     * to (3) ship.rotate()
     * <p>- Then when we do put:
     * <p><code>Map&#60KeyOp, Consumer<Void>> m = keyEventBinding.get(keyCode of 'a');</code>
     * <p><code>m.put(KeyOp.Release, (Void) -> ship.rotate());</code>
     * <p>- When we do get:
     * <p><code>Map&#60KeyOp, Consumer<Void>> m = keyEventBinding.get(keyCode of 'a');</code>
     * <p><code>Consumer<Void> shipRotateFunc = m.get(KeyOp.Release);</code>
     */
    Map<Integer, Map<KeyOp, Consumer<Void>>> keyEventBinding;

    public MyKeyListener() {
        this.keyEventBinding = new HashMap<>();
        this.trueListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
    }

    /**
     * Update all key binding by read from the <code>Map<..> keyEventBinding</code>
     * <p>by create a new Java key listener and put inside functions from the map.
     */
    public void refreshKeyBinding() {
        this.trueListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyCode = e.getKeyCode();
                var f = getFromBinding(keyCode, KeyOp.Type);
                if (f != null) {
                    f.accept(null);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();                
                var f = getFromBinding(keyCode, KeyOp.Press);
                if (f != null) {
                    f.accept(null);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                var f = getFromBinding(keyCode, KeyOp.Release);
                if (f != null) {
                    f.accept(null);
                }
            }
        };
    }

    public enum KeyOp {
        Press, Release, Type
    }

    public void setMapping(char key, Consumer<Void> f) {
        setMapping(key, f, KeyOp.Press);
    }

    public void setMapping(char key, Consumer<Void> f, MyKeyListener.KeyOp keyOp) {
        int keyCode = KeyCode.getKeyCode(key);
        setMapping(keyCode, f, keyOp);
    }

    public void setMapping(int keyCode, Consumer<Void> f, MyKeyListener.KeyOp keyOp) {
        System.out.println("[setMapping] keyCode " + keyCode);

        keyEventBinding.computeIfAbsent(keyCode, k -> new HashMap<>());
        keyEventBinding.get(keyCode).put(keyOp, f);
        refreshKeyBinding();
    }

    /**
     * @param keyCode
     * @param keyOp
     * @return the function bind to that keyCode and keyOp
     */
    private Consumer<Void> getFromBinding(int keyCode, KeyOp keyOp) {
        var m = keyEventBinding.get(keyCode);
        if (m == null) return null;
        var f = m.get(keyOp);
        return f;
    }

}
