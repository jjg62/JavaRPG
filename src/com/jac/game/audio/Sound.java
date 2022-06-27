package com.jac.game.audio;

import com.jac.game.utils.FileUtils;

import javax.sound.sampled.Clip;

public class Sound {

    protected Clip clip;

    public Sound(String path){
        this.clip = SoundHandler.loadClip("audio/" + path);
        stop();
    }

    public void play(){
        SoundHandler.play(clip);
    }

    public void stop(){
        clip.stop();
    }
}
