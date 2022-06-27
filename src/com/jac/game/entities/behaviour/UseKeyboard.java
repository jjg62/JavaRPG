package com.jac.game.entities.behaviour;


import com.jac.game.abilities.Ability;
import com.jac.game.abilities.Vector;
import com.jac.game.control.Controller;
import com.jac.game.entities.Mob;

public class UseKeyboard implements Behaviour{

    private Mob owner;
    private int speed;

    public UseKeyboard(Mob owner, int speed){
        this.owner = owner;
        this.speed = speed;
    }

    @Override
    public Vector moveCommand() {
        return getMoveDirection().scale(speed);
    }

    @Override
    public boolean abilityCommand(Ability a) {
        return Controller.keys[a.getKey()];
    }

    @Override
    public Vector getDirectionVector() {
        Vector direction = getMoveDirection();
        if(direction.x == 0 && direction.y == 0){
            return Controller.direction;
        }
        return direction;
    }

    //get direction vector just according to keys being currently pressed.
    private Vector getMoveDirection(){
        return new Vector((Controller.left() ? -1 : 0) + (Controller.right() ? 1 : 0), (Controller.up() ? -1 : 0) + (Controller.down() ? 1 : 0));
    }

}
