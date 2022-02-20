package dev.sevora;

import java.util.Map;
import static java.util.Map.entry;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Keybinding is a class meant to be used statically, it should hold
 * all the logic and values to figure out the calculator instruction from
 * a key event.
 * @author Ralph Louis Gopez
 */
public class Keybindings {

    // Below are all the single keys
    public static Map<KeyCode, String> ALL_NORMAL = Map.ofEntries(
        entry(KeyCode.DIGIT0, "0"),
        entry(KeyCode.DIGIT1, "1"),
        entry(KeyCode.DIGIT2, "2"),
        entry(KeyCode.DIGIT3, "3"),
        entry(KeyCode.DIGIT4, "4"),
        entry(KeyCode.DIGIT5, "5"),
        entry(KeyCode.DIGIT6, "6"),
        entry(KeyCode.DIGIT7, "7"),
        entry(KeyCode.DIGIT8, "8"),
        entry(KeyCode.DIGIT9, "9"),
        
        entry(KeyCode.NUMPAD0, "0"),
        entry(KeyCode.NUMPAD1, "1"),
        entry(KeyCode.NUMPAD2, "2"),
        entry(KeyCode.NUMPAD3, "3"),
        entry(KeyCode.NUMPAD4, "4"),
        entry(KeyCode.NUMPAD5, "5"),
        entry(KeyCode.NUMPAD6, "6"),
        entry(KeyCode.NUMPAD7, "7"),
        entry(KeyCode.NUMPAD8, "8"),
        entry(KeyCode.NUMPAD9, "9"),

        entry(KeyCode.PLUS, "+"),
        entry(KeyCode.MINUS, "-"),
        entry(KeyCode.MULTIPLY, "x"),
        entry(KeyCode.X, "x"),
        entry(KeyCode.DIVIDE, "÷"),
        entry(KeyCode.SLASH, "÷"),

        entry(KeyCode.ENTER, "="),
        entry(KeyCode.SPACE, "="),
        entry(KeyCode.EQUALS, "="),

        entry(KeyCode.BACK_SPACE, "C"),
        entry(KeyCode.DELETE, "AC"),
        entry(KeyCode.PERIOD, ".")
    );  
    
    // Below are combination of keys for ease-of-use as not all
    // keyboards have a number pad.
    public static Map<KeyCombination, String> COMBINATIONS = Map.ofEntries(
        entry(new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHIFT_DOWN), "+"),
        entry(new KeyCodeCombination(KeyCode.DIGIT8, KeyCombination.SHIFT_DOWN), "*"),
        entry(new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.SHIFT_DOWN), "%")
    );

    /**
     * Use this method to figure out what instruction 
     * a key event should be translated to.
     * @param event A JavaFX KeyEvent.
     * @return String that contains the instruction that can be used on the calculator.
     */
    public static String getInstructionFromEvent(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        for (Map.Entry<KeyCombination, String> combination : Keybindings.COMBINATIONS.entrySet()) {
            if (combination.getKey().match(event)) {
                return combination.getValue();
            }
        }

        if (Keybindings.ALL_NORMAL.containsKey(keyCode)) {
            return Keybindings.ALL_NORMAL.get(keyCode);
        }

        return "";
    }
}
