package com.jac.game.abilities;

import com.jac.game.entities.Mob;
import com.jac.game.entities.structs.Scheduler;
import com.jac.game.utils.DirectionUtils;

public abstract class ConditionAbility extends Ability{

    private int waitPhaseDuration = 90;
    protected boolean waiting;

    public ConditionAbility(String name, int key, int cooldown, Mob owner) {
        super(name, key, cooldown, owner);
    }

    @Override
    public boolean cast(){
        if(isOnCooldown && !owner.isChanneling()){
            owner.playAbilitySound(this);
            updateDirectionVector();
            updateChannelAnimation();

            startWaitPhase();

            isOnCooldown = false;
            return true;
        }
        return false;
    }

    @Override
    public void tick(){
        if(waiting) {
            if(trigger()){
                endWaitPhase();
                play();
            }
            waitPhase();
        }else {
            super.tick();
        }
    }

    protected void startWaitPhase(){
        waiting = true;
        Scheduler.getInstance().addTimedAction(waitPhaseDuration, ()->endWaitPhase());
    }

    @Override
    public void cancel(boolean force){
        super.cancel(force);
        endWaitPhase();
    }

    protected void endWaitPhase(){
        waiting = false;
    }

    protected abstract void waitPhase();

    protected abstract boolean trigger();
}

