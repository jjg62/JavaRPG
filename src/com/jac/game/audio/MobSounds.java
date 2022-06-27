package com.jac.game.audio;

import com.jac.game.abilities.Ability;
import com.jac.game.entities.Mob;
import com.jac.game.textures.Animation;
import com.jac.game.utils.DirectionUtils;
import com.jac.game.utils.FileUtils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class MobSounds {

    private final String pathStart;
    private Mob owner;

    private HashMap<String, Clip> soundsStore;
    private String[] variants = {"1", "2", "3", "4"};
    private String[] types = {"idle", "hit"};

    public MobSounds(Mob owner){
        this.owner = owner;
        pathStart = "audio/entities/" + owner.getName() + "/"; //Start of path for the file - /audio/entities/player/
        soundsStore = new HashMap<>();
        loadSounds();
    }

    private void loadSounds(){
        for(String type : types){
            for(String variant : variants){
                soundsStore.put(key(type, variant), loadSound(type, variant));
            }
        }
        loadAbilitySounds();
    }

    private void loadAbilitySounds(){
        for(Ability a : owner.getAbilities().getAdding()){
            soundsStore.put(key("ability", a.getName()), loadSound("ability", a.getName()));
        }
    }

    private Clip loadSound(String type, String variant){
        return SoundHandler.loadClip(pathStart + type + "/" + owner.getName() + "_" + key(type, variant) + ".wav");
    }

    public Clip get(String type, String variant){
        return soundsStore.get(key(type, variant));
    }

    private String key(String type, String variant){
        return type + "_" + variant;
    }

    public void playAVariant(String type){
        Random rand = new Random();
        String variant = Integer.toString(rand.nextInt(4) + 1);
        Clip clip = get(type, variant);
        if(clip != null){
            SoundHandler.play(clip);
        }
    }

    public void playAbilitySound(Ability a){
        Clip clip = get("ability", a.getName());
        if(clip != null){
            SoundHandler.play(clip);
        }
    }


}
