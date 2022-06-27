package com.jac.game.abilities;

import com.jac.game.abilities.attacks.Attack;
import com.jac.game.audio.SoundHandler;
import com.jac.game.entities.Mob;

import javax.sound.sampled.Clip;

public class AbilityCounter extends ConditionAbility{

    private Ability counterAttack;

    //Temp
    private Clip punch;

    public AbilityCounter(String name, int key, int cooldown, Mob owner, Ability counterAttack) {
        super(name, key, cooldown, owner);
        this.counterAttack = counterAttack;
        this.punch = SoundHandler.loadClip("audio/temp/shield_hit.wav");
    }

    @Override
    protected void waitPhase() {

    }

    @Override
    protected void startWaitPhase(){
        super.startWaitPhase();
        //owner.setChanneling(true);
        //owner.setCountering(true);
    }

    @Override
    protected void endWaitPhase(){
        if(waiting) {
            super.endWaitPhase();
            owner.setCountered(false);
            //owner.setChanneling(false);
            //owner.setCountering(false);
        }
    }

    //Trigger - takes damage
    @Override
    protected boolean trigger() {
        return owner.isCountered();
    }

    @Override
    public void play() {
        counterAttack.updateDirectionVector();
        counterAttack.play();
        //Sound
        SoundHandler.play(punch);
    }


    @Override
    public void tick(){
        super.tick();
        counterAttack.tick();
    }


    //Probably temp because there's only one channel sprite
    @Override
    protected void updateChannelAnimation(){
        owner.setChannelAnimation(channelAnimation, "s", channelAnimationSpeed);
    }
}
