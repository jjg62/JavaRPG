package com.jac.game.entities.behaviour;

import com.jac.game.abilities.Vector;
import com.jac.game.entities.Entity;
import com.jac.game.entities.Mob;

public class StayWithin extends FollowAlways{

    private int range = 256;

    public StayWithin(Mob owner, Entity target, int speed) {
        super(owner, target, speed);
    }

    @Override
    public Vector moveCommand(){
        Vector follow = super.moveCommand();
        double distance = Math.sqrt(xDistance*xDistance + yDistance*yDistance);
        if(distance < range - speed) {
            return new Vector(-follow.x, -follow.y);
        }else if(distance > range + speed){
            return follow;
        }else{
            return new Vector(0, 0);
        }
    }
}
