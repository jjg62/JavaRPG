package com.jac.game.cutscene.events;

import com.jac.game.audio.Sound;
import com.jac.game.cutscene.CutsceneEvent;

public class PlaySoundEvent extends CutsceneEvent {

    private Sound sound;

    public PlaySoundEvent(Sound sound){
        this.sound = sound;
    }

    @Override
    protected void onStart() {
        sound.play();
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return true;
    }
}
