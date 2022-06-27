package com.jac.game.audio;

public class Music extends LoopingSound {

    public static final Music RITA_ENCOUNTER = new Music("music/rita_encounter.wav");
    public static final Music BOUNCE_BACK = new Music("music/bounce_back.wav");
    public static final Music BIG_SAD = new Music("music/temp_sad.wav");
    public static final Music BATTLE = new Music("music/battle_theme.wav");

    public Music(String path) {
        super(path);
    }

}