package com.jac.game.entities.behaviour.projectiles;

import com.jac.game.abilities.ProjectileBehaviour;
import com.jac.game.abilities.Vector;

import java.util.Random;

public class Tornado extends Bouncing{

    private int var;

    public Tornado(int speed, int var) {
        super(speed);
        this.var = var;
    }

    @Override
    public Vector moveCommand(){
        if(moveVector == null){
            moveVector = new Vector(speed, 0);
            moveVector.rotate(angle);
        }
        checkForBounce();
        Vector randomisedMove = new Vector(moveVector.x + randomModifier(), moveVector.y + randomModifier());
        return randomisedMove;
    }

    private int randomModifier(){
        Random rand = new Random();
        return rand.nextInt(var*2) - var;
    }

    @Override
    public ProjectileBehaviour copy(){
        return new Tornado(speed, var);
    }
}
