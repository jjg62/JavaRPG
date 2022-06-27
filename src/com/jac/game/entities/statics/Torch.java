package com.jac.game.entities.statics;

import com.jac.game.display.GameGraphics;
import com.jac.game.entities.Entity;
import com.jac.game.fx.VisualEffect;
import com.jac.game.rooms.Room;
import com.jac.game.textures.Animation;
import com.jac.game.utils.FileUtils;

import java.util.HashMap;

public class Torch extends Entity {

    private int colour = 0;

    private static HashMap<String, Animation> animations = new HashMap<>(){{
        String[] types = {"torch", "torchLight"};
        int[] colours = {0, 1, 2, 3, 4};
        for(String type : types){
            for(int colour : colours){
                String tag = type + colour;
                put(tag, new Animation(12, FileUtils.loadAnimationFrames("/textures/entities/interacts/torch/" + tag + ".png", 32, 32)));
            }
        }
    }};

    private Animation anim;
    private VisualEffect light;

    public Torch(Room room, int colour) {
        super(room, 64, 64);
        this.colour = colour;
    }

    public void updateAnimations(int colour){
        anim = animations.get("torch" + colour).clone();
        if(light != null) light.end();
        light = new VisualEffect(room, animations.get("torchLight" + colour).clone(), 128, 128, 48, true);
        light.play(x - 32, y - 32);
    }

    public Torch(Room room){
        this(room, 0);
    }

    @Override
    public void spawn(int x, int y){
        super.spawn(x, y);
        updateAnimations(colour);
    }

    @Override
    public void tick() {
        anim.tick();
    }

    @Override
    public void render(GameGraphics graphics) {
        graphics.draw(anim.getFrame(), x, y, width, height);
    }

    public int getColour(){
        return colour;
    }
}
