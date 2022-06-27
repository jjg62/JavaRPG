package com.jac.game.control;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.structs.Ticking;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Controller.keys[e.getKeyCode()] = true;
        Controller.updateLastDirection(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Controller.keys[e.getKeyCode()] = false;
    }


}
