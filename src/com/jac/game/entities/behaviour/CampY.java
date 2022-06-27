package com.jac.game.entities.behaviour;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.Vector;
import com.jac.game.entities.Entity;
import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.Scheduler;

public class CampY extends FollowAlways{

    private boolean canFleeHorizontal = true;
    private boolean fleeing = false;
    private boolean fleeOnCooldown = false;

    private int fleeRange = 128;
    private int fleeDistance = 700;
    private int fleeCooldown = 180;
    private int fleeSpeed = 6;

    private int campSide = 1; //-1 for top, 1 for bottom

    public CampY(Mob owner, Entity target, int speed) {
        super(owner, target, speed);
        startFleeCooldown();
    }

    @Override
    public Vector moveCommand(){
        checkFlee();

        if(!fleeing) {
            return campingMoveCommand();
        }else{
            return fleeingMoveCommand();
        }
    }

    private Vector campingMoveCommand(){
        Vector track = super.moveCommand();
        track.y = 0;
        return track;
    }

    private Vector fleeingMoveCommand(){
        Vector flee = new Vector(0, 0);
        if(canFleeHorizontal && Math.abs(xDistance) < fleeRange){
            flee.x = -trackX(0) * fleeSpeed;
        }else{
            canFleeHorizontal = false;
            flee.x = 0;
        }
        flee.y = campSide*fleeSpeed;
        return flee;
    }

    private void checkFlee(){
        if(fleeOnCooldown && Math.abs(yDistance) < fleeRange){
            System.out.println("FLEEING!");
            fleeing = true;
            canFleeHorizontal = true;
            fleeOnCooldown = false;
            campSide = -campSide;
            Scheduler.getInstance().addTimedAction(fleeDistance/fleeSpeed, ()->{
                startFleeCooldown();
                fleeing=false;
            });
        }
    }

    private void startFleeCooldown(){
        Scheduler.getInstance().addTimedAction(fleeCooldown, ()->fleeOnCooldown = true);
    }

    @Override
    public boolean abilityCommand(Ability a){
        return super.abilityCommand(a) && !fleeing;
    }

    @Override
    public Vector getDirectionVector(){
        Vector facing = super.getDirectionVector();
        if(!fleeing) return facing;


        Vector fleeingDirectionVector = new Vector(0, campSide);
        return fleeingDirectionVector;
    }

}
