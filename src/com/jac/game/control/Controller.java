package com.jac.game.control;

import com.jac.game.abilities.Vector;

import java.awt.event.KeyEvent;

public class Controller {

    public static Vector direction = new Vector(0, 1);
    public static boolean[] keys = new boolean[256];

    public static boolean up(){
        return keys[KeyEvent.VK_W];
    }
    public static boolean down(){
        return keys[KeyEvent.VK_S];
    }
    public static boolean left(){
        return keys[KeyEvent.VK_A];
    }
    public static boolean right(){
        return keys[KeyEvent.VK_D];
    }
    public static boolean talk() {
        return keys[KeyEvent.VK_E];
    }
    public static boolean inventory(){
        return keys[KeyEvent.VK_Q];
    }

    public static void updateLastDirection(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_W){
            direction.x = 0;
            direction.y = -1;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            direction.x = 0;
            direction.y = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            direction.x = -1;
            direction.y = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            direction.x = 1;
            direction.y = 0;
        }
    }


}
