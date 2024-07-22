package com.discipline.drms.utils;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

public class KeyboardHandler {

    public static void addKeyHandler(Node node, EventHandler<KeyEvent> handler) {
        node.setFocusTraversable(true);
        node.setOnKeyPressed(handler);
    }
}
