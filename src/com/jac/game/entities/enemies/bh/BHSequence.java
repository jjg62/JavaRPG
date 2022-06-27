package com.jac.game.entities.enemies.bh;

import com.jac.game.abilities.Ability;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.Attack;
import com.jac.game.abilities.attacks.components.Hitbox;
import com.jac.game.cutscene.events.PanCameraEvent;
import com.jac.game.display.Camera;
import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.rooms.Room;

import java.util.ArrayList;

public abstract class BHSequence extends Mob {

    private int xTarget, yTarget;
    private int trackSpeed;
    private boolean tracking;

    private ArrayList<Integer> attackTimes;

    public BHSequence(Room room, String name, int width, int height) {
        super(room, width, height, 1,  name);
    }

    public void addAttackAtTime(int time, Ability attack){
        if(attackTimes == null) attackTimes = new ArrayList<>();
        attackTimes.add(time);
        abilities.add(attack);
        //attack 0 will play at time 0, etc.
    }

    @Override
    public void spawn(int x, int y){
        super.spawn(x, y);

        ArrayList<Ability> abilitiesToAdd = abilities.getAdding();
        //Cast abilities at right time
        for(int i = 0; i < Math.min(attackTimes.size(), abilitiesToAdd.size()); i++){
            Ability abilityToCast = abilitiesToAdd.get(i);
            Scheduler.getInstance().addTimedAction(attackTimes.get(i), ()-> abilityToCast.cast());
        }
    }

    public void startMovingTowards(int xTarget, int yTarget, int trackSpeed){
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.trackSpeed = trackSpeed;
        this.tracking = true;
    }

    private void moveTowards(){
        tracking = false; //Attempt to stop

        int dx = xTarget - x;
        int dy = yTarget - y;

        if(Math.abs(dx) > trackSpeed){
            xSpeed = Integer.signum(dx)*trackSpeed;
            tracking = true;
        }else{
            x = xTarget;
        }

        if(Math.abs(dy) > trackSpeed){
            ySpeed = Integer.signum(dy)*trackSpeed;
            tracking = true;
        }else{
            y = yTarget;
        }
    }

    @Override
    public void tick(){
        //Camera.duelCamera(this, room.getPlayer());
        super.tick();
    }

    @Override
    public void hit(Hitbox hb){
        //Invincible
    }

    @Override
    public void die(){
        super.die();
    }

    @Override
    public void move(){
        if(tracking) moveTowards();
        moveX();
        moveY();
        xSpeed = 0;
        ySpeed = 0;
    }
}
