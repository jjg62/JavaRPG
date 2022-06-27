package com.jac.game.abilities.attacks;

import com.jac.game.abilities.Ability;
import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.Scheduler;

public class CompoundAbility extends Attack{

    private Ability a1;
    private Ability a2;

    public CompoundAbility(String name, int key, int range, int cooldown, Mob owner, Ability a1, Ability a2) {
        super(name, key, range, cooldown, owner);
        this.a1 = a1;
        this.a2 = a2;
    }

    @Override
    public boolean cast(){
        if(isOnCooldown && !owner.isChanneling()){
            owner.playAbilitySound(this);
            updateDirectionVector();
            updateChannelAnimation();
            play();
            isOnCooldown = false;
            return true;
        }
        return false;
    }

    @Override
    public void play(){
        a1.updateDirectionVector();
        a2.updateDirectionVector();
        Scheduler.getInstance().addTimedAction(a1.getActivationDelay(), ()->{
            a1.play();
        });

        Scheduler.getInstance().addTimedAction(a2.getActivationDelay(), ()->{
            a2.play();
        });
    }

    @Override
    public void tick(){
        a1.tick();
        a2.tick();
        super.tick();
    }

    @Override
    public boolean shouldOwnerChannel(){
        return a1.shouldOwnerChannel() || a2.shouldOwnerChannel();
    }

    @Override
    public void cancel(boolean force){
        super.cancel(force);
        a1.cancel(force);
        a2.cancel(force);
    }
}
