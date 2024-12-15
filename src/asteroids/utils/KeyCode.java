package asteroids.utils;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert unicode characters into java.awt.event.KeyEvent,
 * an integer enum class for key input on the keyboard. 
 * This class is NOT being used since using unicode character
 * is not a good practice. 
 *
 */
public class KeyCode {
    private static final Map<Character, Integer> keyboardMap = new HashMap<>();

    static {
        keyboardMap.put('0', KeyEvent.VK_0);
        keyboardMap.put('1', KeyEvent.VK_1);
        keyboardMap.put('2', KeyEvent.VK_2);
        keyboardMap.put('3', KeyEvent.VK_3);
        keyboardMap.put('4', KeyEvent.VK_4);
        keyboardMap.put('5', KeyEvent.VK_5);
        keyboardMap.put('6', KeyEvent.VK_6);
        keyboardMap.put('7', KeyEvent.VK_7);
        keyboardMap.put('8', KeyEvent.VK_8);
        keyboardMap.put('9', KeyEvent.VK_9);
        keyboardMap.put('A', KeyEvent.VK_A);
        keyboardMap.put('B', KeyEvent.VK_B);
        keyboardMap.put('C', KeyEvent.VK_C);
        keyboardMap.put('D', KeyEvent.VK_D);
        keyboardMap.put('E', KeyEvent.VK_E);
        keyboardMap.put('F', KeyEvent.VK_F);
        keyboardMap.put('G', KeyEvent.VK_G);
        keyboardMap.put('H', KeyEvent.VK_H);
        keyboardMap.put('I', KeyEvent.VK_I);
        keyboardMap.put('J', KeyEvent.VK_J);
        keyboardMap.put('K', KeyEvent.VK_K);
        keyboardMap.put('L', KeyEvent.VK_L);
        keyboardMap.put('M', KeyEvent.VK_M);
        keyboardMap.put('N', KeyEvent.VK_N);
        keyboardMap.put('O', KeyEvent.VK_O);
        keyboardMap.put('P', KeyEvent.VK_P);
        keyboardMap.put('Q', KeyEvent.VK_Q);
        keyboardMap.put('R', KeyEvent.VK_R);
        keyboardMap.put('S', KeyEvent.VK_S);
        keyboardMap.put('T', KeyEvent.VK_T);
        keyboardMap.put('U', KeyEvent.VK_U);
        keyboardMap.put('V', KeyEvent.VK_V);
        keyboardMap.put('W', KeyEvent.VK_W);
        keyboardMap.put('X', KeyEvent.VK_X);
        keyboardMap.put('Y', KeyEvent.VK_Y);
        keyboardMap.put('Z', KeyEvent.VK_Z);
        keyboardMap.put(' ', KeyEvent.VK_SPACE);
        keyboardMap.put('!', KeyEvent.VK_EXCLAMATION_MARK);
        keyboardMap.put('"', KeyEvent.VK_QUOTEDBL);
        keyboardMap.put('#', KeyEvent.VK_NUMBER_SIGN);
        keyboardMap.put('$', KeyEvent.VK_DOLLAR);
        // keyboardMap.put('%', KeyEvent.VK_PERCENT);
        keyboardMap.put('&', KeyEvent.VK_AMPERSAND);
        keyboardMap.put('\'', KeyEvent.VK_QUOTE);
        keyboardMap.put('(', KeyEvent.VK_LEFT_PARENTHESIS);
        keyboardMap.put(')', KeyEvent.VK_RIGHT_PARENTHESIS);
        keyboardMap.put('*', KeyEvent.VK_ASTERISK);
        keyboardMap.put('+', KeyEvent.VK_PLUS);
        keyboardMap.put(',', KeyEvent.VK_COMMA);
        keyboardMap.put('-', KeyEvent.VK_MINUS);
        keyboardMap.put('.', KeyEvent.VK_PERIOD);
        keyboardMap.put('/', KeyEvent.VK_SLASH);
        keyboardMap.put(':', KeyEvent.VK_COLON);
        keyboardMap.put(';', KeyEvent.VK_SEMICOLON);
        keyboardMap.put('<', KeyEvent.VK_LESS);
        keyboardMap.put('=', KeyEvent.VK_EQUALS);
        keyboardMap.put('>', KeyEvent.VK_GREATER);
        // keyboardMap.put('?', KeyEvent.VK_);
        keyboardMap.put('@', KeyEvent.VK_AT);
        keyboardMap.put('[', KeyEvent.VK_OPEN_BRACKET);
        keyboardMap.put('\\', KeyEvent.VK_BACK_SLASH);
        keyboardMap.put(']', KeyEvent.VK_CLOSE_BRACKET);
        keyboardMap.put('^', KeyEvent.VK_CIRCUMFLEX);
        keyboardMap.put('_', KeyEvent.VK_UNDERSCORE);
        keyboardMap.put('`', KeyEvent.VK_BACK_QUOTE);
        keyboardMap.put('{', KeyEvent.VK_BRACELEFT);
        // keyboardMap.put('|', KeyEvent.VK_VERTICAL_BAR);
        keyboardMap.put('}', KeyEvent.VK_BRACERIGHT);
        // keyboardMap.put('~', KeyEvent.VK_TILDE);

        keyboardMap.put('a', KeyEvent.VK_A);
        keyboardMap.put('b', KeyEvent.VK_B);
        keyboardMap.put('c', KeyEvent.VK_C);
        keyboardMap.put('d', KeyEvent.VK_D);
        keyboardMap.put('e', KeyEvent.VK_E);
        keyboardMap.put('f', KeyEvent.VK_F);
        keyboardMap.put('g', KeyEvent.VK_G);
        keyboardMap.put('h', KeyEvent.VK_H);
        keyboardMap.put('i', KeyEvent.VK_I);
        keyboardMap.put('j', KeyEvent.VK_J);
        keyboardMap.put('k', KeyEvent.VK_K);
        keyboardMap.put('l', KeyEvent.VK_L);
        keyboardMap.put('m', KeyEvent.VK_M);
        keyboardMap.put('n', KeyEvent.VK_N);
        keyboardMap.put('o', KeyEvent.VK_O);
        keyboardMap.put('p', KeyEvent.VK_P);
        keyboardMap.put('q', KeyEvent.VK_Q);
        keyboardMap.put('r', KeyEvent.VK_R);
        keyboardMap.put('s', KeyEvent.VK_S);
        keyboardMap.put('t', KeyEvent.VK_T);
        keyboardMap.put('u', KeyEvent.VK_U);
        keyboardMap.put('v', KeyEvent.VK_V);
        keyboardMap.put('w', KeyEvent.VK_W);
        keyboardMap.put('x', KeyEvent.VK_X);
        keyboardMap.put('y', KeyEvent.VK_Y);
        keyboardMap.put('z', KeyEvent.VK_Z);

        keyboardMap.put('↑', KeyEvent.VK_UP);
        keyboardMap.put('↓', KeyEvent.VK_DOWN);
        keyboardMap.put('←', KeyEvent.VK_LEFT);
        keyboardMap.put('→', KeyEvent.VK_RIGHT);
        keyboardMap.put('⇧', KeyEvent.VK_SHIFT);
        keyboardMap.put(' ', KeyEvent.VK_SPACE);  /* Might cause bug, but anyway I DONT want to introduce */
        /* the dependency of java.awt.event.KeyEvent into outside */
    }

    public static int getKeyCode(char character) {
        Integer keyCode = keyboardMap.get(character);
        return (keyCode != null) ? keyCode : KeyEvent.VK_UNDEFINED;
    }
}