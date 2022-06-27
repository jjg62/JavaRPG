package com.jac.game.audio;

import javax.sound.sampled.Clip;

public class LoopingSound extends Sound{

    protected boolean playing;

    public LoopingSound(String path) {
        super(path);
    }

    public void play(){
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        playing = true;
    }

    public void stop(){
        super.stop();
        playing = false;
    }

    public boolean isPlaying(){
        return playing;
    }

}
