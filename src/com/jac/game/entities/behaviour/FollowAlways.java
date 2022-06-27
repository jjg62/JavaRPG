package com.jac.game.entities.behaviour;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.Vector;
import com.jac.game.entities.Entity;
import com.jac.game.entities.Mob;
import com.jac.game.utils.DirectionUtils;

public class FollowAlways implements Behaviour {

    private Entity target;
    private Mob owner;
    private int moveRange = 48;
    protected int speed;

    protected int xDistance;
    protected int yDistance;

    public FollowAlways(Mob owner, Entity target, int speed){
        this.owner = owner;
        this.target = target;
        this.speed = speed;
    }

    protected int trackX(int sensitivity){
        if(Math.abs(xDistance) > sensitivity){
            return Integer.signum(xDistance);
        }
        return 0;
    }

    protected int trackY(int sensitivity){
        if(Math.abs(yDistance) > sensitivity){
            return Integer.signum(yDistance);
        }
        return 0;
    }

    protected void updateDistance(){
        xDistance = target.getX() + target.getBounds().x + target.getBounds().width/2 - (owner.getHurtbox().x + owner.getBounds().width/2);
        yDistance = target.getY() + target.getBounds().y + target.getBounds().height/2 - (owner.getHurtbox().y + owner.getBounds().height/2);
    }


    @Override
    public Vector moveCommand() {
        updateDistance();
        return new Vector(trackX(moveRange)*speed, trackY(moveRange)*speed);
    }

    private boolean outOfRange(int distance){
        return Math.abs(distance) > moveRange;
    }

    public Vector getDirectionVector(){
        updateDistance();
        return DirectionUtils.radiansToDirectionVector(DirectionUtils.vectorToRadians(new Vector(xDistance, yDistance)));
    }

    @Override
    public boolean abilityCommand(Ability a) {
        if(a instanceof Attack){
            return Math.abs(xDistance) <= ((Attack)a).getRange() &&
                    Math.abs(yDistance) <= ((Attack)a).getRange();
        }else{
            return true;
        }
    }

}
