package com.jac.game.abilities.attacks.components.projectiles;

import com.jac.game.abilities.ProjectileBehaviour;
import com.jac.game.abilities.Vector;
import com.jac.game.abilities.attacks.components.AttackComponent;
import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.Scheduler;

public class ChannelProjectile extends Projectile {

    public ChannelProjectile(Mob owner, int duration, Vector displacement, int width, int height, int hitboxXOffset, int hitboxYOffset, int hitboxWidth, int hitboxHeight, int activationDelay, int knockbackScalar, ProjectileBehaviour guide) {
        super(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar, guide);
    }

    @Override
    protected void initInfo(AttackComponent created, int x, int y, double angle){
        super.initInfo(created, x, y, angle);
        setChanneling();
    }

    private void setChanneling(){
        owner.setChanneling(true);
        Scheduler.getInstance().addTimedAction(activationDelay, ()->owner.setChanneling(false));
    }

    @Override
    public void tick(){
        //If channel is interrupted
        if(!owner.isChanneling() && !active){
            onDeath();
        }
        super.tick();
    }

    @Override
    protected void onHit(Mob target){
        super.onHit(target);
        onDeath();
    }

    @Override
    protected AttackComponent copy(){
        return new ChannelProjectile(owner, duration, displacement, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, activationDelay, knockbackScalar, guide.copy())
                .withActiveAnimation(activeAnimation)
                .withFX(vfx);
    }
}
