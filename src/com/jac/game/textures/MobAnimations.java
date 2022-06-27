package com.jac.game.textures;

import com.jac.game.entities.Mob;
import com.jac.game.entities.MovingCollider;
import com.jac.game.textures.Animation;
import com.jac.game.utils.DirectionUtils;
import com.jac.game.utils.FileUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class MobAnimations {

    private int width, height;
    private final String pathStart;
    private final int speed = 4;
    private MovingCollider owner;
    private String name;

    private HashMap<String, Animation> animationsStore;
    private String[] directions = {"n", "ne", "e", "se", "s", "sw", "w", "nw"};
    private String[] types = {"walk", "attack", "channel", "jump", "idle", "shield"};

    public MobAnimations(MovingCollider owner, String name, int width, int height){
        this.owner = owner;
        this.name = name;
        pathStart = "/textures/entities/" + name + "/"; //Start of path for the file - /textures/entities/player/
        this.width = width;
        this.height = height;
        animationsStore = new HashMap<>();
        loadAnimations();
    }

    private void loadAnimations(){
        for(String type : types){
            for(String direction : directions){
                animationsStore.put(key(type, direction), new Animation(speed, loadAnimationFrames(type, direction)));
            }
        }
    }


    private BufferedImage[] loadAnimationFrames(String type, String direction){
        return FileUtils.loadAnimationFrames(pathStart + type + "/" + name + "_" + key(type, direction) + ".png", width, height);
    }

    public Animation get(String type, String direction){

        return animationsStore.get(key(type, direction));
    }

    private String key(String type, String direction){
        return type + "_" + direction;
    }

    public Animation getMoveAnimation(boolean still){
        return get(still ? "idle" : "walk", DirectionUtils.vectorToString(owner.getDirectionVector()));
    }

}
