package com.jac.game.abilities.attacks.components;

import com.jac.game.abilities.Vector;
import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.Scheduler;

public class ChannelHitbox extends Hitbox {

    private int endLag = 0;
    private boolean last;

    public ChannelHitbox(Mob owner, int duration, Vector displacement, int width, int height, int hitboxXOffset, int hitboxYOffset, int hitboxWidth, int hitboxHeight, int activiationDelay, int knockbackScalar, boolean last) {
        super(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activiationDelay, knockbackScalar);
        this.last = last;
    }


    public ChannelHitbox withEndLag(int endLag){
        this.endLag = endLag;
        return this;
    }

    @Override
    protected void initInfo(AttackComponent created, int x, int y, double angle){
        super.initInfo(created, x, y, angle);
        setChanneling();
    }

    @Override
    public void tick(){
        //If channel is interrupted
        if(!owner.isChanneling() && !active){
            onDeath();
        }
        super.tick();
    }

    private void setChanneling(){
        owner.setChanneling(true);
        if(last) Scheduler.getInstance().addTimedAction(activationDelay + endLag, ()->owner.setChanneling(false));
    }

    @Override
    protected AttackComponent copy(){
        return new ChannelHitbox(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar, last)
                .withEndLag(endLag)
                .withActiveAnimation(activeAnimation)
                .withFX(vfx);
    }
}
