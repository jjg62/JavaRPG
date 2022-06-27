package com.jac.game.ui.speakers;

import com.jac.game.audio.SoundHandler;
import com.jac.game.utils.FileUtils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Speaker {

    private final char[] emotions = {'h', 'c', 's', 'n'};

    private String soundPathStart;
    private String imagePathStart;
    private String name;
    private HashMap<Character, Clip> sounds;
    private HashMap<Character, BufferedImage> images;

    public Speaker(String name){
        this.name = name;
        this.soundPathStart = "audio/speakers/" + name + "/" + name + "_";
        this.imagePathStart = "/textures/speakers/" + name + "/" + name + "_";

        this.sounds = new HashMap<>();
        this.images = new HashMap<>();
        initSounds();
        initImages();
    }

    private void initSounds(){
        for(char emotion : emotions){
            sounds.put(emotion, loadSound(emotion));
        }
    }

    private void initImages(){
        for(char emotion : emotions){
            images.put(emotion, FileUtils.loadImage(imagePathStart + emotion + ".png"));
        }
    }

    private Clip loadSound(char emotion){
        return SoundHandler.loadClip(soundPathStart + emotion + ".wav");
    }

    public void playDialogueSound(char emotion){
        Clip sound = sounds.get(emotion);
        if(sound != null) SoundHandler.play(sound);
    }

    public BufferedImage getImage(char emotion){
        return images.get(emotion);
    }

}
