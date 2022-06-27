package com.jac.game.cutscene.events;

import com.jac.game.audio.Music;
import com.jac.game.audio.SoundHandler;
import com.jac.game.cutscene.CutsceneEvent;
import com.jac.game.ui.MultiTextbox;

public class ChangeMusicEvent extends CutsceneEvent {

    private Music music;
    private boolean resume;

    public ChangeMusicEvent(Music music){
        this.music = music;
    }

    public ChangeMusicEvent(){
        this.resume = true;
    }


    @Override
    protected void onStart() {
        if(resume){
            SoundHandler.resumeRoomMusic();
        }else{
            SoundHandler.changeMusic(music);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean endCondition() {
        return true;
    }
}
